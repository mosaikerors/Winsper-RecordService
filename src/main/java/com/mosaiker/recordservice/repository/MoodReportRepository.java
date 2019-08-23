package com.mosaiker.recordservice.repository;

import com.mosaiker.recordservice.entity.MoodReport;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MoodReportRepository extends CrudRepository<MoodReport, Long> {
  List<MoodReport> findAllByUId(Long uId);
}
