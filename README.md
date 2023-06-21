# Ballerina ASB Library

[![Build](https://github.com/xlibb/module-asb/actions/workflows/build-timestamped-master.yml/badge.svg)](https://github.com/xlibb/module-asb/actions/workflows/build-timestamped-master.yml)
[![codecov](https://codecov.io/gh/xlibb/module-asb/branch/master/graph/badge.svg)](https://codecov.io/gh/xlibb/module-asb)
[![GitHub Last Commit](https://img.shields.io/github/last-commit/xlibb/module-asb.svg)](https://github.com/xlibb/module-asb/commits/master)

[Azure Service Bus](https://azure.microsoft.com/en-us/services/service-bus/) is a robust cloud-based 
Messaging as a Service (MaaS) offering. It serves as an enterprise message broker, providing both message queues and 
publish/subscribe topics. The service bus plays a vital role in decoupling applications and services from one another. 
Messages are utilized for seamless data transfer between different applications and services.

The Ballerina Admin client connector for Azure Service Bus facilitates connectivity to Azure Service Bus, 
enabling the creation and deletion of Azure Service Bus topics and subscriptions.

### Admin client

Azure service bus Admin client provides administrative capabilities to create and delete Azure service bus topics
and subscriptions. In addition to the creation and deletion functionalities for topics and subscriptions, this module
also provides the ability to check the existence of an Azure Service Bus topic or subscription.

#### Create admin client

An `asb.admin:Client` can be created as follows.
```ballerina
import xlibb/asb.admin as admin;

configurable string connectionString = ?;

final admin:Client clientEp = check new(connectionString);
```

Following APIs are available in the Azure service bus admin client:
- <b> topicExists </b>: Checks whether an ASB `topic` is available in the service bus resource.
- <b> createTopic </b>: Creates an ASB `topic` in the service bus resource.
- <b> deleteTopic </b>: Deletes an ASB `topic` from the service bus resource.
- <b> subscriptionExists </b>: Checks whether an ASB `subscription` exists in the service bus resource.
- <b> createSubscription </b>: Creates an ASB `subscription` in the service bus resource.
- <b> deleteSubscription </b>: Deletes an ASB `subscription` from the service bus resource.

##### Topic exists

This method checks whether the given topic is available in the service bus namespace. User has to provide the name of
the topic as an input to the function.
```ballerina
import xlibb/asb.admin as admin;

public function main() returns error? {
    // ...
    boolean topicExists = check clientEp->topicExists("topic-a");
}
```

In the event of a network failure during execution, it will lead to an `asb.admin:Error`.

##### Create topic

This method creates a new topic in the Azure service bus namespace. User has to provide the name of the topic as an
input to the function.
```ballerina
import xlibb/asb.admin as admin;

public function main() returns error? {
    // ...
    admin:TopicCreated topicCreated = check clientEp->createTopic("topic-a");
}
```

If an attempt is made to create an already existing topic, or if there is a network failure during the execution,
it will result in an `asb.admin:Error`.

##### Delete topic

This method deletes an already existing topic from the Azure service bus namespace. User has to provide the name of
the topic as an input to the function.
```ballerina
import xlibb/asb.admin as admin;

public function main() returns error? {
    // ...
    check clientEp->deleteTopic("topic-a");
}
```

If an attempt is made to delete a non-existing topic, or if there is a network failure during the execution,
it will result in an `asb.admin:Error`.

##### Subscription exists

This method checks whether the given subscription is available in the service bus namespace. User has to provide the
name of the topic and the name of the subscription as inputs to the function.
```ballerina
import xlibb/asb.admin as admin;

public function main() returns error? {
    // ...
    boolean subscriptionExists = check clientEp->subscriptionExists("topic-a", "subscription-1");
}
```

In the event of a network failure during execution, it will lead to an `asb.admin:Error`.

##### Create subscription

This method creates a new subscription for a given topic in the Azure service bus namespace. User has to provide the
name of the topic and the name of the subscription as inputs to the function.
```ballerina
import xlibb/asb.admin as admin;

public function main() returns error? {
    // ...
    admin:SubscriptionCreated subscriptionCreated = check clientEp->createSubscription("topic-a", "subscription-1");
}
```

If an attempt is made to create an already existing subscription, or if there is a network failure during the execution,
it will result in an `asb.admin:Error`.

##### Delete topic

This method deletes an already existing subscription for a given topic from the Azure service bus namespace. User has
to provide the name of the topic and the name of the subscription as inputs to the function.
```ballerina
import xlibb/asb.admin as admin;

public function main() returns error? {
    // ...
    check clientEp->deleteSubscription("topic-a", "subscription-1");
}
```

If an attempt is made to delete a non-existing subscription, or if there is a network failure during the execution,
it will result in an `asb.admin:Error`.

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
