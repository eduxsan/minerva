package com.moricion.qa.reqresin.test;

import com.google.gson.JsonObject;
import com.moricion.qa.common.reporting.Assertion;
import com.moricion.qa.common.reporting.ReportingTestBase;
import com.moricion.qa.common.reporting.Step;
import com.moricion.qa.common.support.DataProviders;
import org.testng.ITestContext;
import org.testng.annotations.Test;

public class ReadInfoTest extends ReportingTestBase {

  @Test(dataProvider = "ReadInfoTest", dataProviderClass = DataProviders.class)
  public void demoTest(String testName, JsonObject data, ITestContext context){
    testDetail.setTestName(testName);
    Step getAllUsersResponse = getAllUsers();

    Assertion assertStatusCode = new Assertion("Was status code the expected", "HTTP/1.1 200 OK",getAllUsersResponse.getHttpStatus());
    assertAndReport(getAllUsersResponse, assertStatusCode);

    assertAll();
  }

}
