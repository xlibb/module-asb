## Package Overview

This package offers administrative client capabilities for Azure Service Bus, allowing the creation or deletion of
Azure Service Bus topics and subscriptions.

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
