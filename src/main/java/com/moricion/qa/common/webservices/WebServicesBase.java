package com.moricion.qa.common.webservices;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;

import static java.lang.String.format;

public abstract class WebServicesBase {

    protected ObjectMapper mapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    protected static final String CONTENT_TYPE = "Content-Type";
    protected static final String ACCEPT = "accept";
    protected static final String APPLICATION_JSON = "application/json";
    protected static final String APPLICATION_JSON_UTF8 = "application/json;charset=UTF-8";
    protected static final String AUTHORIZATION = "Authorization";
    protected static final String X_AUTH_TOKEN = "x-auth-token";

    protected void print(String url, String request, Response response) {
        System.out.println(format("** URL: %s", url));
        System.out.println(format("** REQUEST: %s", request));
        System.out.println(format("** STATUS: %d", response.statusCode()));
        System.out.println(format("** RESPONSE: %s", response.asString()));
        System.out.println();
    }

    protected String toJSON(Object o) throws WebServicesException {
        try {
            return mapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            throw new WebServicesException(format("Unable to parse %s to JSON", o.toString()), e);
        }
    }
}