package com.moricion.qa.common.support;

import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Configuration {
  private String platform;
  private String databaseHost;
  private String env;
  private String repository;
  private String bitbucketPipelineBuild;

  /**
   * Loads the config file.
   * @param file    String path where configuration is located
   * @return Configuration object with config params.
   */
  public static Configuration getConfiguration(String file){
    Configuration config = null;
    Yaml yaml = new Yaml();
    try(InputStream in = Files.newInputStream(Paths.get(file))){
      config = yaml.loadAs(in, Configuration.class);
    }catch (IOException e){
      Log.error("Error loading config file.");
      Log.error(e.getMessage());
    }
    return config;
  }

  public String getPlatform() {
    return platform;
  }

  public void setPlatform(String platform) {
    this.platform = platform;
  }

  public String getDatabaseHost() {
    return databaseHost;
  }

  public void setDatabaseHost(String databaseHost) {
    this.databaseHost = databaseHost;
  }

  public String getEnv() {
    return env;
  }

  public void setEnv(String env) {
    this.env = env;
  }

  public String getRepository() {
    return repository;
  }

  public void setRepository(String repository) {
    this.repository = repository;
  }

  public String getBitbucketPipelineBuild() {
    return bitbucketPipelineBuild;
  }

  public void setBitbucketPipelineBuild(String bitbucketPipelineBuild) {
    this.bitbucketPipelineBuild = bitbucketPipelineBuild;
  }
}
