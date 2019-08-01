package com.mosaiker.recordservice.controller;

import static org.junit.Assert.assertEquals;
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

    when(journalService.findBooksByuId(3L)).thenReturn(new JSONArray());
    assertEquals(new JSONObject() {{
      put("rescode", 3);
    }}, journalController.getBooks(3L));
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

    when(journalService.findJournalsByBookId(3L)).thenReturn(new JSONArray());
    assertEquals(new JSONObject() {{
      put("rescode", 3);
    }}, journalController.getJournals(3L));
  }


}
