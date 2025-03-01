package com.sleepkqq.sololeveling.task.model;

public final class Strength extends Attribute {

  private Strength(int value) {
    super(value);
  }

  public static Strength valueOf(int value) {
    return Attribute.valueOf(value, Strength::new);
  }
}