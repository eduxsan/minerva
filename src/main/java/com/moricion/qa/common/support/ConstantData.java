package com.moricion.qa.common.support;

import static com.moricion.qa.common.support.Utilities.getJSONFilesPath;

public class ConstantData {

  //region GLOBAL CONSTANTS
  public static final String CONFIGURATION_YAML = "src/main/resources/configuration.yaml";
  public static final String NO_REQUEST_USER = "No user";
  public static final String NO_REQUEST_PWD = "No pwd";
  public static final String NO_REQUEST_BODY = "(no request body)";
  public static final String ENV = Configuration.getConfiguration(CONFIGURATION_YAML).getEnv();
  //endregion

  //region DATAPROVIDERS
  public static final String DEV_ENV_REGEX = "^.*dev.*$";
  public static final String QA_ENV_REGEX = "^.*qa.*$";
  public static final String UAT_ENV_REGEX = "^.*uat.*$";
  public static final String AUT_ENV_REGEX = "^.*aut.*$";
  //endregion

  //region FILES
  public static final String JSON_FILES_PATH = getJSONFilesPath();
  public static final String BASE_FILES_PATH = "src/main/java/com/moricion/qa/common/testData/";
  public static final String QA_FILES_PATH = BASE_FILES_PATH + "QA/";
  public static final String AUT_FILES_PATH = BASE_FILES_PATH + "AUT/";
  public static final String DEV_FILES_PATH = BASE_FILES_PATH + "DEV/";
  public static final String UAT_FILES_PATH = BASE_FILES_PATH + "UAT/";
  public static final String EXTENSION = ".json";
  public static final String TESTS_RESULTS_JSON_FILE = "src/main/resources/tests_results.json";
  //endregion

  //region MESSAGES
  public static final String START_TEST_CASE_MESSAGE = "**** Start test case ****";
  public static final String FINISH_TEST_CASE_MESSAGE = "**** Finish test case ****";
  public static final String START_TEST_CASE_MESSAGE_NAME = "**** %s ****";
  //endregion

  //region ENDPOINTS

  public static final String GET_LIST_USERS_STEPNAME = "GET - Retrieve all users";
  public static final String GET_LIST_USERS_CONDITIONAL_STEPNAME = "GET - Retrieve all users from specific page";
  public static final String GET_SINGLE_USER_STEPNAME = "GET - Get user by id";

  public static final String GET_LIST_USERS_ENDPOINT = "/api/users";
  public static final String GET_LIST_USERS_PAGE_CONDITIONAL_ENDPOINT = GET_LIST_USERS_ENDPOINT + "?page=%s";
  public static final String GET_SINGLE_USER_ENDPOINT = GET_LIST_USERS_ENDPOINT + "/%s";
  //endregion
}
