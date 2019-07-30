package com.mosaiker.recordservice.service;
import com.alibaba.fastjson.JSONArray;
import org.springframework.stereotype.Service;

@Service
public interface JournalService {
  public JSONArray findBooksByuId(Long uId);
  public JSONArray findJournalsByBookId(Long journalBookId);
}
