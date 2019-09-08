package com.mosaiker.recordservice.repository;

import com.mosaiker.recordservice.entity.MoodReport;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MoodReportRepository extends MongoRepository<MoodReport, String> {
  List<MoodReport> findAllByUId(Long uId);
}
