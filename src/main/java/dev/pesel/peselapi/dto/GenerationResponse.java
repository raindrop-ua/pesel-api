package dev.pesel.peselapi.dto;

import java.util.List;

public class GenerationResponse {
  private GenerationMeta meta;
  private List<String> data;

  public GenerationResponse() {
  }

  public GenerationResponse(GenerationMeta meta, List<String> data) {
    this.meta = meta;
    this.data = data;
  }

  public GenerationMeta getMeta() {
    return meta;
  }

  public void setMeta(GenerationMeta meta) {
    this.meta = meta;
  }

  public List<String> getData() {
    return data;
  }

  public void setData(List<String> data) {
    this.data = data;
  }
}
