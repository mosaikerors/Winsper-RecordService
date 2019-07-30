package com.mosaiker.recordservice.service.serviceImple;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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
      JSONObject onebook = new JSONObject();
      onebook.put("journalBookId",book.getJournalBookId());
      onebook.put("name",book.getName());
      onebook.put("coverId",book.getCoverId());
      books.add(onebook);
    }
    return books;
  }

  public JSONArray findJournalsByBookId(Long journalBookId){
    JSONArray journals = new JSONArray();
    JournalBook book = journalBookRepository.findByJournalBookId(journalBookId);
    List<Journal> list = book.getJournals();
    for(Journal journal: list){
      JSONObject onejournal = new JSONObject();
      onejournal.put("journalId", journal.getJournalId());
      onejournal.put("journalUrl",journal.getJournalUrl());
      journals.add(onejournal);
    }
    return journals;
  }

}
