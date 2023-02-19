package com.moricion.qa.common.testData.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;

  public class Step {

    @JsonProperty("data")
    private Map<String, Object> data;
    @JsonProperty("expected")
    private Map<String, Object> expected;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("data")
    public Map<String, Object> getData() {
      return data;
    }

    @JsonProperty("data")
    public void setData(Map<String, Object> data) {
      this.data = data;
    }

    @JsonProperty("expected")
    public Map<String, Object> getExpected() {
      return expected;
    }

    @JsonProperty("expected")
    public void setExpected(Map<String, Object> expected) {
      this.expected = expected;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
      return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
      this.additionalProperties.put(name, value);
    }
  }
