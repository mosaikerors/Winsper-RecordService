//package com.mosaiker.recordservice.controller;
//
//import static org.junit.Assert.assertEquals;
//import static org.mockito.Mockito.when;
//
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//import com.mosaiker.recordservice.entity.Message;
//import com.mosaiker.recordservice.service.MessageService;
//import java.util.ArrayList;
//import org.junit.Test;
//import org.junit.Before;
//import org.junit.After;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
///**
// * MessageController Tester.
// *
// * @author <DeeEll-X>
// * @version 1.0
// * @since <pre>Aug 1, 2019</pre>
// */
//public class MessageControllerTest {
//
//  @Mock
//  private MessageService messageService;
//  @InjectMocks
//  private MessageController messageController;
//  private Message message1 = new Message(1, 1L, 2L, "test2", "1", "follow");
////  private Message message2 = new  Message(2, 1L, 2L, "test2", "2", "like");
////  private Message message3 = new  Message(3, 1L, 2L, "test2", "3", "star");
////  private Message message4 = new  Message(4, 1L, 2L, "test2", "4", "comment");
////  private Message message5 = new  Message(5, 1L, 2L, "test2", "5", "contribute");
//
//
//  @Before
//  public void before() throws Exception {
//    MockitoAnnotations.initMocks(this);
//  }
//
//  @After
//  public void after() throws Exception {
//  }
//
//  /**
//   * Method: getSomeTypeMessage(@RequestParam int type, @RequestParam Long uId)
//   */
//  @Test
//  public void testGetSomeTypeMessage() throws Exception {
//    when(messageService.findMessagesByReceiverUIdAndType(1L, 1))
//        .thenReturn(new ArrayList<Message>() {{
//          add(message1);
//        }});
//    JSONArray expectArray = new JSONArray() {{
//      add(message1.ToJSONObject());
//    }};
//    JSONObject expect = new JSONObject(true) {{
//      put("rescode", 0);
//      put("messages", expectArray);
//    }};
//    assertEquals(expect, messageController.getSomeTypeMessage(1, 1L));
//  }
//
//  /**
//   * Method: hasReadOne(@RequestBody JSONObject param)
//   */
//  @Test
//  public void testHasReadOne() throws Exception {
//    JSONObject param = new JSONObject() {{
//      put("messageId", 1);
//    }};
//    assertEquals(new JSONObject() {{
//      put("rescode", 0);
//    }}, messageController.hasReadOne(param));
//  }
//
//  /**
//   * Method: hasReadSomeType(@RequestBody JSONObject param)
//   */
//  @Test
//  public void testHasReadSomeType() throws Exception {
//    JSONObject param = new JSONObject() {{
//      put("type", 1);
//      put("uId", 1L);
//    }};
//    assertEquals(new JSONObject() {{
//      put("rescode", 0);
//    }}, messageController.hasReadSomeType(param));
//  }
//
//  /**
//   * Method: hasReadAll(@RequestBody JSONObject param)
//   */
//  @Test
//  public void testHasReadAll() throws Exception {
//    JSONObject param = new JSONObject() {{
//      put("uId", 1L);
//    }};
//    assertEquals(new JSONObject() {{
//      put("rescode", 0);
//    }}, messageController.hasReadAll(param));
//  }
//
//  /**
//   * Method: deleteSomeType(@RequestParam int type, @RequestParam Long uId)
//   */
//  @Test
//  public void testDeleteSomeType() throws Exception {
//    assertEquals(new JSONObject() {{
//      put("rescode", 0);
//    }}, messageController.deleteSomeType(1, 1L));
//  }
//
//  /**
//   * Method: deleteOne(@RequestParam String messageId)
//   */
//  @Test
//  public void testDeleteOne() throws Exception {
//    assertEquals(new JSONObject() {{
//      put("rescode", 0);
//    }}, messageController.deleteOne("1"));
//  }
//
//  /**
//   * Method: deleteAll(@RequestParam Long uId)
//   */
//  @Test
//  public void testDeleteAll() throws Exception {
//    assertEquals(new JSONObject() {{
//      put("rescode", 0);
//    }}, messageController.deleteAll(1L));
//  }
//
//
//}
