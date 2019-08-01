package com.mosaiker.recordservice.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Journal {

  @Id
  @GeneratedValue
  private Long journalId;// 手账 id
  private String journalUrl;

}
