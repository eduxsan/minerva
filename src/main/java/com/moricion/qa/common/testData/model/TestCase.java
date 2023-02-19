package com.moricion.qa.common.testData.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestCase {
    @JsonProperty("name")
    private String name;
    @JsonProperty("description")
    private String description;
    @JsonProperty("steps")
    private List<Step> steps = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("name")
    public String getName() {
      return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
      this.name = name;
    }

    @JsonProperty("description")
    public String getDescription() {
      return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
      this.description = description;
    }

    @JsonProperty("steps")
    public List<Step> getSteps() {
      return steps;
    }

    @JsonProperty("steps")
    public void setSteps(List<Step> steps) {
      this.steps = steps;
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
