package com.mosaiker.recordservice.controller;

import com.mosaiker.recordservice.service.serviceImple.JournalServiceImpleTest;
import com.mosaiker.recordservice.service.serviceImple.MessageServiceImpleTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({DiaryController.class, JournalControllerTest.class,MessageControllerTest.class,MoodReportControllerTest.class})
public class ControllerTests {

}
