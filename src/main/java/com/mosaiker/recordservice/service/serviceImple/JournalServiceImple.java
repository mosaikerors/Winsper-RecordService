package com.mosaiker.recordservice.service.serviceImple;

import com.alibaba.fastjson.JSONArray;
import com.mosaiker.recordservice.entity.Journal;
import com.mosaiker.recordservice.entity.JournalBook;
import com.mosaiker.recordservice.repository.JournalBookRepository;
import com.mosaiker.recordservice.repository.JournalRepository;
import com.mosaiker.recordservice.service.JournalService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JournalServiceImple implements JournalService {

  @Autowired
  private JournalBookRepository journalBookRepository;

  @Autowired
  private JournalRepository journalRepository;

  @Override
  public JSONArray findBooksByuId(Long uId) {
    JSONArray books = new JSONArray();
    List<JournalBook> bookList = journalBookRepository.findAllByUId(uId);
    for(JournalBook book: bookList){
      books.add(book.ToJSONObject());
    }
    return books;
  }

  @Override
  public JSONArray findJournalsByBookId(Long journalBookId){
    JSONArray journals = new JSONArray();
    JournalBook book = journalBookRepository.findByJournalBookId(journalBookId);
    List<Journal> list = book.getJournals();
    for(Journal journal: list){
      journals.add(journal.ToJSONObject());
    }
    return journals;
  }

  @Override
  public int createJournal(Long journalBookId, String journalUrl, Long uId) {
    JournalBook book = journalBookRepository.findByJournalBookId(journalBookId);
    if (book == null) {
      return 3;
    }
    if (book.getUId() != uId) {
      return 4;
    }
    Journal journal = new Journal();
    journal.setJournalUrl(journalUrl);
    journalRepository.save(journal);
    List<Journal> list = book.getJournals();
    list.add(journal);
    book.setJournals(list);
    journalBookRepository.save(book);
    return 0;
  }

  @Override
  public int deleteJournal(Long journalId, Long journalBookId, Long uId) {
    JournalBook book = journalBookRepository.findByJournalBookId(journalBookId);
    if (book == null) {
      return 3;
    }
    if (book.getUId() != uId) {
      return 4;
    }
    Journal journal = journalRepository.findById(journalId).get();
    if (journal == null) {
      return 1;
    }
    journalRepository.deleteById(journalId);
    List<Journal> list = book.getJournals();
    list.remove(journal);
    book.setJournals(list);
    journalBookRepository.save(book);
    return 0;
  }

}
