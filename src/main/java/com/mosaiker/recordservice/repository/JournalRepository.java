package com.mosaiker.recordservice.repository;

import com.mosaiker.recordservice.entity.Journal;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JournalRepository extends CrudRepository<Journal, Long> {

}
