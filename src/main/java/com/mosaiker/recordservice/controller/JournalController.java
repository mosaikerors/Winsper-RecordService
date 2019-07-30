package com.mosaiker.recordservice.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mosaiker.recordservice.service.JournalService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JournalController {
  @Autowired
  private JournalService journalService;

  @RequestMapping(value = "/journal/books", method = RequestMethod.GET)
  @ResponseBody
  public JSONObject getBooks(@RequestParam JSONObject param) {
    try {
      JSONObject ret = new JSONObject();
      Long uId = param.getLong("uId");
      JSONArray list = journalService.findBooksByuId(uId);
      if(list.isEmpty()){
        ret.put("rescode",3);
      }else{
        ret.put("rescode",0);
        ret.put("journalBooks",list);
      }
      return ret;

    } catch (Exception e) {
      JSONObject ret = new JSONObject();
      ret.put("rescode", 1);
      return ret;
    }
  }


  @RequestMapping(value = "/journal/{journalBookId}", method = RequestMethod.GET)
  @ResponseBody
  public JSONObject getJournals(@PathVariable Long jounalBookId) {
    try {
      JSONObject ret = new JSONObject();
      JSONArray list = journalService.findJournalsByBookId(jounalBookId);
      if(list.isEmpty()){
        ret.put("rescode",3);
      }else{
        ret.put("rescode",0);
        ret.put("journals",list);
      }
      return ret;

    } catch (Exception e) {
      JSONObject ret = new JSONObject();
      ret.put("rescode", 1);
      return ret;
    }
  }
}
