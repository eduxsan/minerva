package com.moricion.qa.common.support;

public class Enumerations {

  /**
   * Type of message that will be defined within a push notification
   */
  public enum NotificationMessageType {
    Body,
    Subject
  }

  /**
   * Enum for defining the test case type in order to build its assertion
   */
  public enum TestCaseType {
    Positive,
    Negative
  }

  /**
   * Enumeration to define the protocol to send the request
   */
  public enum ProtocolType{
    HTTPS,
    HTTP
  }
}
