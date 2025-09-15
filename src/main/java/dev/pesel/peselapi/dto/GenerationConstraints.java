package dev.pesel.peselapi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class GenerationConstraints {
  private String sex;
  private LocalDate dob;

  public GenerationConstraints() {
  }

  public GenerationConstraints(String sex, LocalDate dob) {
    this.sex = sex;
    this.dob = dob;
  }

  public String getSex() {
    return sex;
  }

  public void setSex(String sex) {
    this.sex = sex;
  }

  public LocalDate getDob() {
    return dob;
  }

  public void setDob(LocalDate dob) {
    this.dob = dob;
  }
}
