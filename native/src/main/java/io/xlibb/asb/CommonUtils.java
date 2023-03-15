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

import com.azure.core.exception.HttpResponseException;
import com.azure.core.http.HttpResponse;
import io.ballerina.runtime.api.creators.ErrorCreator;
import io.ballerina.runtime.api.creators.ValueCreator;
import io.ballerina.runtime.api.values.BError;
import io.ballerina.runtime.api.values.BMap;
import io.ballerina.runtime.api.values.BString;

import java.util.Map;

import static io.ballerina.runtime.api.utils.StringUtils.fromString;
import static io.xlibb.asb.Constants.CLIENT_INITIALIZATION_ERROR;
import static io.xlibb.asb.ModuleUtils.getModule;

/**
 * {@code CommonUtils} contains the common utility methods which can be used as helper methods.
 */
public final class CommonUtils {
    public static BError createError(String errorType, String message, Throwable throwable) {
        BError cause = ErrorCreator.createError(throwable);
        BMap<BString, Object> errorDetails = getErrorDetails(errorType, throwable);
        return ErrorCreator.createError(getModule(), errorType, fromString(message), cause, errorDetails);
    }

    private static BMap<BString, Object> getErrorDetails(String errorType, Throwable throwable) {
        if (CLIENT_INITIALIZATION_ERROR.equals(errorType)) {
            return ValueCreator.createRecordValue(getModule(), "ErrorDetails",
                    Map.of("statusCode", 10000));
        }
        if (throwable instanceof HttpResponseException) {
            HttpResponse httpResponse = ((HttpResponseException) throwable).getResponse();
            int statusCode = httpResponse.getStatusCode();
            return ValueCreator.createRecordValue(getModule(), "ErrorDetails",
                            Map.of("statusCode", statusCode));
        }
        return ValueCreator.createRecordValue(getModule(), "ErrorDetails",
                Map.of("statusCode", 10001));
    }
}
