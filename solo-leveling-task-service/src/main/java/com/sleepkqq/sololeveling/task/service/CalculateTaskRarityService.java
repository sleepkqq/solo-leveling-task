package com.sleepkqq.sololeveling.task.service;

import static com.sleepkqq.sololeveling.avro.task.TaskRarity.COMMON;
import static com.sleepkqq.sololeveling.avro.task.TaskRarity.EPIC;
import static com.sleepkqq.sololeveling.avro.task.TaskRarity.LEGENDARY;
import static com.sleepkqq.sololeveling.avro.task.TaskRarity.RARE;
import static com.sleepkqq.sololeveling.avro.task.TaskRarity.UNCOMMON;

import com.sleepkqq.sololeveling.avro.task.TaskRarity;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import one.util.streamex.StreamEx;
import org.springframework.stereotype.Service;

@Service
public class CalculateTaskRarityService {

  private static final Random RANDOM = new Random();

  public TaskRarity calculateTaskRarity(int userLevel) {
    var rarityProbabilities = getRarityProbabilities(userLevel);

    var randomValue = RANDOM.nextDouble();
    var cumulativeProbability = 0.0;
    for (var entry : rarityProbabilities.entrySet()) {
      cumulativeProbability += entry.getValue();
      if (randomValue < cumulativeProbability) {
        return entry.getKey();
      }
    }

    return COMMON;
  }

  private static Map<TaskRarity, Double> getRarityProbabilities(int userLevel) {
    var probabilities = new HashMap<TaskRarity, Double>();

    if (userLevel < 80) {
      probabilities.put(COMMON, Math.max(0.4, 0.8 - (userLevel / 100.0)));
    } else {
      probabilities.put(COMMON, 0.0);
    }

    probabilities.put(UNCOMMON, Math.min(0.35, Math.max(0.05, userLevel / 150.0)));
    probabilities.put(RARE, Math.min(0.25, Math.max(0.0, (userLevel - 10) / 200.0)));
    probabilities.put(EPIC, Math.min(0.2, Math.max(0.0, (userLevel - 20) / 300.0)));
    probabilities.put(LEGENDARY, Math.min(0.2, Math.max(0.0, (userLevel - 40) / 500.0)));

    normalizeProbabilities(probabilities);
    return probabilities;
  }

  private static void normalizeProbabilities(Map<TaskRarity, Double> probabilities) {
    var total = StreamEx.ofValues(probabilities).mapToDouble(Double::doubleValue).sum();
    probabilities.replaceAll((k, v) -> v / total);
  }
}
