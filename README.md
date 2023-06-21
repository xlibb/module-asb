# Ballerina ASB Library

[![Build](https://github.com/xlibb/module-asb/actions/workflows/build-timestamped-master.yml/badge.svg)](https://github.com/xlibb/module-asb/actions/workflows/build-timestamped-master.yml)
[![codecov](https://codecov.io/gh/xlibb/module-asb/branch/master/graph/badge.svg)](https://codecov.io/gh/xlibb/module-asb)
[![GitHub Last Commit](https://img.shields.io/github/last-commit/xlibb/module-asb.svg)](https://github.com/xlibb/module-asb/commits/master)

[Azure Service Bus](https://azure.microsoft.com/en-us/services/service-bus/) is a robust cloud-based 
Messaging as a Service (MaaS) offering. It serves as an enterprise message broker, providing both message queues and 
publish-subscribe topics. The service bus plays a vital role in decoupling applications and services from one another. 
Messages are utilized for seamless data transfer between different applications and services.

The Ballerina Admin client connector for Azure Service Bus facilitates connectivity to Azure Service Bus, 
enabling the creation and deletion of Azure Service Bus topics and subscriptions.

## Build from the source

### Set up the prerequisites

1.  Download and install Java SE Development Kit (JDK) version 11 (from one of the following locations).

    - [Oracle](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)

    - [OpenJDK](https://adoptopenjdk.net/)

      > **Note:** Set the JAVA_HOME environment variable to the path name of the directory into which you installed JDK.

2.  Export your Github Personal access token with the read package permissions as follows.

              export packageUser=<Username>
              export packagePAT=<Personal access token>

### Build the source

Execute the commands below to build from the source.

1. To build the library:

   ```
   ./gradlew clean build
   ```

2. To run the integration tests:
   ```
   ./gradlew clean test
   ```
3. To build the module without the tests:
   ```
   ./gradlew clean build -x test
   ```
4. To debug module implementation:
   ```
   ./gradlew clean build -Pdebug=<port>
   ./gradlew clean test -Pdebug=<port>
   ```
5. To debug the module with Ballerina language:
   ```
   ./gradlew clean build -PbalJavaDebug=<port>
   ./gradlew clean test -PbalJavaDebug=<port>
   ```
6. Publish ZIP artifact to the local `.m2` repository:
   ```
   ./gradlew clean build publishToMavenLocal
   ```
7. Publish the generated artifacts to the local Ballerina central repository:
   ```
   ./gradlew clean build -PpublishToLocalCentral=true
   ```
8. Publish the generated artifacts to the Ballerina central repository:
   ```
   ./gradlew clean build -PpublishToCentral=true
   ```
