package com.mosaiker.recordservice.entity;

import com.alibaba.fastjson.JSONObject;
import java.util.Date;
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
public class Diary {
  @Id
  @GeneratedValue
  private Long diaryId;
  private Long time;
  private Long uId;
  private String title;
  private String username;
  private String text;

  public Diary() {

  }

  public Diary(String title, String text, Long uId, String username) {
    this.text = text;
    this.title = title;
    this.time = new Date().getTime();
    this.username = username;
    this.uId = uId;
  }

  public JSONObject ToMiniJSONObject() {
    JSONObject ret = new JSONObject() {{
      put("diaryId", diaryId);
      put("title", title);
      put("time", time);
    }};
    return ret;
  }

  public JSONObject ToJSONObject() {
    JSONObject ret = new JSONObject() {{
      put("title", title);
      put("text", text);
      put("username", username);
      put("time", time);
    }};
    return ret;
  }

  public Long getUId() {
    return uId;
  }
}
