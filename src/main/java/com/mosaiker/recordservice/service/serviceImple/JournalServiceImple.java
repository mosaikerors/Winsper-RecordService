package com.mosaiker.recordservice.service.serviceImple;

import com.alibaba.fastjson.JSONArray;
import com.mosaiker.recordservice.entity.Journal;
import com.mosaiker.recordservice.entity.JournalBook;
import com.mosaiker.recordservice.repository.JournalBookRepository;
import com.mosaiker.recordservice.service.JournalService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JournalServiceImple implements JournalService {

  @Autowired
  private JournalBookRepository journalBookRepository;

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

}
