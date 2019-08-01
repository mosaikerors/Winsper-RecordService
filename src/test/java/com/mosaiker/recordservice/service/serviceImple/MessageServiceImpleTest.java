package com.mosaiker.recordservice.service.serviceImple;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.mosaiker.recordservice.entity.Message;
import com.mosaiker.recordservice.repository.MessageRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * MessageServiceImple Tester.
 *
 * @author <DeeEll-X>
 * @version 1.0
 * @since <pre>Aug 2, 2019</pre>
 */
public class MessageServiceImpleTest {

  @Mock
  private MessageRepository messageRepository;
  @InjectMocks
  private MessageServiceImple messageServiceImple;
  private Message message1 = new Message(1, 1L, 2L, "test2", "1", "follow");
  private Message message2 = new Message(2, 1L, 2L, "test2", "2", "like");
  private Message message3 = new Message(3, 1L, 2L, "test2", "3", "star");
  private Message message4 = new Message(4, 1L, 2L, "test2", "4", "comment");
  private Message message5 = new Message(5, 1L, 2L, "test2", "5", "contribute");
  private List<Message> messageList = new ArrayList<Message>() {{
    add(message1);
    add(message2);
    add(message3);
    add(message4);
    add(message5);
  }};

  @Before
  public void before() throws Exception {
    MockitoAnnotations.initMocks(this);
  }

  @After
  public void after() throws Exception {
  }

  /**
   * Method: findMessagesByReceiverUId(Long uId)
   */
  @Test
  public void testFindMessagesByReceiverUId() throws Exception {
    when(messageRepository.findAllByReceiverUId(1L)).thenReturn(messageList);
    assertEquals(messageList, messageServiceImple.findMessagesByReceiverUId(1L));
  }

  /**
   * Method: findUnreadMessagesByReceiverUId(Long uId)
   */
  @Test
  public void testFindUnreadMessagesByReceiverUId() throws Exception {
    when(messageRepository.findAllByReceiverUIdAndHasReadFalse(1L)).thenReturn(messageList);
    assertEquals(messageList, messageServiceImple.findUnreadMessagesByReceiverUId(1L));
  }

  /**
   * Method: findMessagesByReceiverUIdAndType(Long uId, int type)
   */
  @Test
  public void testFindMessagesByReceiverUIdAndType() throws Exception {
    when(messageRepository.findAllByReceiverUIdAndType(1L, 1))
        .thenReturn(new ArrayList<Message>() {{
          add(message1);
        }});
    assertEquals(new ArrayList<Message>() {{
      add(message1);
    }}, messageServiceImple.findMessagesByReceiverUIdAndType(1L, 1));
  }

  /**
   * Method: findUnreadMessagesByReceiverUIdAndType(Long uId, int type)
   */
  @Test
  public void testFindUnreadMessagesByReceiverUIdAndType() throws Exception {
    when(messageRepository.findAllByReceiverUIdAndTypeAndHasReadFalse(1L, 1))
        .thenReturn(new ArrayList<Message>() {{
          add(message1);
        }});
    assertEquals(new ArrayList<Message>() {{
      add(message1);
    }}, messageServiceImple.findUnreadMessagesByReceiverUIdAndType(1L, 1));
  }

  /**
   * Method: readMessagesByReceiverUIdAndType(Long uId, int type)
   */
  @Test
  public void testReadMessagesByReceiverUIdAndType() throws Exception {
    List<Message> list = new ArrayList<Message>() {{
      add(message1);
    }};
    when(messageRepository.findAllByReceiverUIdAndTypeAndHasReadFalse(1L, 1))
        .thenReturn(list);
    list.get(0).setHasRead(true);
    when(messageRepository.saveAll(list)).thenReturn(list);
    messageServiceImple.readMessagesByReceiverUIdAndType(1L, 1);
    verify(messageRepository).saveAll(anyObject());
  }

  /**
   * Method: readMessageByMId(String mId)
   */
  @Test
  public void testReadMessageByMId() throws Exception {
    when(messageRepository.findById("2")).thenReturn(Optional.of(message2));
    messageServiceImple.readMessageByMId("2");
    Message mes = message2;
    mes.setHasRead(true);
    verify(messageRepository).save(mes);
  }

  /**
   * Method: readMessagesByReceiverUId(Long uId)
   */
  @Test
  public void testReadMessagesByReceiverUId() throws Exception {
    List<Message> mesList = new ArrayList<>(messageList);
    when(messageRepository.findAllByReceiverUIdAndHasReadFalse(1L)).thenReturn(mesList);
    for (Message mes : mesList) {
      mes.setHasRead(true);
    }
    messageServiceImple.readMessagesByReceiverUId(1L);
    verify(messageRepository).saveAll(mesList);
  }

  /**
   * Method: deleteMessagesByReceiverUIdAndType(Long uId, int type)
   */
  @Test
  public void testDeleteMessagesByReceiverUIdAndType() throws Exception {
    List<Message> mesList = new ArrayList<Message>() {{
      add(message3);
    }};
    when(messageRepository.findAllByReceiverUIdAndType(1L, 3)).thenReturn(mesList);
    messageServiceImple.deleteMessagesByReceiverUIdAndType(1L, 3);
    verify(messageRepository).deleteAll(mesList);
  }

  /**
   * Method: deleteMessagesByReceiverUId(Long uId)
   */
  @Test
  public void testDeleteMessagesByReceiverUId() throws Exception {
    when(messageRepository.findAllByReceiverUId(1L)).thenReturn(messageList);
    messageServiceImple.deleteMessagesByReceiverUId(1L);
    verify(messageRepository).deleteAll(messageList);
  }

  /**
   * Method: deleteMessageByMId(String mId)
   */
  @Test
  public void testDeleteMessageByMId() throws Exception {
    when(messageRepository.findById("4")).thenReturn(Optional.of(message4));
    messageServiceImple.deleteMessageByMId("4");
    verify(messageRepository).delete(message4);
  }

  /**
   * Method: addNewMessage(Message message)
   */
  @Test
  public void testAddNewMessage() throws Exception {
    when(messageRepository.save(message5)).thenReturn(message5);
    messageServiceImple.addNewMessage(message5);
    verify(messageRepository).save(message5);
  }


}
