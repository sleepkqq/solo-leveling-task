package com.sleepkqq.sololeveling.task.config;

import com.sleepkqq.sololeveling.task.kafka.GenerateTasksEvent;
import com.sleepkqq.sololeveling.task.kafka.SaveTasksEvent;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;

import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConfig {

  private static final String SCHEMA_REGISTRY_URL_CONFIG = "schema.registry.url";
  private static final String SPECIFIC_AVRO_READER_CONFIG = "specific.avro.reader";

  private static final String TASK_GROUP_ID = "task-group";

  @Value("${spring.kafka.bootstrap-servers}")
  private String bootstrapServers;

  @Value("${spring.kafka.properties.schema.registry.url}")
  private String schemaRegistryUrl;

  @Bean
  public ProducerFactory<String, GenerateTasksEvent> producerFactoryGenerateTasksEvent() {
    return createProducerFactory();
  }

  @Bean
  public KafkaTemplate<String, GenerateTasksEvent> kafkaTemplateGenerateTasksEvent(
      ProducerFactory<String, GenerateTasksEvent> producerFactory
  ) {
    return new KafkaTemplate<>(producerFactory);
  }

  @Bean
  public ConsumerFactory<String, SaveTasksEvent> consumerFactorySaveTasksEvent() {
    return createConsumerFactory(TASK_GROUP_ID);
  }

  @Bean
  public ConcurrentKafkaListenerContainerFactory<String, SaveTasksEvent> kafkaListenerContainerFactory(
      ConsumerFactory<String, SaveTasksEvent> consumerFactory
  ) {
    var factory = new ConcurrentKafkaListenerContainerFactory<String, SaveTasksEvent>();
    factory.setConsumerFactory(consumerFactory);
    return factory;
  }

  private <V> ProducerFactory<String, V> createProducerFactory() {
    return new DefaultKafkaProducerFactory<>(Map.of(
        ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers,
        ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class,
        ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class,
        SCHEMA_REGISTRY_URL_CONFIG, schemaRegistryUrl
    ));
  }

  public <V> ConsumerFactory<String, V> createConsumerFactory(String groupId) {
    return new DefaultKafkaConsumerFactory<>(Map.of(
        ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers,
        ConsumerConfig.GROUP_ID_CONFIG, groupId,
        ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class,
        ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class,
        SCHEMA_REGISTRY_URL_CONFIG, schemaRegistryUrl,
        SPECIFIC_AVRO_READER_CONFIG, Boolean.TRUE.toString()
    ));
  }
}