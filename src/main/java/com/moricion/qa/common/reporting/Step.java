package com.moricion.qa.common.reporting;

import io.restassured.http.Header;

import java.util.ArrayList;
import java.util.List;

/**
 * This represents a single test step's output in the HTML report. Assertions and logs are attached to it.
 */
public class Step {

    private String stepName;
    private String username;
    private String password;
    private String endpoint;
    private String requestBody;
    private String httpStatus;
    private List < Assertion > assertions = new ArrayList < > ();
    private String responseBody;
    private List < String > headers; //this is only used for REST headers
    private String query;
    private List < QueryResult > queryResults;
    private List < List < QueryResult >> queryRowsResults;
    private String dataBase;
    private String table;
    private String[] column;
    private String searchParameter;

    /**
     * constructor without response body
     * @param stepName name of test
     * @param username user logged in as
     * @param password password for login
     * @param endpoint endpoint reached for request
     * @param requestBody request body
     */
    public Step(String stepName, String username, String password, String endpoint, String requestBody) {
        this.stepName = stepName;
        this.username = username;
        setPassword(password);
        this.endpoint = endpoint;
        this.requestBody = requestBody;
        //no headers by default
    }

    /**
     * constructor without response body
     *
     * @param stepName      name of test
     * @param username      user logged in as
     * @param password      password for login
     * @param endpoint      endpoint reached for request
     * @param query         request body
     */
    public Step(String stepName, String username, String password, String endpoint, String query, List < QueryResult > queryResults) {
        this.stepName = stepName;
        this.username = username;
        setPassword(password);
        this.endpoint = endpoint;
        this.query = query;
        this.queryResults = queryResults;
        //no headers by default
    }

    /**
     * constructor with text response body
     * @param stepName name of test
     * @param username user logged in as
     * @param password password for login
     * @param endpoint endpoint reached for request
     * @param requestBody request body
     * @param responseBody response body
     */
    public Step(String stepName, String username, String password, String endpoint, String requestBody, String responseBody) {
        this.stepName = stepName;
        this.username = username;
        setPassword(password);
        this.endpoint = endpoint;
        this.requestBody = requestBody;
        this.responseBody = responseBody;
    }

    /**
     * constructor for data base
     * @param stepName          String name of test case
     * @param dataBase          String name of database
     * @param username          String user name
     * @param searchParameter   String name of parameter to search
     * @param table             String name of the table database
     * @param column            String name of the column database
     * @param query             String query
     */
    public Step(String stepName, String dataBase, String username, String searchParameter, String table, String query, String[] column) {
        this.stepName = stepName;
        this.username = username;
        this.dataBase = dataBase;
        this.searchParameter = searchParameter;
        this.table = table;
        this.column = column;
        this.query = query;
    }

    public String getStepName() {
        return stepName;
    }

    public void setStepName(String stepName) {
        this.stepName = stepName;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }

    @Override
    public String toString() {
        return "STEP{" + "NAME: " +
                stepName + ", User: " +
                username + ", Password: " +
                password + ", Endpoint: " +
                endpoint + ", Step Details: " +
                requestBody + ", Step Results: " +
                responseBody + ", Assertions : " +
                '}';
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    /**
     * censors the password as asterixes
     * @param password String password string
     */
    public void setPassword(String password) {
        this.password = "************";
    }

    public List < Assertion > getAssertions() {
        return assertions;
    }

    public void setAssertions(List < Assertion > assertions) {
        this.assertions = assertions;
    }

    /**
     * add an assertion
     * @param assertion an assertion
     */
    public void addAssertion(Assertion assertion) {
        this.assertions.add(assertion);
    }

    public String getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }

    public List < String > getHeaders() {
        return headers;
    }

    public void setHeaders(List < String > headers) {
        this.headers = headers;
    }
    public void addHeader(Header header) {
        headers.add(header.toString());
    }

    public String getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(String httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public List < QueryResult > getQueryResults() {
        return queryResults;
    }

    public void setQueryResults(List < QueryResult > queryResults) {
        this.queryResults = queryResults;
    }

    public List < List < QueryResult >> getQueryRowsResults() {
        return queryRowsResults;
    }

    public void setQueryRowsResults(List < List < QueryResult >> queryRowsResults) {
        this.queryRowsResults = queryRowsResults;
    }

    public void setDataBase(String dataBase) {
        this.dataBase = dataBase;
    }

    public String getDataBase() {
        return dataBase;
    }

    public void setSearchParameter(String searchParameter) {
        this.searchParameter = searchParameter;
    }

    public String getSearchParameter() {
        return searchParameter;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getTable() {
        return table;
    }
}