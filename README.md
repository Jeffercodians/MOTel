# MOtel (Minecraft Open Telemetry Platform)

Add Open Telemetry hooks into Minecraft events to enable trace analysis of events happening in the game world.

## Run the Demos

From the fresh clone of the repository, run the desired demo bash script

Script                  | Description
------------------------|-----------------------------------------------------------
demos/jaeger_trace.bash | See jaeger spin up and a simple trace be sent through
demos/dev_server.bash   | Build the mod jar, launch the server, then launch the client.  Configured for offline dev testing

## Setting up your IDE:

If you prefer to use Eclipse:
1. Run the following command: `gradlew genEclipseRuns` (`./gradlew genEclipseRuns` if you are on Mac/Linux)
2. Open Eclipse, Import > Existing Gradle Project > Select Folder 
   or run `gradlew eclipse` to generate the project.

If you prefer to use IntelliJ:
1. Open IDEA, and import project.
2. Select your build.gradle file and have it import.
3. Run the following command: `gradlew genIntellijRuns` (`./gradlew genIntellijRuns` if you are on Mac/Linux)
4. Refresh the Gradle Project in IDEA if required.

If at any point you are missing libraries in your IDE, or you've run into problems you can 
run `gradlew --refresh-dependencies` to refresh the local cache. `gradlew clean` to reset everything 
{this does not affect your code} and then start the process again.

## Resources

* [OpenTelemetry Instrumentation for Java](https://github.com/open-telemetry/opentelemetry-java-instrumentation)
  * [SDK autoconfiguration example](https://github.com/open-telemetry/opentelemetry-java-examples/tree/main/autoconfigure)
* [OpenTelemetry SDK Autoconfigure](https://github.com/open-telemetry/opentelemetry-java/blob/main/sdk-extensions/autoconfigure/README.md)
