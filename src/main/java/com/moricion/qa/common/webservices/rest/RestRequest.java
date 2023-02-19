package com.moricion.qa.common.webservices.rest;


import org.json.simple.parser.ParseException;

import java.util.HashMap;
import java.util.Map;

public abstract class RestRequest {
  Map<String, String> elements; //hashmap of json properties

  /**
   * creates a request object from a comma-separated String input
   * @param rawInput An input that is divided with COMMAS, not semicolons
   * @param keys an array of Json keys (included in every extending class)
   * @throws ParseException
   */
  public RestRequest(String rawInput, String... keys) {
    elements = new HashMap<>();
    String[] inputArray = rawInput.split(",");
    for (int i= 0; i< keys.length;i++){
      if (inputArray[i].isEmpty() || inputArray[i].equalsIgnoreCase("null"))
        continue;
      elements.put(keys[i], inputArray[i]);
    }
  }

  public abstract String asJson();

  public String getValue(String key){
    return elements.get(key);
  }

  /**
   * print this object as a JSON. More may be needed to be added for some requests.
   * @return
   */
  public String buildJson(){
    StringBuilder json = new StringBuilder("{");
    int i = elements.size();
    for (Map.Entry<String, String> element: elements.entrySet()){
      json.append("\"" + element.getKey() + "\": \"" + element.getValue() + "\"");
      if (--i != 0) //if not the final element
        json.append(", ");
    }
    json.append("}");
    return json.toString();
  }
}