package com.mosaiker.recordservice.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mosaiker.recordservice.entity.MoodReport;

public interface MoodReportService {
  JSONObject getMoodReportListByUId(Long uId);

  JSONObject getMoodReportByMoodReportId(String mrId);

  void addMoodReport(MoodReport moodReport);
}
