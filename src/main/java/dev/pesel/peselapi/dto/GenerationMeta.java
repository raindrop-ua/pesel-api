package dev.pesel.peselapi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.Instant;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class GenerationMeta {
  private String requestId;
  private Instant generatedAt;
  private Long durationMs;
  private boolean unique;
  private int count;
  private Integer maxPossible;
  private GenerationConstraints constraints;

  public GenerationMeta() {
  }

  public GenerationMeta(String requestId, Instant generatedAt, Long durationMs, boolean unique,
                        int count, Integer maxPossible, GenerationConstraints constraints) {
    this.requestId = requestId;
    this.generatedAt = generatedAt;
    this.durationMs = durationMs;
    this.unique = unique;
    this.count = count;
    this.maxPossible = maxPossible;
    this.constraints = constraints;
  }

  public static String newRequestId() {
    return UUID.randomUUID().toString();
  }

  public String getRequestId() {
    return requestId;
  }

  public void setRequestId(String requestId) {
    this.requestId = requestId;
  }

  public Instant getGeneratedAt() {
    return generatedAt;
  }

  public void setGeneratedAt(Instant generatedAt) {
    this.generatedAt = generatedAt;
  }

  public Long getDurationMs() {
    return durationMs;
  }

  public void setDurationMs(Long durationMs) {
    this.durationMs = durationMs;
  }

  public boolean isUnique() {
    return unique;
  }

  public void setUnique(boolean unique) {
    this.unique = unique;
  }

  public int getCount() {
    return count;
  }

  public void setCount(int count) {
    this.count = count;
  }

  public Integer getMaxPossible() {
    return maxPossible;
  }

  public void setMaxPossible(Integer maxPossible) {
    this.maxPossible = maxPossible;
  }

  public GenerationConstraints getConstraints() {
    return constraints;
  }

  public void setConstraints(GenerationConstraints constraints) {
    this.constraints = constraints;
  }
}
