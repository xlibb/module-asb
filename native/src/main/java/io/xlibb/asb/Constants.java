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

package io.xlibb.asb;

import io.ballerina.runtime.api.values.BString;

import static io.ballerina.runtime.api.utils.StringUtils.fromString;

/**
 * {@code Constants} contains the public constants to be used.
 */
public interface Constants {
    String PACKAGE_ORG = "xlibb";
    String PACKAGE_NAME = "asb";

    String ASB_ADMIN_CLIENT = "ASB_ADMIN_CLIENT";

    // Error names for ASB package
    String CLIENT_INITIALIZATION_ERROR = "ClientInitializationError";
    String CLIENT_INVOCATION_ERROR = "ClientInvocationError";

    // General error statusCodes for ASB packages
    int CLIENT_INITIALIZATION_ERROR_CODE = 10000;
    int CLIENT_INVOCATION_ERROR_CODE = 10001;

    // Record names for ASB package
    String TOPIC_CREATED_RECORD = "TopicCreated";
    String SUBSCRIPTION_CREATED_RECORD = "SubscriptionCreated";

    // Record field names
    BString RECORD_FIELD_TOPIC_NAME = fromString("topicName");
    BString RECORD_FIELD_STATUS = fromString("status");
    BString RECORD_FIELD_USER_META_DATA = fromString("userMetaData");
}
