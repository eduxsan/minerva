package com.moricion.qa.common.support;

import org.testng.annotations.DataProvider;

import java.io.IOException;

public class DataProviders {
  @DataProvider(name = "ReadInfoTest")
  public Object[][] demoDataProvider() throws IOException {
    return JSONReader.readJSON("ReadInfoTest","demoDataProvider");
  }
}
