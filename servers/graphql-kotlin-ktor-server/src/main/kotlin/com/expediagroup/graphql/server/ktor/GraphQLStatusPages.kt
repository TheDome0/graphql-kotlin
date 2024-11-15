/*
 * Copyright 2024 Expedia, Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.expediagroup.graphql.server.ktor

import io.ktor.http.HttpStatusCode
import io.ktor.server.plugins.statuspages.StatusPagesConfig
import io.ktor.server.response.respond

/**
 * Configures default exception handling using Ktor Status Pages.
 *
 * Returns following HTTP status codes:
 * * 405 (Method Not Allowed) - when attempting to execute mutation or query through a GET request
 * * 400 (Bad Request) - any other exception
 */
fun StatusPagesConfig.defaultGraphQLStatusPages(): StatusPagesConfig {
    exception<Throwable> { call, cause ->
        when (cause) {
            is UnsupportedOperationException -> call.respond(HttpStatusCode.MethodNotAllowed)
            is InvalidPayloadException -> call.respond(HttpStatusCode.UnsupportedMediaType)
            else -> call.respond(HttpStatusCode.BadRequest)
        }
    }
    return this
}
