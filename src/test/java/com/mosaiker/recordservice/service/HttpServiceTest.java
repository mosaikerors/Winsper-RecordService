package com.mosaiker.recordservice.service;

import static org.junit.Assert.*;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import org.junit.Test;

public class HttpServiceTest {

  @Test
  public void doGet() throws UnsupportedEncodingException {
    String result = HttpService.doGet("http://127.0.0.1:5000/predict?number=10");
    JSONObject parseObject = JSONObject.parseObject(result);
    JSONArray jsonArray = parseObject.getJSONArray("poems");
    System.out.println(jsonArray);
    System.out.println(result);
    String strUTF8 = URLDecoder.decode(parseObject.getString("poems"), "UTF-8");
    System.out.println(strUTF8);
  }

  @Test
  public void doPost() throws UnsupportedEncodingException {
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("niubi", 1);
    String result = HttpService.doPost("http://127.0.0.1:5000/keyWord", jsonObject.toJSONString());
//    JSONObject parseObject = JSONObject.parseObject(result);
//    System.out.println(result);
//    String strUTF8 = URLDecoder.decode(parseObject.getString("poem"), "UTF-8");
//    System.out.println(strUTF8);
  }
}