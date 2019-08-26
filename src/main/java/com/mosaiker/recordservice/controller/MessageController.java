package com.mosaiker.recordservice.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mosaiker.recordservice.entity.Message;
import com.mosaiker.recordservice.service.MessageService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class MessageController {
    @Autowired
    private MessageService messageService;

    @RequestMapping(value = "/message/detailed", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getSomeTypeMessage(@RequestParam int type, @RequestHeader Long uId) {
        JSONObject result = new JSONObject(true);
        result.put("rescode", 0);
        JSONArray list = new JSONArray();
        List<Message> messages = messageService.findMessagesByReceiverUIdAndType(uId, type);
        for(Message one:messages){
            list.add(one.ToJSONObject());
        }
        result.put("messages", list);
        return result;
    }

    @RequestMapping(value = "/message/list", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getAllTypeMessageList(@RequestHeader Long uId) {
        JSONObject result = new JSONObject(true);
        result.put("rescode", 0);
        JSONArray list = new JSONArray();
        for (int type = 0; type < 5; type++) {
            List<Message> messages = messageService.findMessagesByReceiverUIdAndType(uId, type);
            JSONObject tmp = messages.get(messages.size() - 1).ToJSONObject();
            tmp.put("type", type);
            list.add(tmp);
        }
        result.put("messages", list);
        return result;
    }

    @RequestMapping(value = "/message/hasRead/single", method = RequestMethod.PUT)
    @ResponseBody
    public JSONObject hasReadOne(@RequestBody JSONObject param) {
        JSONObject result = new JSONObject();
        String messageId = param.getString("messageId");
        messageService.readMessageByMId(messageId);
        result.put("rescode", 0);
        return result;
    }

    @RequestMapping(value = "/message/hasRead/type", method = RequestMethod.PUT)
    @ResponseBody
    public JSONObject hasReadSomeType(@RequestBody JSONObject param, @RequestHeader Long uId) {
        JSONObject result = new JSONObject();
        int type = param.getIntValue("type");
        messageService.readMessagesByReceiverUIdAndType(uId, type);
        result.put("rescode", 0);
        return result;
    }

    @RequestMapping(value = "/message/hasRead/all", method = RequestMethod.PUT)
    @ResponseBody
    public JSONObject hasReadAll(@RequestHeader Long uId) {
        JSONObject result = new JSONObject();
        messageService.readMessagesByReceiverUId(uId);
        result.put("rescode", 0);
        return result;
    }

    @RequestMapping(value = "/message", method = RequestMethod.DELETE)
    @ResponseBody
    public JSONObject deleteSomeType(@RequestParam int type, @RequestHeader Long uId) {
        JSONObject result = new JSONObject();
        messageService.deleteMessagesByReceiverUIdAndType(uId, type);
        result.put("rescode", 0);
        return result;
    }

    @RequestMapping(value = "/message/one", method = RequestMethod.DELETE)
    @ResponseBody
    public JSONObject deleteOne(@RequestParam String messageId) {
        JSONObject result = new JSONObject();
        messageService.deleteMessageByMId(messageId);
        result.put("rescode", 0);
        return result;
    }

    @RequestMapping(value = "/message/all", method = RequestMethod.DELETE)
    @ResponseBody
    public JSONObject deleteAll(@RequestHeader Long uId) {
        JSONObject result = new JSONObject();
        messageService.deleteMessagesByReceiverUId(uId);
        result.put("rescode", 0);
        return result;
    }
}
