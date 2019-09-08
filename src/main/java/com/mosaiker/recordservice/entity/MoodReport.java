package com.mosaiker.recordservice.entity;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import javax.persistence.GeneratedValue;
import org.springframework.data.mongodb.core.mapping.Document;


@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MoodReport {
  @Id
  @GeneratedValue
  String moodReportId;
  Long uId;
  int year;
  int week;
  int heanNum;
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
      put("keyWord", keyWord);
      put("image", image);
      put("poem", poem);
    }};
    return ret;
  }

  public MoodReport(Long uId, int year, int week, int heanNum, String keyWord, int mood, String image, String poem) {
    this.uId = uId;
    this.year = year;
    this.week = week;
    this.heanNum = heanNum;
    this.keyWord = keyWord;
    this.mood = mood;
    this.image = image;
    this.poem = poem;
  }
}
