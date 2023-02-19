package com.moricion.qa.common.reporting;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.math.BigDecimal;

public class TestListener  implements ITestListener {

    private static TestReport report = TestSuiteListener.getTestReport();

    @Override
    public void onTestStart(ITestResult result) {}

    @Override
    public void onTestSuccess(ITestResult result) {}

    @Override
    public void onTestFailure(ITestResult result) {}

    @Override
    public void onTestSkipped(ITestResult result) {}

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {}

    @Override
    public void onStart(ITestContext context) {}

    @Override
    public void onFinish(ITestContext context) {
        int pass = context.getPassedTests().size();
        int fail = context.getFailedTests().size();
        int total = pass + fail;
        BigDecimal passRate = new BigDecimal(Double.toString((new Double(pass) / new Double(total)) * 100D)).setScale(2, BigDecimal.ROUND_UP);
        BigDecimal failRate = new BigDecimal(Double.toString((new Double(fail) / new Double(total)) * 100D)).setScale(2, BigDecimal.ROUND_UP);

        report.setTotalTest(Integer.toString(total));
        report.setPass(String.valueOf(pass));
        report.setFail(String.valueOf(fail));
        report.setPassRate(passRate.toString());
        report.setFailRate(failRate.toString());
    }


}
