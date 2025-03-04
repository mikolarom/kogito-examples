/*
 * Copyright 2022 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.acme;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusIntegrationTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

@QuarkusIntegrationTest
@QuarkusTestResource(MockServices.class)
class StockProfitIT {

    @BeforeAll
    static void init() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    void stockProfit() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .body(
                        Collections
                                .singletonMap(
                                        "workflowdata",
                                        Collections.singletonMap("symbol", "KGTO")))
                .post("/stockprofit")
                .then()
                .statusCode(201)
                .body("id", notNullValue())
                .body("workflowdata.symbol", is("KGTO"))
                .body("workflowdata.currentPrice", is(110F))
                .body("workflowdata.profit", is("10%"));
    }
}
