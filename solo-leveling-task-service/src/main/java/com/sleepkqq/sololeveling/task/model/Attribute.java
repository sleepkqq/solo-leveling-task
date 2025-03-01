package com.sleepkqq.sololeveling.task.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Attribute {

  private final int value;

  protected static <T extends Attribute> T valueOf(int value, AttributeCreator<T> creator) {
    if (value < 0 || value > 10) {
      throw new IllegalArgumentException("value must be between 0 and 10");
    }
    return creator.create(value);
  }

  @FunctionalInterface
  protected interface AttributeCreator<T extends Attribute> {

    T create(int value);
  }
}