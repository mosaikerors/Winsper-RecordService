package com.mosaiker.recordservice.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mosaiker.recordservice.service.MoodReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MoodReportController {
  @Autowired
  private MoodReportService moodReportService;

  @RequestMapping(value = "/moodReport/list", method = RequestMethod.GET)
  @ResponseBody
  public JSONObject getMoodReportList(@RequestParam Long owner) {

    JSONObject ret = moodReportService.getMoodReportListByUId(owner);
    ret.put("rescode", 0);
    return ret;
  }

  @RequestMapping(value = "/moodReport/detailed", method = RequestMethod.GET)
  @ResponseBody
  public JSONObject getMoodReport(@RequestParam Long moodReportId) {

    JSONObject ret = new JSONObject();
    JSONObject moodReport = moodReportService.getMoodReportByMoodReportId(moodReportId);
    if (moodReport == null) {
      ret.put("rescode", 1);
      return ret;
    }
    ret.put("rescode", 0);
    ret.put("moodReport", moodReport);
    return ret;
  }

}
