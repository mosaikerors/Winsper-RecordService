package com.mosaiker.recordservice.repository;

import com.mosaiker.recordservice.entity.Diary;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiaryRepository extends CrudRepository<Diary, Long> {
  List<Diary> findAllByUId(Long uId);
}
