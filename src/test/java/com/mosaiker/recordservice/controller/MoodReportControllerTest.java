package com.mosaiker.recordservice.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mosaiker.recordservice.entity.MoodReport;
import com.mosaiker.recordservice.service.HeanInfoService;
import com.mosaiker.recordservice.service.HttpService;
import com.mosaiker.recordservice.service.MoodReportService;
import java.net.URLDecoder;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * MoodReportController Tester.
 *
 * @author <DeeEll-X>
 * @version 1.0
 * @since <pre>���� 6, 2019</pre>
 */
public class MoodReportControllerTest {

  @Mock
  private MoodReportService moodReportService;
  @Mock
  private HeanInfoService heanInfoService;
  @Mock
  private URLDecoder urlDecoder;
  @Mock
  private HttpService httpService;
  @InjectMocks
  private MoodReportController moodReportController;
  private MoodReport report1 = new MoodReport(1L, 2009, 1, 10, "hello", 1, "sea", "sea");

  @Before
  public void before() throws Exception {
    MockitoAnnotations.initMocks(this);
  }

  @After
  public void after() throws Exception {
  }

  /**
   * Method: getMoodReportList(@RequestParam Long owner)
   */
  @Test
  public void testGetMoodReportList() throws Exception {
    when(moodReportService.getMoodReportListByUId(1L)).thenReturn(new JSONObject() {{
    }});
    assertEquals(new JSONObject() {{
      put("rescode", 0);
    }}, moodReportController.getMoodReportList(1L));
  }

  /**
   * Method: getMoodReport(@RequestParam Long moodReportId)
   */
  @Test
  public void testGetMoodReport() throws Exception {
    when(moodReportService.getMoodReportByMoodReportId(1L)).thenReturn(report1.ToJSONObject());
    when(moodReportService.getMoodReportByMoodReportId(2L)).thenReturn(null);
    JSONObject exp_ok = new JSONObject(true) {{
      put("rescode", 0);
      put("moodReport", report1.ToJSONObject());
    }};
    JSONObject exp_null = new JSONObject() {{
      put("rescode", 1);
    }};

    assertEquals(exp_ok, moodReportController.getMoodReport(1L));
    assertEquals(exp_null, moodReportController.getMoodReport(2L));
  }

  /**
   * Method: generateMoodReport()
   */
  @Test
  public void testGenerateMoodReport() throws Exception {
    JSONArray heans = new JSONArray(){{add(new JSONObject(){{put("uId",1L);put("text","test text");}});add(new JSONObject(){{put("uId",1L);}});}};
    JSONObject weekly = new JSONObject(){{put("heans",heans);}};
    when(heanInfoService.findAllByTime("week")).thenReturn(weekly);

    JSONObject poemEle = new JSONObject(){{put("text","test text");}};
    JSONArray poemArray = new JSONArray(){{add(poemEle);add(new JSONObject());}};
    String poemexp = new JSONObject(true){{put("poems",new JSONArray(){{add("test1");}});}}.toJSONString();
    when(httpService.doGet(anyString())).thenReturn(poemexp);

    JSONObject ret = new JSONObject(true){{put("url","url");put("keyWord","keyWord");put("mood",1);}};
    when(httpService.doPost(anyString(),anyString())).thenReturn(ret.toJSONString());

    assertEquals(new JSONObject(){{put("rescode",0);}},moodReportController.generateMoodReport());
  }

  /**
   * Method: generatePoem()
   */
  @Test
  public void testGeneratePoem() throws Exception {
    MockitoAnnotations.initMocks(HttpService.class);
    String str_ok = new JSONObject(true) {{
      put("success", true);
      put("poem", "origin");
    }}.toJSONString();
    when(httpService.doGet(anyString())).thenReturn(str_ok);
    JSONObject ret_ok = moodReportController.generatePoem();
    assertEquals(0, ret_ok.get("rescode"));
    assertEquals("origin", ret_ok.get("originPoem"));

    String str_fail = new JSONObject(true) {{
      put("success", false);
    }}.toJSONString();
    when(httpService.doGet(anyString())).thenReturn(str_fail);
    assertEquals(new JSONObject() {{
      put("rescode", 1);
    }}, moodReportController.generatePoem());

  }


}
