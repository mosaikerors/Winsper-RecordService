package com.mosaiker.recordservice.repository;

import com.mosaiker.recordservice.entity.Journal;
import com.mosaiker.recordservice.entity.JournalBook;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JournalBookRepository extends JpaRepository<JournalBook,Long> {
  List<JournalBook> findAllByUId(Long uId);
  JournalBook findByJournalBookId(Long journalBookId);
}
