package com.mosaiker.recordservice.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mosaiker.recordservice.service.HttpService;
import com.mosaiker.recordservice.service.MoodReportService;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
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

  //此处应需要管理员权限
  @RequestMapping(value = "/moodReport/generate", method = RequestMethod.POST)
  @ResponseBody
  public JSONObject generateMoodReport() {
    //先搜索这一周所有发出的函，根据uId分类，然后调用api生成keyword,mood,image,poem.
    //然后新增moodReport，这周没发过函的不给他生成心情报表
    JSONObject ret = new JSONObject();
    ret.put("rescode", 0);
    return ret;
  }

  @RequestMapping(value = "/moodReport/generatePoem", method = RequestMethod.POST)
  @ResponseBody
  public JSONObject generatePoem() throws UnsupportedEncodingException {
    JSONObject ret = new JSONObject();
    String result = HttpService.doPost("http://10.0.0.98:11370/predict", "");
    JSONObject parseObject = JSONObject.parseObject(result);
    if (parseObject.getBoolean("success")) {
      ret.put("rescode", 0);
      ret.put("originPoem", parseObject.getString("poem"));
      ret.put("poem", URLDecoder.decode(parseObject.getString("poem"), "UTF-8"));
    } else {
      ret.put("rescode", 1);
    }
    return ret;
  }

}
