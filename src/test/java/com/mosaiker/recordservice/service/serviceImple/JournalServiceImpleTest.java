package com.mosaiker.recordservice.service.serviceImple;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.alibaba.fastjson.JSONArray;
import com.mosaiker.recordservice.entity.Journal;
import com.mosaiker.recordservice.entity.JournalBook;
import com.mosaiker.recordservice.repository.JournalBookRepository;
import com.mosaiker.recordservice.repository.JournalRepository;
import java.util.ArrayList;
import java.util.List;
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
  @Mock
  private JournalRepository journalRepository;
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

  /**
   * Method: createJournal(Long journalBookId, String journalUrl, Long uId)
   */
  @Test
  public void testCreateJournal() {
    JournalBook book1 = new JournalBook("book1", 1, 1L);
    JournalBook book_fail = new JournalBook("book_fail", 1, 2L);
    when(journalBookRepository.findByJournalBookId(0L)).thenReturn(null);
    when(journalBookRepository.findByJournalBookId(1L)).thenReturn(book1);
    when(journalBookRepository.findByJournalBookId(2L)).thenReturn(book_fail);

    when(journalRepository.save(anyObject())).thenReturn(new Journal() {{
      setJournalUrl("url");
    }});

    assertEquals(3, journalServiceImple.createJournal(0L, "url", 1L));
    assertEquals(4, journalServiceImple.createJournal(2L, "url", 1L));
    assertEquals(0, journalServiceImple.createJournal(1L, "url", 1L));
  }

  /**
   * Method: deleteJournal(Long journalId, Long journalBookId, Long uId)
   */
  @Test
  public void testDeleteJournal() {

    Journal journal1 = new Journal(1L, "url");
    JournalBook book1 = new JournalBook("book1", 1, 1L);
    JournalBook book_fail = new JournalBook("book_fail", 1, 2L);
    JournalBook book_null = new JournalBook("book_null", 1, 1L);

    book1.setJournals(new ArrayList<Journal>() {{ add(journal1); }});

    when(journalBookRepository.findByJournalBookId(0L)).thenReturn(null);
    when(journalBookRepository.findByJournalBookId(1L)).thenReturn(book1);
    when(journalBookRepository.findByJournalBookId(2L)).thenReturn(book_fail);
    when(journalBookRepository.findByJournalBookId(3L)).thenReturn(book_null);

    when(journalRepository.findJournalByJournalId(1L)).thenReturn(journal1);
    when(journalRepository.findJournalByJournalId(2L)).thenReturn(null);

    assertEquals(3, journalServiceImple.deleteJournal(1L, 0L, 1L));
    assertEquals(2, journalServiceImple.deleteJournal(1L, 2L, 1L));
    assertEquals(1, journalServiceImple.deleteJournal(2L, 3L, 1L));
    assertEquals(0, journalServiceImple.deleteJournal(1L, 1L, 1L));

  }

  /**
   * Method: testCreateJournal(JournalBook journalBook)
   */
  @Test
  public void testCreateJournalBook() {
    JournalBook book1 = new JournalBook("book1", 1, 1L);
    when(journalBookRepository.save(book1)).thenReturn(book1);
    journalServiceImple.createJournalBook(book1);

  }

  /**
   * Method: deleteJournalBook(Long journalBookId, Long uId)
   */
  @Test
  public void testDeleteJournalBook() {
    JournalBook book1 = new JournalBook("book1", 1, 1L);
    when(journalBookRepository.findByJournalBookId(1L)).thenReturn(book1);

    assertEquals(2, journalServiceImple.deleteJournalBook(1L, 2L));
    assertEquals(0, journalServiceImple.deleteJournalBook(1L, 1L));
  }

}
