package com.sleepkqq.sololeveling.task.model;

public final class Intelligence extends Attribute {

  private Intelligence(int value) {
    super(value);
  }

  public static Intelligence valueOf(int value) {
    return Attribute.valueOf(value, Intelligence::new);
  }
}