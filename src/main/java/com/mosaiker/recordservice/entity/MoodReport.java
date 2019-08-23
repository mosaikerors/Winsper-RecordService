package com.mosaiker.recordservice.entity;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import javax.persistence.GeneratedValue;


@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MoodReport {
  @Id
  @GeneratedValue
  Long moodReportId;
  Long uId;
  int year;
  int week;
  int heanNum;
  int starNum;
  String keyWord;
  int mood;
  String image;
  String poem;

  public JSONObject ToMiniJSONObject() {
    JSONObject ret = new JSONObject() {{
      put("moodReportId", moodReportId);
      put("mood", mood);
      put("year", year);
      put("week", week);
    }};
    return ret;
  }

  public JSONObject ToJSONObject() {
    JSONObject ret = new JSONObject() {{
      put("mood", mood);
      put("year", year);
      put("heanNum", heanNum);
      put("starNum", starNum);
      put("keyWord", keyWord);
      put("image", image);
      put("poem", poem);
    }};
    return ret;
  }
}
