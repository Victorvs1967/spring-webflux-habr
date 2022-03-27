package com.vvs.springwebfluxhabr.model;

import com.mongodb.lang.NonNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document("students")
public class Student {
  @Id
  private String id;
  @NonNull
  private String name;
  private String email;
  private String firstName;
  private String lastName;
  private String speciality;
  private String fullName;

  public String getFullName() {
    return firstName != null ? firstName.concat(" ").concat(lastName) : "";
  }
}
