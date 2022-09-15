package org.example.service;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

abstract class CommonService {

    protected RequestSpecification requestSpecification;
    private String baseUri;
    private final Function<String, String> prepareUri = uri -> baseUri.concat(uri);

    public CommonService(String baseUri, String apiKey) {
        this.baseUri = baseUri;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        this.requestSpecification = RestAssured.given().auth().oauth2(apiKey);
        setCommonParams();
    }

    protected void setCommonParams() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");
        headers.put("Content-Type", "application/json");
        requestSpecification.headers(headers);
    }

    protected Response get(String uri, int expectedCode) {
        return requestSpecification.expect().statusCode(expectedCode).log().ifError()
                .when().get(prepareUri.apply(uri));
    }

    protected Response get(String uri) {
        return requestSpecification.expect().statusCode(HttpStatus.SC_OK).log().ifError()
                .when().get(prepareUri.apply(uri));
    }

    protected Response post(String uri, Object body) {
        return requestSpecification.body(body).expect().statusCode(HttpStatus.SC_OK).log().ifError()
                .when().post(prepareUri.apply(uri));
    }

    protected Response put(String uri, Object body) {
        return requestSpecification.body(body).expect().statusCode(HttpStatus.SC_OK).log().ifError()
                .when().put(prepareUri.apply(uri));
    }

    protected void delete(String uri) {
        requestSpecification.expect().statusCode(HttpStatus.SC_OK).log().ifError()
                .when().delete(prepareUri.apply(uri));
    }
}
