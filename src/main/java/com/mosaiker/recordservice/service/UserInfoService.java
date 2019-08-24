package com.mosaiker.recordservice.service;

import com.alibaba.fastjson.JSONObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "user-service")
@Service
public interface UserInfoService {

  @RequestMapping(method = RequestMethod.GET, value = "/getSimpleInfo")
  JSONObject getSimpleInfo(@RequestHeader Long uId);

}
