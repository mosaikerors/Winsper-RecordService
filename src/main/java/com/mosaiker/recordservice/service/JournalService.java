package com.mosaiker.recordservice.service;
import com.alibaba.fastjson.JSONArray;
import com.mosaiker.recordservice.entity.Journal;
import com.mosaiker.recordservice.entity.JournalBook;
import org.springframework.stereotype.Service;

@Service
public interface JournalService {
  JSONArray findBooksByuId(Long uId);
  JSONArray findJournalsByBookId(Long journalBookId);
  int createJournal(Long journalBookId, String journalUrl, Long uId);
  int deleteJournal(Long journalId, Long journalBookId, Long uId);
  void createJournalBook(JournalBook journalBook);
  int deleteJournalBook(Long journalBookId, Long uId);
}
