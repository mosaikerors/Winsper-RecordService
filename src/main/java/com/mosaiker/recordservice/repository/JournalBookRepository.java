package com.mosaiker.recordservice.repository;

import com.mosaiker.recordservice.entity.JournalBook;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JournalBookRepository extends CrudRepository<JournalBook, Long> {
  List<JournalBook> findAllByUId(Long uId);
  JournalBook findByJournalBookId(Long journalBookId);
}
