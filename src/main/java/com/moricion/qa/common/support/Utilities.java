package com.moricion.qa.common.support;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import static com.moricion.qa.common.support.ConstantData.*;

/**
 * @author Eduardo Sanchez eduxsan@outlook.com
 * @version 1.0
 */
public class Utilities {

  /**
   * search for a specific value in a json
   *
   * @param SearchObject String json to be searched
   * @param SearchTerm   String search keywords
   * @return
   * @throws ParseException
   */
  public static String specificSearchValue(String SearchObject, String SearchTerm) throws ParseException {
    JSONParser parser = new JSONParser();
    String[] Terms = SearchTerm.split("\\.");
    JSONObject Object = (JSONObject) parser.parse(SearchObject); //parse response body
    return searchJSONObject(Object, Terms);
  }

  /**
   * this will parse through a JSON array and return the key value pairs
   *
   * @param jsonObject  JSONObject json to be searched
   * @param searchTerms String[] search keywords
   * @return
   */
  public static String searchJSONObject(JSONObject jsonObject, String... searchTerms) {
    try {
      for (int i = 0; i < searchTerms.length; i++) {
        String searchTerm = searchTerms[i];
        Class objectClass = jsonObject.get(searchTerm).getClass();
        if (objectClass == Long.class || objectClass == String.class || objectClass == Boolean.class) { //expecting value to be a Long, String or Boolean, may have to update this later to include other types
          return jsonObject.get(searchTerm).toString();
        } else if (jsonObject.get(searchTerm).getClass() == JSONArray.class) //to parse something like errors:[] which will become a JSONArray and not a string or a JSONObject
        {
          if (i == searchTerms.length - 1) { //no more searchterms left, no more searching possible
            JSONArray array = (JSONArray) jsonObject.get(searchTerm);
            try {
              return array.get(0).toString();
            } catch (IndexOutOfBoundsException e) { // in the event the array is empty
              return null;
            }
          } else {
            return searchJSONArray((JSONArray) jsonObject.get(searchTerm), Arrays.copyOfRange(searchTerms, i + 1, searchTerms.length)); //remaining searchterms
          }
        } else { //if not a Long or a String, it's probably a nested JSON, in which case we will search with the next search term
          jsonObject = (JSONObject) jsonObject.get(searchTerm);
        }
      }
    } catch (NullPointerException e) {
      //will return null in the event of nullpointerexception
    }
    return null;
  }

  /**
   * Retrieve a single value from a Json array.
   *
   * @param jsonArray   JSONArray json to be searched
   * @param searchTerms String[] search keyword
   * @return
   */
  public static String searchJSONArray(JSONArray jsonArray, String... searchTerms) {
    for (int i = 0; i < jsonArray.size(); i++) {
      if (jsonArray.get(i).getClass() == JSONObject.class) {
        return searchJSONObject((JSONObject) jsonArray.get(i), searchTerms);
      }
    }
    return null;
  }

  //region Date Time

  /**
   * Get today's date
   *
   * @return today's date in YYYY-MM-dd format
   */
  public static String todaysDate() {
    return todaysDate("YYYY-MM-dd");
  }

  /**
   * Get today's date
   *
   * @param format String format of the date, i.e. YYYY-MM-dd
   * @return today's date in YYYY-MM-dd format
   */
  public static String todaysDate(String format) {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
    Date date = new Date();
    return simpleDateFormat.format(date);
  }

  /**
   * Method to get the datetime in "yyyy-MM-dd HH:mm:ss" format
   *
   * @param hour String hour
   * @param year String year
   * @return
   */
  public static String getDateTime(String hour, String year) {
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Calendar cal = Calendar.getInstance();
    cal.add(Calendar.HOUR, Integer.parseInt(hour));
    cal.add(Calendar.YEAR, Integer.parseInt(year));
    return String.format(dateFormat.format(cal.getTime()));
  }
  //endregion

  /***
   * Generates a simple unique username based on datetime.
   * @return a username with format userYYYYMMddHHmm@la-moricion.com
   */
  public static String simpleUniqueUsername() {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYYMMddHHmm");
    Date date = new Date();
    return String.format("user%s@la-moricion.com", simpleDateFormat.format(date));
  }

  /**
   * Retrieves the date of today plus N days in the future.
   *
   * @param days amount of days to add
   * @return date in format YYYY-MM-dd
   */
  public static String getFutureDate(int days) {
    Calendar calendar = Calendar.getInstance();
    calendar.add(Calendar.DAY_OF_YEAR, days);
    Date tomorrowsDate = calendar.getTime();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd");
    return simpleDateFormat.format(tomorrowsDate);
  }

  /**
   * Retrieves a list of string with quotes
   * <code>foo,bar,baz,qux</code> to <code>"foo","bar","baz","qux"</code>
   *
   * @param list      String list of items
   * @param separator String character separator
   * @return string of quoted items separated by <code>separator<code>
   */
  public static String getQuotedListFromStringArray(String list, String separator) {
    String quotedList = "";
    String[] items = list.split(separator);
    for (int i = 0; i < items.length; i++) {
      quotedList = quotedList.concat("\"" + items[i] + "\"");
      if (i < items.length - 1) {
        quotedList = quotedList.concat(separator);
      }
    }
    return quotedList;
  }

  /**
   * Define if the CSV files path will be related to AT-QA, QA, or DEV env, if env in configuration contains at-qa the path will be related to atQA folder
   *
   * @return String   Path where the test data will be taken
   */
  public static String getJSONFilesPath() {
    Configuration.getConfiguration(CONFIGURATION_YAML).getEnv();
    String path = "";

    if (Configuration.getConfiguration(CONFIGURATION_YAML).getEnv().matches(DEV_ENV_REGEX)) {
      path = DEV_FILES_PATH;
    } else if (Configuration.getConfiguration(CONFIGURATION_YAML).getEnv().matches(AUT_FILES_PATH)) {
      path = AUT_FILES_PATH;
    } else if (Configuration.getConfiguration(CONFIGURATION_YAML).getEnv().matches(UAT_ENV_REGEX)) {
      path = UAT_FILES_PATH;
    } else {
      path = QA_FILES_PATH;
    }
    return path;
  }
}
