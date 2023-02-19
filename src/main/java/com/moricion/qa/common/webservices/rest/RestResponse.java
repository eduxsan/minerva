package com.moricion.qa.common.webservices.rest;

import com.moricion.qa.common.support.Utilities;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

/**
 * object representing a REST response from the inventory web service. Most fields do not have public mutators,
 * because there is no point to changing the response.
 */
public class RestResponse {
  Map<String, String> elements; //hashmap of json properties
  String httpStatus;// HTTP status given from the response
  String responseBody; //response body- this is what you should be printing

  /**
   * parse a restassured response
   * @param response response RestAssured Response object
   */
  public RestResponse(Response response, String... keys){
    elements = new HashMap<>();
    responseBody = response.getBody().asString();
    httpStatus = response.getStatusLine().trim();
    for (int i= 0; i< keys.length;){
      try {
        elements.put(keys[i], Utilities.specificSearchValue(responseBody, keys[i]));
      }
      catch(Exception e){
        System.out.println(keys[i] + " could not be found in the response!");
      }
    }
  }


  /**
   * @return
   */
  public String getResponseBody() {
    return responseBody;
  }
}
