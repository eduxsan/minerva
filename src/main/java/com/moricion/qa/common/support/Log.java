package com.moricion.qa.common.support;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import static com.moricion.qa.common.support.ConstantData.*;

public class Log {
  private static Logger log = LogManager.getLogger(Log.class);

  public static void startTestCase(String testCaseName) {
    log.info(START_TEST_CASE_MESSAGE);
    log.info(String.format(START_TEST_CASE_MESSAGE_NAME, testCaseName));
  }

  public static void finishTestCase(String testCaseName) {
    log.info(FINISH_TEST_CASE_MESSAGE);
    log.info(String.format(START_TEST_CASE_MESSAGE_NAME, testCaseName));
  }

  public static void info(String message) {
    Log.info(message);
  }

  public static void warn(String message) {
    Log.warn(message);
  }

  public static void error(String message) {
    Log.error(message);
  }

  public static void fatal(String message) {
    Log.fatal(message);
  }

  public static void debug(String message) {
    Log.debug(message);
  }

}