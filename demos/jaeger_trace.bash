#!/usr/bin/env bash

GIT_ROOT="$(git rev-parse --show-toplevel)"
source "${GIT_ROOT}/.env"

_compose() (
    COMPOSE="$(command -v docker-compose)"
    [ -n "${COMPOSE-}" ] || \
        COMPOSE=("$(command -v docker)" "compose")

    cd "${GIT_ROOT}" || exit 1

    "${COMPOSE[@]}" "${@}"
)

bring_up() {
    _compose up -d jaeger

    trap "_compose down" EXIT
}

run_jaeger_example() (
    example_root="$(mktemp -d)"

    cd "${example_root}" || exit 1

    git clone https://github.com/open-telemetry/opentelemetry-java-examples.git .

    ./gradlew shadowJar

    java \
        -cp jaeger/build/libs/opentelemetry-examples-jaeger-0.1.0-SNAPSHOT-all.jar \
        io.opentelemetry.example.jaeger.JaegerExample \
            "http://localhost:${JAEGER_OTEL_PORT}"

    rm -rf "${example_root}"
)

# Bring up docker-compose
bring_up

# Clone/build/run Java Jaeger examples
run_jaeger_example

# Launch Jaeger web UI
echo "Opening Jaeger HTTP interface"
open "http://localhost:${JAEGER_HTTP_PORT}"

# Wait for user to kill, then docker-compose down

read -r -p "press ENTER to bring the Jaeger stack down"