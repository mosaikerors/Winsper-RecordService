package com.mosaiker.recordservice.service.serviceImple;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.junit.Assert.assertEquals;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mosaiker.recordservice.entity.MoodReport;
import com.mosaiker.recordservice.repository.MessageRepository;
import com.mosaiker.recordservice.repository.MoodReportRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * MoodReportServiceImple Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>���� 6, 2019</pre>
 */
public class MoodReportServiceImpleTest {

  private MoodReport report1 = new MoodReport(1L, 2009, 1, 10, "hello", 1, "sea", "sea");
  @Mock
  private MoodReportRepository moodReportRepository;
  @InjectMocks
  private MoodReportServiceImple moodReportServiceImple;

  @Before
  public void before() throws Exception {
    MockitoAnnotations.initMocks(this);
  }

  @After
  public void after() throws Exception {
  }

  /**
   * Method: getMoodReportListByUId(Long uId)
   */
  @Test
  public void testGetMoodReportListByUId() throws Exception {
    List<MoodReport> reportList = new ArrayList<MoodReport>() {{
      add(report1);
    }};
    when(moodReportRepository.findAllByUId(1L)).thenReturn(reportList);
    JSONObject ret = new JSONObject(true);
    ret.put("positiveNum", 0);
    ret.put("neutralNum", 1);
    ret.put("negativeNum", 0);
    ret.put("moodReports", new JSONArray() {{
      add(report1.ToMiniJSONObject());
    }});
    assertEquals(ret, moodReportServiceImple.getMoodReportListByUId(1L));
  }

  /**
   * Method: getMoodReportByMoodReportId(Long mrId)
   */
  @Test
  public void testGetMoodReportByMoodReportId() throws Exception {
    when(moodReportRepository.findById(1L)).thenReturn(Optional.of(report1));
    assertEquals(report1.ToJSONObject(), moodReportServiceImple.getMoodReportByMoodReportId(1L));
  }

  /**
   * Method: addMoodReport(MoodReport moodReport)
   */
  @Test
  public void testAddMoodReport() throws Exception {
    moodReportServiceImple.addMoodReport(report1);
    verify(moodReportRepository).save(report1);
  }


}
