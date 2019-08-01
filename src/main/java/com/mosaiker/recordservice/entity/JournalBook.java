package com.mosaiker.recordservice.entity;

import com.alibaba.fastjson.JSONObject;
import java.util.List;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

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
}
