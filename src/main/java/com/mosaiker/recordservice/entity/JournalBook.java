package com.mosaiker.recordservice.entity;
import com.alibaba.fastjson.JSONObject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.ElementCollection;
import javax.persistence.GeneratedValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JournalBook {

  @Id
  @GeneratedValue
  private Long journalBookId;
  private String name;
  private Integer coverId;
  private Long uId;
  @ElementCollection
  List<Journal> journals;
}
