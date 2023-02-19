package com.moricion.qa.common.reporting;

import org.json.simple.parser.ParseException;
import org.testng.ISuite;
import org.testng.ISuiteListener;

public class TestSuiteListener implements ISuiteListener {

    private static ReportEngine reportEngine = new ReportEngine("reportTemplate.ftlh","Report_Automated_Test.html");
    private static TestReport testReport;

    static {
        try {
            testReport = new TestReport("API WS","git");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStart(ISuite suite) {
        System.out.println("TestSuite Started");
    }

    @Override
    public void onFinish(ISuite suite) {
        System.out.println("TestSuite Finished");
        reportEngine.generateReport(testReport);

    }

    public static TestReport getTestReport() {
        return testReport;
    }
}
