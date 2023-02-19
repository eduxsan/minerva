package com.moricion.qa.common.reporting;

public class Assertion {
    String reason;
    String expected;
    String actual;

    /**
     *
     * @param reason String to display as the assertion title
     * @param expected String expected result
     * @param actual String actual result
     */
    public Assertion(String reason, String expected, String actual) {
        this.reason = reason;
        this.expected = expected;
        this.actual = actual;
    }

    public Assertion() {}

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getExpected() {
        return expected;
    }

    public void setExpected(String expected) {
        this.expected = expected;
    }

    public String getActual() {
        return actual;
    }

    public void setActual(String actual) {
        this.actual = actual;
    }

    @Override
    public String toString() {
        return "Assertion{" +
                "Reason='" + reason + '\'' +
                ", expected='" + expected + '\'' +
                ", actual='" + actual + '\'' +
                '}';
    }
}