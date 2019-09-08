package com.mosaiker.recordservice.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mosaiker.recordservice.entity.Journal;
import com.mosaiker.recordservice.entity.JournalBook;
import com.mosaiker.recordservice.service.JournalService;
import java.util.ArrayList;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * JournalController Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>���� 1, 2019</pre>
 */
public class JournalControllerTest {

  @Mock
  private JournalService journalService;
  @InjectMocks
  private JournalController journalController;
  private Journal journal1 = new Journal(1L, "pic1");
  private JournalBook journalBook1 = new JournalBook(new ArrayList<Journal>() {{
    add(journal1);
  }}, 1L, "testBook", 1, 1L);

  @Before
  public void before() throws Exception {
    MockitoAnnotations.initMocks(this);
  }

  @After
  public void after() throws Exception {
  }

  /**
   * Method: getBooks(@RequestParam Long uId)
   */
  @Test
  public void testGetBooks() throws Exception {
    JSONArray expectList = new JSONArray() {{
      add(journalBook1.ToJSONObject());
    }};
    when(journalService.findBooksByuId(1L)).thenReturn(expectList);
    assertEquals(new JSONObject(true) {{
      put("rescode", 0);
      put("journalBooks", expectList);
    }}, journalController.getBooks(1L));

  }

  /**
   * Method: getJournals(@PathVariable Long jounalBookId)
   */
  @Test
  public void testGetJournals() throws Exception {
    JSONArray expectList = new JSONArray() {{
      add(journal1.ToJSONObject());
    }};
    when(journalService.findJournalsByBookId(1L)).thenReturn(expectList);
    assertEquals(new JSONObject(true) {{
      put("rescode", 0);
      put("journals", expectList);
    }}, journalController.getJournals(1L));
  }

  /**
   * Method: deleteBook(@RequestParam Long journalBookId, @RequestHeader("uId") Long uId)
   */
  @Test
  public void testDeleteBook() {
    when(journalService.deleteJournalBook(1L,1L)).thenReturn(0);
    assertEquals(new JSONObject(){{put("rescode",0);}},journalController.deleteBook(1L,1L));
  }

  /**
   * Method: createBook(@RequestBody JSONObject param, @RequestHeader("uId") Long uId)
   */
  @Test
  public void createBook() {
    JSONObject param = new JSONObject(true){{put("name","jbook");put("coverId",1);}};
    assertEquals(new JSONObject(){{put("rescode",0);}},journalController.createBook(param,1L));
    verify(journalService).createJournalBook(anyObject());

  }
  /**
   * Method: createJournal(@RequestBody JSONObject param, @RequestHeader("uId") Long uId)
   */
  @Test
  public void createJournal() {
    JSONObject param = new JSONObject(true){{put("journalBookId",1L);put("journalUrl","url");}};
    when(journalService.createJournal(1L,"url",1L)).thenReturn(0);
    assertEquals(new JSONObject(){{put("rescode",0);}},journalController.createJournal(param,1L));
  }

  /**
   * Method: deleteJournal(@RequestParam Long journalId, @RequestParam Long journalBookId, @RequestHeader("uId") Long uId)
   */
  @Test
  public void deleteJournal() {

    when(journalService.deleteJournal(1L,1L,1L)).thenReturn(0);
    assertEquals(new JSONObject(){{put("rescode",0);}},journalController.deleteJournal(1L,1L,1L));
}
}
