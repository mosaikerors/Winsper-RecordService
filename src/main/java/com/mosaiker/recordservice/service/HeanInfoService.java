package com.mosaiker.recordservice.service;

import com.alibaba.fastjson.JSONObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@FeignClient(value = "hean-service")
@Service
public interface HeanInfoService {

  @RequestMapping(value = "/allByTime", method = RequestMethod.GET)
  @ResponseBody
  JSONObject findAllByTime(@RequestParam String time);

}
