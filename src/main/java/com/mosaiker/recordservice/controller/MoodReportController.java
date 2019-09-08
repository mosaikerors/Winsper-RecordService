package com.mosaiker.recordservice.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mosaiker.recordservice.entity.MoodReport;
import com.mosaiker.recordservice.service.HeanInfoService;
import com.mosaiker.recordservice.service.HttpService;
import com.mosaiker.recordservice.service.MoodReportService;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class MoodReportController {
  @Autowired
  private MoodReportService moodReportService;

  @Autowired
  private HttpService httpService;
  @Autowired
  private HeanInfoService heanInfoService;

  @RequestMapping(value = "/moodReport/list", method = RequestMethod.GET)
  @ResponseBody
  public JSONObject getMoodReportList(@RequestParam Long owner) {

    JSONObject ret = moodReportService.getMoodReportListByUId(owner);
    ret.put("rescode", 0);
    return ret;
  }

  @RequestMapping(value = "/moodReport/detailed", method = RequestMethod.GET)
  @ResponseBody
  public JSONObject getMoodReport(@RequestParam String moodReportId) {

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
    Date date = new Date();
    Calendar cal = Calendar.getInstance();
    cal.setFirstDayOfWeek(Calendar.MONDAY);//设置周一为一周的第一天
    cal.setTime(date);
    int year = cal.get(Calendar.YEAR);
    int week = cal.get(Calendar.WEEK_OF_YEAR);
    JSONArray result = heanInfoService.findAllByTime("week").getJSONArray("heans");
    Map<Long, JSONArray> peopleMap = new HashMap<>();
    List<MoodReport> reportList = new ArrayList<>();
    for (int i = 0; i < result.size(); i++) {
      JSONObject hean = result.getJSONObject(i);
      Long uId = hean.getLong("uId");
      JSONArray jsonArray = peopleMap.get(uId);
      if (jsonArray == null) {
        jsonArray = new JSONArray();
      }
      jsonArray.add(hean);
      peopleMap.put(uId, jsonArray);
    }
    //生成诗
    String poemsResult = httpService.doGet("http://47.103.0.246:11370/predict?number="+String.valueOf(peopleMap.size()));
    JSONArray poems = JSONObject.parseObject(poemsResult).getJSONArray("poems");
    int i = 0;
    for (Long uId : peopleMap.keySet()) {
      JSONArray heans = peopleMap.get(uId);
      int heanNum = heans.size();
      String poem = poems.getString(i);
      i++;

      String image = "";
      StringBuilder texts = new StringBuilder();
      for (int j = 0; j < heanNum; j++) {
        String text = heans.getJSONObject(j).getString("text");
        if (text != null && !text.equals("")) {
          texts.append(text);
          if (image.equals("")) {
            //生成图片
            JSONObject imageRequest = new JSONObject();
            imageRequest.put("caption", text);
            String imageResult = httpService.doPost("http://10.0.0.41:23422/predict", imageRequest.toJSONString());
            image = JSONObject.parseObject(imageResult).getString("url");
          }
        }
      }
      //生成关键词
      JSONObject textRequest = new JSONObject();
      textRequest.put("texts", texts);
      String textResult = httpService.doPost("http://47.103.0.246:11370/keyWord", textRequest.toJSONString());
      String keyWord = JSONObject.parseObject(textResult).getString("keyWord");

      //生成心情
      JSONObject moodRequest = new JSONObject();
      moodRequest.put("text", texts);
      String moodResult = httpService.doPost("http://10.0.0.41:23423/predict", moodRequest.toJSONString());
      int mood = JSONObject.parseObject(moodResult).getIntValue("mood");

      //生成心情报表
      MoodReport moodReport = new MoodReport(uId, year, week, heanNum, keyWord, mood, image, poem);
      reportList.add(moodReport);
    }

    //存到数据库
    for (MoodReport moodReport : reportList) {
      moodReportService.addMoodReport(moodReport);
    }

    JSONObject ret = new JSONObject();
    ret.put("rescode", 0);
    return ret;
  }

  @RequestMapping(value = "/moodReport/generateOne", method = RequestMethod.POST)
  @ResponseBody
  public JSONObject generateOneMoodReport(@RequestBody JSONObject param) {
    //先搜索这一周所有发出的函，根据uId分类，然后调用api生成keyword,mood,image,poem.
    //然后新增moodReport，这周没发过函的不给他生成心情报表
    Date date = new Date();
    Calendar cal = Calendar.getInstance();
    cal.setFirstDayOfWeek(Calendar.MONDAY);//设置周一为一周的第一天
    cal.setTime(date);
    int year = cal.get(Calendar.YEAR);
    int week = cal.get(Calendar.WEEK_OF_YEAR);
    JSONArray result = heanInfoService.findAllByTime("week").getJSONArray("heans");
    JSONArray heans = new JSONArray();
    Long targetUId = param.getLong("targetUId");
    for (int i = 0; i < result.size(); i++) {
      JSONObject hean = result.getJSONObject(i);
      Long uId = hean.getLong("uId");
      if (uId.equals(targetUId)) {
        heans.add(hean);
      }
    }
    //生成诗
    String poemsResult = httpService.doGet("http://47.103.0.246:11370/predict");
    JSONArray poems = JSONObject.parseObject(poemsResult).getJSONArray("poems");
    int heanNum = heans.size();
    String poem = poems.getString(0);
    String image = "";
    StringBuilder texts = new StringBuilder();
    for (int j = 0; j < heanNum; j++) {
      String text = heans.getJSONObject(j).getString("text");
      if (text != null && !text.equals("")) {
        texts.append(text);
        if (image.equals("")) {
          //生成图片
          JSONObject imageRequest = new JSONObject();
          imageRequest.put("caption", text);
          String imageResult = httpService.doPost("http://10.0.0.41:23422/predict", imageRequest.toJSONString());
          image = JSONObject.parseObject(imageResult).getString("url");
        }
      }
    }

    //生成关键词
    JSONObject textRequest = new JSONObject();
    textRequest.put("texts", texts);
    String textResult = httpService.doPost("http://47.103.0.246:11370/keyWord", textRequest.toJSONString());
    String keyWord = JSONObject.parseObject(textResult).getString("keyWord");

    //生成心情
    JSONObject moodRequest = new JSONObject();
    moodRequest.put("text", texts);
    String moodResult = httpService.doPost("http://10.0.0.41:23423/predict", moodRequest.toJSONString());
    int mood = JSONObject.parseObject(moodResult).getIntValue("mood");

    //生成心情报表
    MoodReport moodReport = new MoodReport(uId, year, week, heanNum, keyWord, mood, image, poem);
    moodReportService.addMoodReport(moodReport);

    JSONObject ret = new JSONObject();
    ret.put("rescode", 0);
    return ret;
  }

  @RequestMapping(value = "/moodReport/generatePoem", method = RequestMethod.POST)
  @ResponseBody
  public JSONObject generatePoem() throws UnsupportedEncodingException {
    JSONObject ret = new JSONObject();
    String result = httpService.doGet("http://10.0.0.41:11370/predict");
    System.out.println(result);
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
