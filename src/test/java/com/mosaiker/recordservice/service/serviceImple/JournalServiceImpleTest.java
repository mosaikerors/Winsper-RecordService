package com.mosaiker.recordservice.service.serviceImple;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import com.alibaba.fastjson.JSONArray;
import com.mosaiker.recordservice.entity.Journal;
import com.mosaiker.recordservice.entity.JournalBook;
import com.mosaiker.recordservice.repository.JournalBookRepository;
import java.util.ArrayList;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * JournalServiceImple Tester.
 *
 * @author <DeeEll-X>
 * @version 1.0
 * @since <pre>Aug 2, 2019</pre>
 */
public class JournalServiceImpleTest {

  @Mock
  private JournalBookRepository journalBookRepository;
  @InjectMocks
  private JournalServiceImple journalServiceImple;
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
   * Method: findBooksByuId(Long uId)
   */
  @Test
  public void testFindBooksByuId() throws Exception {
    when(journalBookRepository.findAllByUId(1L)).thenReturn(new ArrayList<JournalBook>() {{
      add(journalBook1);
    }});
    assertEquals(new JSONArray() {{
      add(journalBook1.ToJSONObject());
    }}, journalServiceImple.findBooksByuId(1L));
  }

  /**
   * Method: findJournalsByBookId(Long journalBookId)
   */
  @Test
  public void testFindJournalsByBookId() throws Exception {
    when(journalBookRepository.findByJournalBookId(1L)).thenReturn(journalBook1);
    assertEquals(new JSONArray() {{
      add(journal1.ToJSONObject());
    }}, journalServiceImple.findJournalsByBookId(1L));
  }


}
