
Installation information
=======

This template repository can be directly cloned to get you started with a new
mod. Simply create a new repository cloned from this one, by following the
instructions at [github](https://docs.github.com/en/repositories/creating-and-managing-repositories/creating-a-repository-from-a-template).

Once you have your clone, you can initialize your copy.

Setup Process:
--------

Step 1: Open your command-line and browse to the folder where you extracted cloned your copy of this repository to.

1. Open IDEA, and import project.
2. Select your build.gradle file and have it import.
3. Run the following command: `gradlew genIntellijRuns` (`./gradlew genIntellijRuns` if you are on Mac/Linux)
4. Refresh the Gradle Project in IDEA if required.

If at any point you are missing libraries in your IDE, or you've run into problems you can 
run `gradlew --refresh-dependencies` to refresh the local cache. `gradlew clean` to reset everything 
{this does not affect your code} and then start the process again.

Mapping Names:
============
By default, the MDK is configured to use the official mapping names from Mojang for methods and fields 
in the Minecraft codebase. These names are covered by a specific license. All modders should be aware of this
license, if you do not agree with it you can change your mapping names to other crowdsourced names in your 
build.gradle. For the latest license text, refer to the mapping file itself, or the reference copy here:
https://github.com/NeoForged/NeoForm/blob/main/Mojang.md

Additional Resources: 
==========
Community Documentation: https://docs.neoforged.net/  
NeoForged Discord: https://discord.neoforged.net/

## How to add bees

1. Start MOTel: `./gradlew runClient`
2. Click `Singleplayer`
3. Click `Create New World`
4. Set World Name to `Bee's Knees` or another less humorous name
5. Set Game Mode to `Creative`
6. Set Allow Cheats to `On`
7. Click `Create New World`
8. ...
9. Profit

## Dependencies Added

* [OpenTelemetry Java](https://github.com/open-telemetry/opentelemetry-java)
  * based on [Java OpenTelemetry Examples](https://github.com/open-telemetry/opentelemetry-java-examples)
  * [Docs](https://opentelemetry.io/docs/instrumentation/java/)