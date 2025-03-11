package com.sleepkqq.sololeveling.task.model;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Getter
@Setter
@NoArgsConstructor
@Document(indexName = "tasks")
public class Task {

  @Id
  private String id;

  @Field(type = FieldType.Text)
  private String title;

  @Field(type = FieldType.Text)
  private String description;

  @Field(type = FieldType.Integer)
  private int experience;

  @Enumerated(EnumType.STRING)
  @Field(type = FieldType.Keyword)
  private TaskRarity rarity;

  private Set<TaskTopic> topics;

  @Field(type = FieldType.Integer)
  private int agility;

  @Field(type = FieldType.Integer)
  private int strength;

  @Field(type = FieldType.Integer)
  private int intelligence;

  @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second)
  private LocalDateTime createdAt;

  @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second)
  private LocalDateTime updatedAt;

  public Task(
      String title,
      String description,
      int experience,
      TaskRarity rarity,
      Set<TaskTopic> topics,
      Agility agility,
      Strength strength,
      Intelligence intelligence
  ) {
    this.id = UUID.randomUUID().toString();
    this.title = title;
    this.description = description;
    this.experience = experience;
    this.rarity = rarity;
    this.topics = topics;
    this.agility = agility.getValue();
    this.strength = strength.getValue();
    this.intelligence = intelligence.getValue();
    this.createdAt = LocalDateTime.now();
    this.updatedAt = LocalDateTime.now();
  }

  public Agility getAgility() {
    return Agility.valueOf(agility);
  }

  public Strength getStrength() {
    return Strength.valueOf(strength);
  }

  public Intelligence getIntelligence() {
    return Intelligence.valueOf(intelligence);
  }
}

