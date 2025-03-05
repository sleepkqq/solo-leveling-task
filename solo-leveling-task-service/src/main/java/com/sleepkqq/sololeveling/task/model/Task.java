package com.sleepkqq.sololeveling.task.model;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Getter
@Document(indexName = "tasks")
public class Task {

  @Id
  private final String id;

  @Field(type = FieldType.Text)
  private final String title;

  @Field(type = FieldType.Text)
  private final String description;

  @Field(type = FieldType.Integer)
  private final int experience;

  @Enumerated(EnumType.STRING)
  @Field(type = FieldType.Keyword)
  private final TaskRarity rarity;

  private final Set<TaskTopic> topics;

  @Field(type = FieldType.Integer)
  private final int agility;

  @Field(type = FieldType.Integer)
  private final int strength;

  @Field(type = FieldType.Integer)
  private final int intelligence;

  @Field(type = FieldType.Date)
  private final LocalDateTime createdAt;

  @Field(type = FieldType.Date)
  private final LocalDateTime updatedAt;

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

