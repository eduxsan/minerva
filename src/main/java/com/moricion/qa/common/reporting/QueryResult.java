package com.moricion.qa.common.reporting;

/**
 * <h1>Object model class!</h1>
 * Class model that encapsulate the DB query results as key-value approach
 * The class help to print out the results in the HTML report
 *
 * @author  Jonathan Saldivar
 * @version 1.0
 * @since   01-12-2021
 */
public class QueryResult {
  private String column;
  private String value;

  public String getColumn() {
    return column;
  }

  public void setColumn(String column) {
    this.column = column;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }
}