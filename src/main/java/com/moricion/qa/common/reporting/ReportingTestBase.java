package com.moricion.qa.common.reporting;

import io.restassured.response.Response;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

import static com.moricion.qa.common.support.ConstantData.*;
import static com.moricion.qa.common.webservices.rest.RestClient.getWithoutTokenNoAuth;
import static java.lang.String.format;
import static org.hamcrest.core.Is.is;

@Listeners({TestSuiteListener.class, TestListener.class})
public abstract class ReportingTestBase {

  private TestReport report = TestSuiteListener.getTestReport();
  protected TestDetail testDetail;

  @BeforeMethod(alwaysRun = true)
  public void beforeMethod(ITestResult result) {
    String testName = format("%s.%s_%d",
            result.getMethod().getRealClass().getSimpleName(),
            result.getMethod().getMethodName(),
            result.getMethod().getCurrentInvocationCount());
    testDetail = new TestDetail(testName);
    report.addTestDetail(testDetail);
    System.out.println("Starting Test " + testDetail.getTestName());
  }

  @AfterMethod(alwaysRun = true)
  public void afterMethod(ITestResult result) {
    testDetail.setResultAndColor(result);
  }

  /**
   * make assertion
   *
   * @param assertions list of Assertion objects
   * @param step       Step object
   * @param <T>
   */
  public <T> void assertAndReport(Step step, Assertion... assertions) {
    Matcher<? super T> matcher = is(true);
    testDetail.addStep(step);
    for (Assertion assertion : assertions) {
      try {
        step.addAssertion(assertion);
        String actual = assertion.getActual();
        String expected = assertion.getExpected();
        Description expectedDescription = new StringDescription()
                .appendText("\nExpected: ")
                .appendValue(expected);

        Description actualDescription = new StringDescription()
                .appendText("\nFound: ")
                .appendValue(actual);

           /* if (assertion.getClass() == ExistenceAssertion.class){ //for existence assertions, needs slightly different logic
                if (assertion.getActual() == null){
                    testDetail.errors.append(expectedDescription.toString() + actualDescription.toString());
                }
            }
            else*/
        if (!assertion.getExpected().equalsIgnoreCase(actual)) {
          testDetail.errors.append(expectedDescription.toString() + actualDescription.toString());
        }
      } catch (Exception e) {
        testDetail.errors.append(e.getMessage());
        e.printStackTrace();
      }
    }
  }

  /**
   * evaluate all assertions
   */
  public void assertAll() {
    String errorPrint = testDetail.errors.toString();
    if (errorPrint.length() > 0) {
      throw new AssertionError(errorPrint);
    }
  }

  protected void addTestDescription(String desc) {
    testDetail.setDescription(desc);
  }

  //region REQRESIN Steps

  /**
   * Perform a request to retrieve the info from all users
   * @return
   */
  protected Step getAllUsers(){
    Step step = new Step(GET_LIST_USERS_STEPNAME, NO_REQUEST_USER, NO_REQUEST_PWD,GET_LIST_USERS_ENDPOINT, NO_REQUEST_BODY);
    Response response = getWithoutTokenNoAuth(GET_LIST_USERS_ENDPOINT,null);
    step.setResponseBody(response.getBody().asString());
    step.setHttpStatus(response.getStatusLine().trim());
    return step;
  }
  // endregion
}
