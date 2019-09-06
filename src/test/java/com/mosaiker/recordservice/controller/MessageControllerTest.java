package com.mosaiker.recordservice.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mosaiker.recordservice.entity.Message;
import com.mosaiker.recordservice.service.MessageService;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * MessageController Tester.
 *
 * @author <DeeEll-X>
 * @version 1.0
 * @since <pre>Aug 1, 2019</pre>
 */
public class MessageControllerTest {

  @Mock
  private MessageService messageService;
  @InjectMocks
  private MessageController messageController;
  private Message message1 = new Message(1, 1L, 2L, "test2", "1", "follow");



  @Before
  public void before() throws Exception {
    MockitoAnnotations.initMocks(this);
  }

  @After
  public void after() throws Exception {
  }

  /**
   * Method: getSomeTypeMessage(@RequestParam int type, @RequestParam Long uId)
   */
  @Test
  public void testGetSomeTypeMessage() throws Exception {
    when(messageService.findMessagesByReceiverUIdAndType(1L, 1))
        .thenReturn(new ArrayList<Message>() {{
          add(message1);
        }});
    JSONArray expectArray = new JSONArray() {{
      add(message1.ToJSONObject());
    }};
    JSONObject expect = new JSONObject(true) {{
      put("rescode", 0);
      put("messages", expectArray);
    }};
    assertEquals(expect, messageController.getSomeTypeMessage(1, 1L));
  }

  /**
   * Method: getAllTypeMessageList(@RequestParam Long uId)
   */
  @Test
  public void testGetAllTypeMessageList() {
    when(messageService.findMessagesByReceiverUIdAndType(1L, 0)).thenReturn(null);
    when(messageService.findMessagesByReceiverUIdAndType(1L,4)).thenReturn(new ArrayList<>());
    when(messageService.findMessagesByReceiverUIdAndType(1L, 2)).thenReturn(null);
    when(messageService.findMessagesByReceiverUIdAndType(1L, 3)).thenReturn(null);
    when(messageService.findMessagesByReceiverUIdAndType(1L, 1))
        .thenReturn(new ArrayList<Message>() {{
          add(message1);
        }});
    JSONArray expectArray = new JSONArray() {{
      add(message1.ToJSONObject());
    }};
    JSONObject expect = new JSONObject(true) {{
      put("rescode", 0);
      put("messages", expectArray);
    }};
    System.out.println(messageController.getAllTypeMessageList(1L));
    assertEquals(expect, messageController.getAllTypeMessageList(1L));

  }
  /**
   * Method: hasReadOne(@RequestBody JSONObject param)
   */
  @Test
  public void testHasReadOne() throws Exception {
    JSONObject param = new JSONObject() {{
      put("messageId", 1);
    }};
    assertEquals(new JSONObject() {{
      put("rescode", 0);
    }}, messageController.hasReadOne(param));
  }

  /**
   * Method: hasReadSomeType(@RequestBody JSONObject param)
   */
  @Test
  public void testHasReadSomeType() throws Exception {
    JSONObject param = new JSONObject() {{
      put("type", 1);
      put("uId", 1L);
    }};
    assertEquals(new JSONObject() {{
      put("rescode", 0);
    }}, messageController.hasReadSomeType(param,2L));
  }

  /**
   * Method: hasReadAll(@RequestBody JSONObject param)
   */
  @Test
  public void testHasReadAll() throws Exception {
    assertEquals(new JSONObject() {{
      put("rescode", 0);
    }}, messageController.hasReadAll(1L));
  }

  /**
   * Method: deleteSomeType(@RequestParam int type, @RequestParam Long uId)
   */
  @Test
  public void testDeleteSomeType() throws Exception {
    assertEquals(new JSONObject() {{
      put("rescode", 0);
    }}, messageController.deleteSomeType(1, 1L));
  }

  /**
   * Method: deleteOne(@RequestParam String messageId)
   */
  @Test
  public void testDeleteOne() throws Exception {
    assertEquals(new JSONObject() {{
      put("rescode", 0);
    }}, messageController.deleteOne("1"));
  }

  /**
   * Method: deleteAll(@RequestParam Long uId)
   */
  @Test
  public void testDeleteAll() throws Exception {
    assertEquals(new JSONObject() {{
      put("rescode", 0);
    }}, messageController.deleteAll(1L));
  }


}
