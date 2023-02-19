package com.moricion.qa.common.webservices.rest;

import com.moricion.qa.common.support.Enumerations;
import com.moricion.qa.common.webservices.WebServicesBase;
import io.restassured.http.Method;
import io.restassured.response.Response;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import static com.moricion.qa.common.support.ConstantData.ENV;
import static io.restassured.RestAssured.given;
import static java.lang.String.format;

/**
 * This class manipulates all the API calls to be performed
 */
public class RestClient extends WebServicesBase {

  /**
   *
   * Make a request to an endpoint to the main WS of the test suite. i.e. "env" value from configuration.yaml file
   * The WS url have to be set in wsUrl parameter
   *
   * @param body                  String JSON body of the request
   * @param user                  String User to be authenticated
   * @param password              String Password oif the user to be authenticated
   * @param endpoint              String endpoint url. Just the endpoint.
   * @param method                Enumeration value for valid request verbs
   * @param token                 String token from earlier login
   * @param protocolType          Enumeration value for valid http protocols
   * @param noAuthorization       Boolean value in order to determine if the request needs authentication
   * @return                      Response object with the elements of the request performed
   */
  private static Response submit(String body, String user, String password, String endpoint, Method method, String token, Enumerations.ProtocolType protocolType, boolean noAuthorization) {
    return submit(body, user, password, endpoint, method, token, protocolType, noAuthorization, null);
  }

  /**
   * Make a request to an endpoint that NOT belongs to the main WS of the test suite
   * The WS url have to be set in wsUrl parameter
   *
   * @param body                  String JSON body of the request
   * @param user                  String User to be authenticated
   * @param password              String Password oif the user to be authenticated
   * @param endpoint              String endpoint url. Just the endpoint.
   * @param method                Enumeration value for valid request verbs
   * @param token                 String token from earlier login
   * @param protocolType          Enumeration value for valid http protocols
   * @param noAuthorization       Boolean value in order to determine if the request needs authentication
   * @param wsUrl                 String Domain in order to use a different WS of the main WS to be tested. i.e. different value from "env" from configuration.yaml file
   * @return                      Response object with the elements of the request performed
   */
  private static Response submit(String body, String user, String password, String endpoint, Method method, String token, Enumerations.ProtocolType protocolType, boolean noAuthorization, String wsUrl) {
    String url = "";
    String env = wsUrl == null ? ENV : wsUrl;
    if (protocolType.equals(Enumerations.ProtocolType.HTTPS))
      url = env + endpoint;
    else
      url = env + endpoint;

    Map<String, String> headers = new HashMap<>();
    String encoding = Base64.getEncoder().encodeToString(format("%s:%s", user, password).getBytes());
    headers.put(ACCEPT, APPLICATION_JSON_UTF8);
    headers.put(CONTENT_TYPE, APPLICATION_JSON);
    if (token != null)
      headers.put("token", token);

    if (!noAuthorization) {
      headers.put(AUTHORIZATION, "Basic " + encoding);
      System.out.println("REQUEST URL: " + url);
    }
    try {
      if (body != null)
        System.out.println("RAW REQUEST: " + body);
      Response response = null;
      //have to use big if-else branch because switch does not play nicely with RestAssured enums
      if (method == Method.POST) {
        if (body != null)
          response = given().with().headers(headers).body(body).post(url);
        else
          response = given().with().headers(headers).post(url);
      } else if (method == Method.PUT) {
        if (body != null)
          response = given().with().headers(headers).body(body).put(url);
        else
          response = given().with().headers(headers).put(url);
      } else if (method == Method.GET) {
        try {
          response = given().with().headers(headers).get(url);
        } catch (Exception e) {
          response = null;
        }
      } else if (method == Method.DELETE) {
        response = given().with().headers(headers).delete(url);
      }
      System.out.println("RAW RESPONSE: " + response.getBody().asString());
      return response;
    } catch (NullPointerException e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Make a GET request to an endpoint that NOT belongs to the main WS of the test suite. i.e. "env" value from configuration.yaml file
   * The WS url have to be set in wsUrl parameter
   *
   * @param body                  body object
   * @param user                  String User to be authenticated
   * @param password              String Password oif the user to be authenticated
   * @param endpoint              String endpoint url. Just the endpoint.
   * @param wsUrl                 String Domain in order to use a different WS of the main WS to be tested. i.e. different value from "env" from configuration.yaml file
   * @return                      Response object
   */
  public static Response getWithoutToken(String body, String user, String password, String endpoint, String wsUrl) {
    return getWithToken(body, user, password, endpoint, null, wsUrl);
  }

  /**
   * Make a GET request to an endpoint that NOT belongs to the main WS of the test suite. i.e. "env" value from configuration.yaml file
   * The WS url have to be set in wsUrl parameter
   *
   * @param endpoint              String endpoint url. Just the endpoint.
   * @param wsUrl                 String Domain in order to use a different WS of the main WS to be tested. i.e. different value from "env" from configuration.yaml file
   * @return                      Response object
   */
  public static Response getWithoutTokenNoAuth(String endpoint, String wsUrl) {
    return submit(null, null, null, endpoint, Method.GET, null, Enumerations.ProtocolType.HTTPS, true, wsUrl);
  }

  /**
   * Make a get request to an endpoint that does not require authentication
   *
   * @param endpoint
   * @return
   */
  public static Response getWithoutTokenAndHTTP(String user, String password, String endpoint, boolean withNoAuthorization) {
    return getWithToken(user, password, endpoint, null, Enumerations.ProtocolType.HTTP, withNoAuthorization);
  }

  /**
   * Make a GET request to an endpoint that NOT belongs to the main WS of the test suite. i.e. "env" value from configuration.yaml file
   * The WS url have to be set in wsUrl parameter
   *
   * @param body                  String JSON body of the request
   * @param user                  String User to be authenticated
   * @param password              String Password oif the user to be authenticated
   * @param endpoint              String endpoint url. Just the endpoint.
   * @param wsUrl                 String Domain in order to use a different WS of the main WS to be tested. i.e. different value from "env" from configuration.yaml file
   * @return                      Response object
   */
  public static Response getWithToken(String body, String user, String password, String endpoint, String token, String wsUrl) {
    return submit(body, user, password, endpoint, Method.GET, token, Enumerations.ProtocolType.HTTPS, false, wsUrl);
  }

  /**
   * Make a PUT request to an endpoint that NOT belongs to the main WS of the test suite. i.e. "env" value from configuration.yaml file
   * The WS url have to be set in wsUrl parameter
   *
   * @param body                  body object
   * @param user                  String User to be authenticated
   * @param password              String Password oif the user to be authenticated
   * @param endpoint              String endpoint url. Just the endpoint.
   * @param wsUrl                 String Domain in order to use a different WS of the main WS to be tested. i.e. different value from "env" from configuration.yaml file
   * @return                      Response object
   */
  public static Response putWithoutToken(String body, String user, String password, String endpoint, String wsUrl) {
    return putWithToken(body, user, password, endpoint, null, wsUrl);
  }

  /**
   * Make a PUT request to an endpoint that NOT belongs to the main WS of the test suite. i.e. "env" value from configuration.yaml file
   * The WS url have to be set in wsUrl parameter
   *
   * @param body                  String JSON body of the request
   * @param user                  String User to be authenticated
   * @param password              String Password oif the user to be authenticated
   * @param endpoint              String endpoint url. Just the endpoint.
   * @param wsUrl                 String Domain in order to use a different WS of the main WS to be tested. i.e. different value from "env" from configuration.yaml file
   * @return                      Response object
   */
  public static Response putWithToken(String body, String user, String password, String endpoint, String token, String wsUrl) {
    return submit(body, user, password, endpoint, Method.PUT, token, Enumerations.ProtocolType.HTTPS, false, wsUrl);
  }

  /**
   * Make a GET request to an endpoint that requires authentication
   *
   * @param endpoint String endpoint url. Just the endpoint.
   * @param token    String token from earlier login
   * @return
   */
  public static Response getWithToken(String user, String password, String endpoint, String token, Enumerations.ProtocolType protocolType, boolean withNoAuthorization) {
    return submit(null, user, password, endpoint, Method.GET, token, protocolType, withNoAuthorization);
  }

  /**
   * Make a request to an endpoint that NOT belongs to the main WS of the test suite. i.e. "env" value from configuration.yaml file
   * The WS url have to be set in wsUrl parameter
   *
   * @param body                  body object
   * @param user                  String User to be authenticated
   * @param password              String Password oif the user to be authenticated
   * @param endpoint              String endpoint url. Just the endpoint.
   * @param wsUrl                 String Domain in order to use a different WS of the main WS to be tested. i.e. different value from "env" from configuration.yaml file
   * @return                      Response object
   */
  public static Response postWithoutToken(String body, String user, String password, String endpoint, String wsUrl) {
    return postWithToken(body, user, password, endpoint, null, wsUrl);
  }

  /**
   * Make a POST request to an endpoint that requires authentication
   *
   * @param body        String JSON body of the request
   * @param endpoint    String endpoint url. Just the endpoint.
   * @return
   */
  public static Response postWithoutToken(String body, String user, String password, String endpoint) {
    return postWithToken(body, user, password, endpoint, null);
  }

  /**
   * Make a request to an endpoint that NOT belongs to the main WS of the test suite. i.e. "env" value from configuration.yaml file
   * The WS url have to be set in wsUrl parameter
   *
   * @param body                  String JSON body of the request
   * @param user                  String User to be authenticated
   * @param password              String Password oif the user to be authenticated
   * @param endpoint              String endpoint url. Just the endpoint.
   * @param wsUrl                 String Domain in order to use a different WS of the main WS to be tested. i.e. different value from "env" from configuration.yaml file
   * @return                      Response object
   */
  public static Response postWithToken(String body, String user, String password, String endpoint, String token, String wsUrl) {
    return submit(body, user, password, endpoint, Method.POST, token, Enumerations.ProtocolType.HTTPS, false, wsUrl);
  }

  /**
   * Make a POST request to an endpoint that requires authentication
   *
   * @param body        String JSON body of the request
   * @param endpoint    String endpoint url. Just the endpoint.
   * @param token       String token from earlier login
   * @return
   */
  public static Response postWithToken(String body, String user, String password, String endpoint, String token) {
    return submit(body, user, password, endpoint, Method.POST, token, Enumerations.ProtocolType.HTTPS, false);
  }

}
