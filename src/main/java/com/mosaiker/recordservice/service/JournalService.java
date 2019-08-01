package com.mosaiker.recordservice.service;
import com.alibaba.fastjson.JSONArray;
import org.springframework.stereotype.Service;

@Service
public interface JournalService {
  JSONArray findBooksByuId(Long uId);
  JSONArray findJournalsByBookId(Long journalBookId);
}
