package com.mosaiker.recordservice.entity;

import com.alibaba.fastjson.JSONObject;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import javax.persistence.criteria.CriteriaBuilder.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JournalBook {

  @ElementCollection
  List<Journal> journals;
  @Id
  @GeneratedValue
  private Long journalBookId;
  private String name;
  private Integer coverId;
  private Long uId;

  public JSONObject ToJSONObject() {
    JSONObject ret = new JSONObject() {{
      put("journalBookId", journalBookId);
      put("name", name);
      put("coverId", coverId);
    }};
    return ret;
  }

  public JournalBook(String name, Integer coverId, Long uId) {
    this.name = name;
    this.coverId = coverId;
    this.uId = uId;
    this.journals = new ArrayList<>();
  }

  public Long getUId() {
    return uId;
  }
}
