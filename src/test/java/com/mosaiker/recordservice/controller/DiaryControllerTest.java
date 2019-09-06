package com.mosaiker.recordservice.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.mosaiker.recordservice.entity.Diary;
import com.mosaiker.recordservice.repository.DiaryRepository;
import com.mosaiker.recordservice.service.UserInfoService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

/**
* DiaryController Tester.
*
* @author <DeeEll-X>
* @since <pre>���� 6, 2019</pre>
* @version 1.0
*/
public class DiaryControllerTest {
  @Mock
  private DiaryRepository diaryRepository;
  @Mock
  private UserInfoService userInfoService;
  @InjectMocks
  private DiaryController diaryController;
  private Diary diary1 = new Diary("testDiary","this is a test diary",1L,"user1");
@Before
public void before() throws Exception {
  MockitoAnnotations.initMocks(this);
}

@After
public void after() throws Exception {
}

/**
*
* Method: getDiaryList(@RequestParam Long owner)
*
*/
@Test
public void testGetDiaryList() throws Exception {
  List<Diary> diaryList = new ArrayList<Diary>(){{add(diary1);}};
  when(diaryRepository.findAllByUId(1L)).thenReturn(diaryList);
  JSONObject exp = new JSONObject(true){{put("rescode",0);put("diaries",new JSONArray(){{add(diary1.ToMiniJSONObject());}});}};
  assertEquals(exp,diaryController.getDiaryList(1L));
}

/**
*
* Method: getDiary(@RequestParam Long diaryId)
*
*/
@Test
public void testGetDiary() throws Exception {
  when(diaryRepository.findById(1L)).thenReturn(Optional.of(diary1));
  when(diaryRepository.findById(2L)).thenReturn(Optional.ofNullable(null));
  JSONObject exp_ok = diary1.ToJSONObject();
  exp_ok.put("rescode",0);
  JSONObject exp_null = new JSONObject(){{put("rescode",1);}};
  assertEquals(exp_ok,diaryController.getDiary(1L));
  assertEquals(exp_null,diaryController.getDiary(2L));
}

/**
*
* Method: writeDiary(@RequestBody JSONObject param, @RequestHeader("uId") Long uId)
*
*/
@Test
public void testWriteDiary() throws Exception {
  JSONObject param = new JSONObject(){{put("title","test");put("text","test text");}};
  JSONObject info = new JSONObject(){{put("username","user1");}};
  when(userInfoService.getSimpleInfo(1L)).thenReturn(info);
  JSONObject exp = new JSONObject(){{put("rescode",0);}};
  assertEquals(exp,diaryController.writeDiary(param,1L));
  verify(diaryRepository).save(anyObject());
}

/**
*
* Method: deleteDiary(@RequestParam Long diaryId, @RequestHeader("uId") Long uId)
*
*/
@Test
public void testDeleteDiary() throws Exception {
  when(diaryRepository.findById(1L)).thenReturn(Optional.of(diary1));
  when(diaryRepository.findById(2L)).thenReturn(Optional.ofNullable(null));

  JSONObject exp_ok = new JSONObject(){{put("rescode",0);}};
  JSONObject exp_null = new JSONObject(){{put("rescode",1);}};
  JSONObject exp_wrong = new JSONObject(){{put("rescode",2);}};
  assertEquals(exp_null,diaryController.deleteDiary(2L,1L));
  assertEquals(exp_ok,diaryController.deleteDiary(1L,1L));
  verify(diaryRepository).deleteById(1L);
  assertEquals(exp_wrong,diaryController.deleteDiary(1L,2L));

}


}
