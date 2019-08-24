package com.mosaiker.recordservice.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mosaiker.recordservice.entity.Diary;
import com.mosaiker.recordservice.repository.DiaryRepository;
import com.mosaiker.recordservice.service.JournalService;
import com.mosaiker.recordservice.service.UserInfoService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DiaryController {
  @Autowired
  private DiaryRepository diaryRepository;

  @Autowired
  private UserInfoService userInfoService;

  @RequestMapping(value = "/diary/list", method = RequestMethod.GET)
  @ResponseBody
  public JSONObject getDiaryList(@RequestParam Long owner) {
    JSONObject ret = new JSONObject(true);
    List<Diary> list = diaryRepository.findAllByUId(owner);
    JSONArray diaries = new JSONArray();
    for (Diary diary : list) {
      diaries.add(diary.ToMiniJSONObject());
    }
    ret.put("rescode", 0);
    ret.put("diaries", diaries);
    return ret;
  }


  @RequestMapping(value = "/diary/detailed", method = RequestMethod.GET)
  @ResponseBody
  public JSONObject getDiary(@RequestParam Long diaryId) {
    Optional<Diary> diary = diaryRepository.findById(diaryId);
    if (diary.isPresent()) {
      JSONObject ret = diary.get().ToJSONObject();
      ret.put("rescode", 0);
      return ret;
    } else {
      JSONObject ret = new JSONObject();
      ret.put("rescode", 1);
      return ret;
    }
  }


  @RequestMapping(value = "/diary", method = RequestMethod.POST)
  @ResponseBody
  public JSONObject writeDiary(@RequestBody JSONObject param, @RequestHeader("uId") Long uId) {
    JSONObject info = userInfoService.getSimpleInfo(uId);
    Diary diary = new Diary(param.getString("title")
        ,param.getString("text"),uId, info.getString("username"));
    diaryRepository.save(diary);
    JSONObject ret = new JSONObject();
    ret.put("rescode", 0);
    return ret;
  }


  @RequestMapping(value = "/diary", method = RequestMethod.DELETE)
  @ResponseBody
  public JSONObject deleteDiary(@RequestParam Long diaryId, @RequestHeader("uId") Long uId) {
    JSONObject ret = new JSONObject();
    Optional<Diary> diary = diaryRepository.findById(diaryId);
    if (diary.isPresent()) {
      if (diary.get().getUId().equals(uId)) {
        diaryRepository.deleteById(diaryId);
        ret.put("rescode", 0);
      } else {
        ret.put("rescode", 2);
      }
    } else {
      ret.put("rescode", 1);
    }
    return ret;
  }
}
