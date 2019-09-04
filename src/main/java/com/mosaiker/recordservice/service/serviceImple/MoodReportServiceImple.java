package com.mosaiker.recordservice.service.serviceImple;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mosaiker.recordservice.entity.MoodReport;
import com.mosaiker.recordservice.repository.MoodReportRepository;
import com.mosaiker.recordservice.service.MoodReportService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MoodReportServiceImple implements MoodReportService {
  @Autowired
  private MoodReportRepository moodReportRepository;

  @Override
  public JSONObject getMoodReportListByUId(Long uId) {
    JSONObject ret = new JSONObject();
    List<MoodReport> reports = moodReportRepository.findAllByUId(uId);
    JSONArray retMoodReports = new JSONArray();
    int[] moods = {0, 0, 0};
    for (MoodReport report : reports) {
      moods[report.getMood()]++;
      retMoodReports.add(report.ToMiniJSONObject());
    }
    ret.put("positiveNum", moods[0]);
    ret.put("neutralNum", moods[1]);
    ret.put("negativeNum", moods[2]);
    ret.put("moodReports", retMoodReports);
    return ret;
  }

  @Override
  public JSONObject getMoodReportByMoodReportId(Long mrId) {
    Optional<MoodReport> moodReport = moodReportRepository.findById(mrId);
    return moodReport.map(MoodReport::ToJSONObject).orElse(null);
  }

  @Override
  public void addMoodReport(MoodReport moodReport) {
    moodReportRepository.save(moodReport);
  }
}
