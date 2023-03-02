// Copyright (c) 2023, WSO2 LLC. (http://www.wso2.org) All Rights Reserved.
//
// WSO2 LLC. licenses this file to you under the Apache License,
// Version 2.0 (the "License"); you may not use this file except
// in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing,
// software distributed under the License is distributed on an
// "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
// KIND, either express or implied.  See the License for the
// specific language governing permissions and limitations
// under the License.

import ballerina/io;
import ballerina/os;
import ballerina/test;

configurable string connectionString = os:getEnv("CONNECTION_STRING");

const string TOPIC_NAME = "topic-1";
const string TOPIC_NAME_2 = "topic-2";
const string SUBSCRIPTION_NAME = "sub-1";
const string SUBSCRIPTION_NAME_2 = "sub-2";

final Client clientEp = check new(connectionString);

@test:Config {
    groups: ["adminClient"]
}
isolated function testInvalidTopicExists() returns error? {
    boolean topicExists = check clientEp->topicExists(TOPIC_NAME);
    test:assertFalse(topicExists);
}

@test:Config {
    groups: ["adminClient"],
    dependsOn: [testInvalidTopicExists]
}
isolated function testTopicCreation() returns error? {
    TopicCreated topicCreated = check clientEp->createTopic(TOPIC_NAME);
    io:println(topicCreated);
    test:assertEquals(topicCreated.topicName, TOPIC_NAME);
    test:assertEquals(topicCreated.status, "Active");
}

@test:Config {
    groups: ["adminClient"],
    dependsOn: [testInvalidTopicExists]
}
isolated function testValidTopicExists() returns error? {
    boolean topicExists = check clientEp->topicExists(TOPIC_NAME);
    test:assertTrue(topicExists);
}

@test:Config {
    groups: ["adminClient"],
    dependsOn: [testValidTopicExists]
}
isolated function testInvalidSubscriptionExists() returns error? {
    boolean subscriptionExists = check clientEp->subscriptionExists(TOPIC_NAME, SUBSCRIPTION_NAME);
    test:assertFalse(subscriptionExists);
}

@test:Config {
    groups: ["adminClient"],
    dependsOn: [testInvalidSubscriptionExists]
}
isolated function testSubscriptionCreation() returns error? {
    SubscriptionCreated subscriptionCreated = check clientEp->createSubscription(TOPIC_NAME, SUBSCRIPTION_NAME);
    io:println(subscriptionCreated);
    test:assertEquals(subscriptionCreated.status, "Active");
}

@test:Config {
    groups: ["adminClient"],
    dependsOn: [testSubscriptionCreation]
}
isolated function testValidSubscriptionExists() returns error? {
    boolean subscriptionExists = check clientEp->subscriptionExists(TOPIC_NAME, SUBSCRIPTION_NAME);
    test:assertTrue(subscriptionExists);
}

@test:Config {
    groups: ["adminClient"],
    dependsOn: [testValidSubscriptionExists]
}
isolated function testSubscriptionDeletion() returns error? {
    check clientEp->deleteSubscription(TOPIC_NAME, SUBSCRIPTION_NAME);
}

@test:Config {
    groups: ["adminClient"],
    dependsOn: [testSubscriptionDeletion]
}
isolated function testTopicDeletion() returns error? {
    check clientEp->deleteTopic(TOPIC_NAME);
}
