package com.moricion.qa.common.support;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import static com.moricion.qa.common.support.ConstantData.*;

public class JSONReader {

  /**
   * @param fileName              String name of the file to read
   * @param dataProviderName      String name of the data provider
   * @return                      Bi-dimensional Object array
   * @throws IOException
   */
  public static Object[][] readJSON(String fileName, String dataProviderName) throws IOException {
    Map<String, JsonObject> dataMap = new HashMap<String, JsonObject>();
    Iterator entriesIterator = null;

    JsonObject dataObj = retrieveJSONDataFromFile(fileName, "dataProviders");
    JsonObject testCases = (JsonObject) dataObj.get(dataProviderName);


    testCases.keySet().forEach(testCase -> {
      ((JsonArray) ((JsonObject) (testCases.get(String.valueOf(testCase)))).get("steps")).forEach(step -> {
        ((JsonObject) ((JsonObject) step).get("data")).keySet().forEach(key -> {
          String value = ((JsonObject) ((JsonObject) step).get("data")).get(String.valueOf(key)).getAsString();
          if (value.contains("/")) {
            String[] fileKey = value.split("/");
            ((JsonObject) ((JsonObject) step).get("data")).add(
                    String.valueOf(key),
                    retrieveJSONDataFromFile(fileKey[0], fileKey[1]));
          }
        });
        ((JsonObject) ((JsonObject) step).get("expected")).keySet().forEach(key -> {
          String value = ((JsonObject) ((JsonObject) step).get("expected")).get(String.valueOf(key)).getAsString();
          if (value.contains("/")) {
            String[] fileKey = value.split("/");
            ((JsonObject) ((JsonObject) step).get("expected")).add(
                    String.valueOf(key),
                    retrieveJSONDataFromFile(fileKey[0], fileKey[1]));
          }
        });
      });

      dataMap.put(String.valueOf(testCase), ((JsonObject) (testCases.get(String.valueOf(testCase)))));
    });

    Set entries = testCases.entrySet();
    entriesIterator = entries.iterator();


    Object[][] arr = new Object[dataMap.size()][2];
    int i = 0;
    while (entriesIterator.hasNext()) {
      Map.Entry mapping = (Map.Entry) entriesIterator.next();
      arr[i][0] = mapping.getKey();
      arr[i][1] = mapping.getValue();

      i++;
    }

    return arr;
  }

  /**
   * @param dataKey
   * @param testCaseDataObjects
   * @return
   */
  private static Map<String, JsonObject> getDataByKey(String dataKey, JsonObject testCaseDataObjects) {
    Map<String, JsonObject> testCaseMap = new HashMap<String, JsonObject>();
    for (String testCase : testCaseDataObjects.keySet()) {
      JsonObject dataElements = new JsonObject();
      for (Object dataset : (JsonArray) ((JsonObject) testCaseDataObjects.get(testCase)).get(dataKey)) {
        String file = ((JsonObject) dataset).get("file").getAsString();
        String keyValue = ((JsonObject) dataset).get("value").getAsString();
        JsonObject data = retrieveJSONDataFromFile(file, keyValue);
        data.keySet().forEach(key -> {
          dataElements.add(String.valueOf(key), data.get(key));
        });
      }
      ((JsonObject) testCaseDataObjects.get(testCase)).add(dataKey, dataElements);
      testCaseMap.put(String.valueOf(testCase), (JsonObject) testCaseDataObjects.get(testCase));
    }
    return testCaseMap;
  }

  /**
   * @param fileName
   * @param keyValue
   * @return
   */
  private static JsonObject retrieveJSONDataFromFile(String fileName, String keyValue) {
    JsonObject dataObj = new JsonObject();
    try {
      Gson gson = new Gson();
      Reader reader = Files.newBufferedReader(Paths.get(JSON_FILES_PATH + fileName + EXTENSION));
      //JsonObject contentObj = (JsonObject) parser.parse(new FileReader(FILES_PATH + fileName + EXTENSION));
      HashMap<?,?> map = gson.fromJson(reader,HashMap.class);
      dataObj = ((JsonObject) gson.toJsonTree(map)).getAsJsonObject(keyValue);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return dataObj;
  }
}
