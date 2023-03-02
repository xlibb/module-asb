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

import ballerina/jballerina.java;

# Record to represent the ASB topic properties.
# 
# + topicName - Name of the topic
# + status - Current status of the ASB topic resource
# + userMetaData - Additional metadata regarding the ASB topic reource
public type TopicCreated record {|
    string topicName;
    string status;
    string userMetaData;
|};

# Record to represent the ASB subscription properties.
# 
# + status - Current status of the ASB subscription resource
# + userMetaData - Additional metadata regarding the ASB subscription reource
public type SubscriptionCreated record {|
    string status;
    string userMetaData;
|};

# Azure SDK based client for Azure service bus administration.
public isolated client class Client {

    # Initializes the `asb.admin:Client`.
    # ```ballerina
    # asb.admin:Client adminClient = check new("connectionString");
    # ```
    # 
    # + connectionString - Azure connection string for the service bus resource
    # + return - The `asb.admin:Client` or an `asb.admin:Error` if the initialization failed
    public isolated function init(string connectionString) returns Error? {
        check self.externInit(connectionString);
    }

    private isolated function externInit(string connectionString) returns Error? =
    @java:Method {
        'class: "io.xlibb.asb.admin.AsbAdminClientAdaptor"
    } external;

    # Checks whether an ASB `topic` is available in the service bus resource.
    # 
    # + topic - Name of the topic
    # + return - `true` if topic exists. `false` if topic does not exist, or else an `asb.admin:Error`
    isolated remote function topicExists(string topic) returns boolean|Error = 
    @java:Method {
        'class: "io.xlibb.asb.admin.AsbAdminClientAdaptor"
    } external;    

    # Creates an ASB `topic` in the service bus resource.
    # 
    # + topic - Name of the topic
    # + return - A `asb.admin:TopicCreated` for successful topic creation or an `asb.admin:Error`
    isolated remote function createTopic(string topic) returns TopicCreated|Error = 
    @java:Method {
        'class: "io.xlibb.asb.admin.AsbAdminClientAdaptor"
    } external;    

    # Deletes an ASB `topic` from the service bus resource.
    # 
    # + topic - Name of the topic
    # + return - An `asb.admin:Error` if there is an exception during the method execution
    isolated remote function deleteTopic(string topic) returns Error? = 
    @java:Method {
        'class: "io.xlibb.asb.admin.AsbAdminClientAdaptor"
    } external;

    # Checks whether an ASB `subscription` exists in the service bus resource.
    # 
    # + topic - Name of the topic
    # + subscription - Name of the subscription
    # + return - `true` if subscription exists. `false` if subscription does not exist, or else an `asb.admin:Error`
    isolated remote function subscriptionExists(string topic, string subscription) returns boolean|Error =
    @java:Method {
        'class: "io.xlibb.asb.admin.AsbAdminClientAdaptor"
    } external;
    
    # Creates an ASB `subscription` in the service bus resource.
    # 
    # + topic - Name of the topic
    # + subscription - Name of the subscription
    # + return - A `asb.admin:SubscriptionCreated` for successful topic creation or an `asb.admin:Error`
    isolated remote function createSubscription(string topic, string subscription) returns SubscriptionCreated|Error =
    @java:Method {
        'class: "io.xlibb.asb.admin.AsbAdminClientAdaptor"
    } external;

    # Deletes an ASB `subscription` from the service bus resource.
    # 
    # + topic - Name of the topic
    # + subscription - Name of the subscription
    # + return - An `asb.admin:Error` if there is an exception during the method execution
    isolated remote function deleteSubscription(string topic, string subscription) returns Error? = 
    @java:Method {
        'class: "io.xlibb.asb.admin.AsbAdminClientAdaptor"
    } external;
}
