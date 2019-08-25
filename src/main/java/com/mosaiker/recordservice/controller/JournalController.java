package com.mosaiker.recordservice.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mosaiker.recordservice.service.JournalService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class JournalController {
  @Autowired
  private JournalService journalService;

  @RequestMapping(value = "/journal/books", method = RequestMethod.GET)
  @ResponseBody
  public JSONObject getBooks(@RequestParam Long owner) {

      JSONObject ret = new JSONObject(true);
      JSONArray list = journalService.findBooksByuId(owner);
      ret.put("rescode",0);
      ret.put("journalBooks",list);
      return ret;
  }


  @RequestMapping(value = "/journal", method = RequestMethod.GET)
  @ResponseBody
  public JSONObject getJournals(@RequestParam Long journalBookId) {
      JSONObject ret = new JSONObject(true);
      JSONArray list = journalService.findJournalsByBookId(journalBookId);
      ret.put("rescode",0);
      ret.put("journalBooks",list);
      return ret;
  }

  @RequestMapping(value = "/journal", method = RequestMethod.POST)
  @ResponseBody
  public JSONObject createJournal(@RequestBody JSONObject param, @RequestHeader("uId") Long uId) {
    JSONObject ret = new JSONObject(true);
    ret.put("rescode", journalService
        .createJournal(param.getLong("journalBookId"), param.getString("journalUrl"), uId));
    return ret;
  }

  @RequestMapping(value = "/journal", method = RequestMethod.DELETE)
  @ResponseBody
  public JSONObject deleteJournal(@RequestParam Long journalId, @RequestParam Long journalBookId, @RequestHeader("uId") Long uId) {
    JSONObject ret = new JSONObject(true);
    ret.put("rescode", journalService
        .deleteJournal(journalId, journalBookId, uId));
    return ret;
  }
}
