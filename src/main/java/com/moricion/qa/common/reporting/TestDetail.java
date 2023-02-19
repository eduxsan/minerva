package com.moricion.qa.common.reporting;

import org.testng.ITestResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a single test run. Contains results, test name, parameters, steps, etc. Assertions and Logs are sub-objects of Steps.
 */
public class TestDetail implements Comparable<TestDetail> {

    private String testName = "";
    private List<String> parameters;
    private String description = "";
    private String result = "";
    private String resultColor = "";
    private List<String> screenshots; //holdover from mobile testing. Not used for inventory WS automation.
    private List<Step> steps = new ArrayList<>();
    private String jamaId = "";
    private String jamaURL = "";
    public StringBuilder errors = new StringBuilder();

    public TestDetail(String testName) {

        this.testName = testName;
        parameters = new ArrayList<>();
        screenshots = new ArrayList<>();
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public List<String> getParameters() {
        return parameters;
    }

    public void setParameters(List<String> parameters) {
        this.parameters = parameters;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getResultColor() {
        return resultColor;
    }

    public void setResultColor(String resultColor) {
        this.resultColor = resultColor;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public List<String> getScreenshots() {
        return screenshots;
    }

    public String getJamaId() {
        return jamaId;
    }

    public String getJamaURL() {
        return jamaURL;
    }

    /*
     * Convenience methods
     */


    public void addStep(Step step) {
        steps.add(step);
    }

    public void addParameter(String param) {
        parameters.add(param);
    }

    public void addScreenshot(String screenshot) {
        screenshots.add(screenshot);
    }

    public void setJamaIdAndURL(String id, String url) {
        this.jamaId = id;
        this.jamaURL = url;
    }

    public void setResultAndColor(ITestResult testResult) {
        String resultAsString;
        String color;
        switch (testResult.getStatus()) {
            case 1:
                resultAsString = "PASS";
                color = "9ACD32"; // green
                break;
            default:
                resultAsString = "FAIL";
                color = "FF6347"; // red
        }
        result = resultAsString;
        resultColor = color;
    }

    @Override
    public String toString() {
        return "TestDetail{" +
                "jamaId='" + jamaId + '\'' +
                ", testName='" + testName + '\'' +
                ", parameters=" + parameters +
                ", description='" + description + '\'' +
                ", result='" + result + '\'' +
                ", resultColor='" + resultColor + '\'' +
                ", steps=" + steps +
                ", screenshots=" + screenshots +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TestDetail detail = (TestDetail) o;
        return jamaId.equals(detail.jamaId) &&
                testName.equals(detail.testName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(jamaId, testName);
    }

    @Override
    public int compareTo(TestDetail t) {
        if (jamaId == null || t.getJamaId() == null) {
            return 0;
        }
        if (testName == null || t.getTestName() == null) {
            return 0;
        }
        return (jamaId + testName).compareTo(t.getJamaId() + t.getTestName());

    }
}

