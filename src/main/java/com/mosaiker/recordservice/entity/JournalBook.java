package com.mosaiker.recordservice.entity;
import java.util.List;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JournalBook {
  @Id
  private Long journalBookId;
  private String name;
  private Integer coverId;
  private Long uId;
  @ElementCollection
  List<Journal> journals;
}
