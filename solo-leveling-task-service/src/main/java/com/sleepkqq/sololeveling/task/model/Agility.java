package com.sleepkqq.sololeveling.task.model;

public final class Agility extends Attribute {

  private Agility(int value) {
    super(value);
  }

  public static Agility valueOf(int value) {
    return Attribute.valueOf(value, Agility::new);
  }
}