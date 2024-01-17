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

build_jar() {
    "${GIT_ROOT}/gradlew" jar
}

bring_up() {
    _compose up -d

    trap "_compose down" EXIT
}

run_minecraft_client() {
    "${GIT_ROOT}/gradlew" runClient
}

# Build the mod jar so the server can load it
build_jar

# Bring up docker-compose
bring_up

# Launch the minecraft client with the same configuration as the server
run_minecraft_client
