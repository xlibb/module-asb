/*
 * Copyright (c) 2023, WSO2 LLC. (http://www.wso2.org) All Rights Reserved.
 *
 *  WSO2 LLC. licenses this file to you under the Apache License,
 *  Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied. See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */

package io.xlibb.asb.admin;

import com.azure.core.exception.HttpResponseException;
import com.azure.core.http.HttpResponse;
import com.azure.messaging.servicebus.administration.ServiceBusAdministrationClient;
import com.azure.messaging.servicebus.administration.ServiceBusAdministrationClientBuilder;
import com.azure.messaging.servicebus.administration.models.SubscriptionProperties;
import com.azure.messaging.servicebus.administration.models.TopicProperties;
import io.ballerina.runtime.api.creators.ValueCreator;
import io.ballerina.runtime.api.values.BMap;
import io.ballerina.runtime.api.values.BObject;
import io.ballerina.runtime.api.values.BString;

import static io.ballerina.runtime.api.utils.StringUtils.fromString;
import static io.xlibb.asb.CommonUtils.createError;
import static io.xlibb.asb.Constants.ASB_ADMIN_CLIENT;
import static io.xlibb.asb.Constants.CLIENT_INITIALIZATION_ERROR;
import static io.xlibb.asb.Constants.CLIENT_INVOCATION_ERROR;
import static io.xlibb.asb.Constants.RECORD_FIELD_STATUS;
import static io.xlibb.asb.Constants.RECORD_FIELD_TOPIC_NAME;
import static io.xlibb.asb.Constants.RECORD_FIELD_USER_META_DATA;
import static io.xlibb.asb.Constants.SUBSCRIPTION_CREATED_RECORD;
import static io.xlibb.asb.Constants.TOPIC_CREATED_RECORD;
import static io.xlibb.asb.ModuleUtils.getModule;

/**
 * {@code AsbAdminClientAdaptor} is the ballerina runtime adaptor for ASB administration client.
 */
public final class AsbAdminClientAdaptor {
    public static Object externInit(BObject adminClient, BString connectionString) {
        try {
            ServiceBusAdministrationClient clientEp = new ServiceBusAdministrationClientBuilder()
                    .connectionString(connectionString.getValue())
                    .buildClient();
            adminClient.addNativeData(ASB_ADMIN_CLIENT, clientEp);
        } catch (Exception e) {
            return createError(CLIENT_INITIALIZATION_ERROR, "failed to initialize the ASB admin-client", e);
        }
        return null;
    }

    public static Object topicExists(BObject adminClient, BString topic) {
        ServiceBusAdministrationClient clientEp = (ServiceBusAdministrationClient) adminClient
                .getNativeData(ASB_ADMIN_CLIENT);
        String topicName = topic.getValue();
        try {
            return clientEp.getTopicExists(topicName);
        } catch (HttpResponseException responseException) {
            HttpResponse httpResponse = responseException.getResponse();
            if (404 == httpResponse.getStatusCode()) {
                return false;
            }
            return createError(CLIENT_INVOCATION_ERROR,
                    String.format("request to identify ASB topic exists %s", topicName), responseException);
        } catch (Exception e) {
            return createError(
                    CLIENT_INVOCATION_ERROR, String.format("request to identify ASB topic exists %s", topicName), e);
        }
    }

    public static Object createTopic(BObject adminClient, BString topic) {
        ServiceBusAdministrationClient clientEp = (ServiceBusAdministrationClient) adminClient
                .getNativeData(ASB_ADMIN_CLIENT);
        String topicName = topic.getValue();
        try {
            TopicProperties topicProperties = clientEp.createTopic(topicName);
            return constructTopicCreatedRecord(topicProperties);
        } catch (Exception e) {
            return createError(CLIENT_INVOCATION_ERROR, String.format("failed to create ASB topic %s", topicName), e);
        }
    }

    private static BMap<BString, Object> constructTopicCreatedRecord(TopicProperties properties) {
        BMap<BString, Object> topicCreated = ValueCreator.createRecordValue(getModule(), TOPIC_CREATED_RECORD);
        topicCreated.put(RECORD_FIELD_TOPIC_NAME, fromString(properties.getName()));
        topicCreated.put(RECORD_FIELD_STATUS, fromString(properties.getStatus().toString()));
        topicCreated.put(RECORD_FIELD_USER_META_DATA, fromString(properties.getUserMetadata()));
        return topicCreated;
    }

    public static Object deleteTopic(BObject adminClient, BString topic) {
        ServiceBusAdministrationClient clientEp = (ServiceBusAdministrationClient) adminClient
                .getNativeData(ASB_ADMIN_CLIENT);
        String topicName = topic.getValue();
        try {
            clientEp.deleteTopic(topicName);
        } catch (Exception e) {
            return createError(CLIENT_INVOCATION_ERROR, String.format("failed to delete ASB topic %s", topicName), e);
        }
        return null;
    }

    public static Object subscriptionExists(BObject adminClient, BString topic, BString subscription) {
        ServiceBusAdministrationClient clientEp = (ServiceBusAdministrationClient) adminClient
                .getNativeData(ASB_ADMIN_CLIENT);
        String topicName = topic.getValue();
        String subscriptionId = subscription.getValue();
        try {
            return clientEp.getSubscriptionExists(topicName, subscriptionId);
        } catch (HttpResponseException responseException) {
            HttpResponse httpResponse = responseException.getResponse();
            if (404 == httpResponse.getStatusCode()) {
                return false;
            }
            String message = String.format(
                    "request to identify ASB subscription exists failed for topic %s and subscriber %s",
                    topicName, subscriptionId);
            return createError(CLIENT_INVOCATION_ERROR, message, responseException);
        } catch (Exception e) {
            String message = String.format(
                    "request to identify ASB subscription exists failed for topic %s and subscriber %s",
                    topicName, subscriptionId);
            return createError(CLIENT_INVOCATION_ERROR, message, e);
        }
    }
    
    public static Object createSubscription(BObject adminClient, BString topic, BString subscription) {
        ServiceBusAdministrationClient clientEp = (ServiceBusAdministrationClient) adminClient
                .getNativeData(ASB_ADMIN_CLIENT);
        String topicName = topic.getValue();
        String subscriptionId = subscription.getValue();
        try {
            SubscriptionProperties subscriptionProperties = clientEp.createSubscription(topicName, subscriptionId);
            return constructSubscriptionCreatedRecord(subscriptionProperties);
        } catch (Exception e) {
            String message = String.format("failed to create ASB subscription for topic %s and subscriber %s",
                    topicName, subscriptionId);
            return createError(CLIENT_INVOCATION_ERROR, message, e);
        }
    }

    private static BMap<BString, Object> constructSubscriptionCreatedRecord(SubscriptionProperties properties) {
        BMap<BString, Object> subscriptionCreated = ValueCreator.createRecordValue(
                getModule(), SUBSCRIPTION_CREATED_RECORD);
        subscriptionCreated.put(RECORD_FIELD_STATUS, fromString(properties.getStatus().toString()));
        subscriptionCreated.put(RECORD_FIELD_USER_META_DATA, fromString(properties.getUserMetadata()));
        return subscriptionCreated;
    }

    public static Object deleteSubscription(BObject adminClient, BString topic, BString subscription) {
        ServiceBusAdministrationClient clientEp = (ServiceBusAdministrationClient) adminClient
                .getNativeData(ASB_ADMIN_CLIENT);
        String topicName = topic.getValue();
        String subscriptionId = subscription.getValue();
        try {
            clientEp.deleteSubscription(topicName, subscriptionId);
        } catch (Exception e) {
            String message = String.format("failed to delete ASB subscription for topic %s and subscriber %s",
                    topicName, subscriptionId);
            return createError(CLIENT_INVOCATION_ERROR, message, e);
        }
        return null;
    }
}
