package com.mosaiker.recordservice.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public interface MoodReportService {
  JSONObject getMoodReportListByUId(Long uId);

  JSONObject getMoodReportByMoodReportId(Long mrId);
}
