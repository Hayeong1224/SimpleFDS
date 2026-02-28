package com.example.core.config;

import com.example.core.transfer.event.TransferRequestedEvent;
import com.example.core.transfer.event.TransferResultEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JacksonJsonDeserializer;
import org.springframework.kafka.support.serializer.JacksonJsonSerializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka // @KafkaListener 실행
@Configuration
public class KafkaConfig {

    /* =========================
       Producer
       ========================= */

    @Bean
    public ProducerFactory<String, Object> producerFactory() {
        Map<String, Object> props = new HashMap<>();

        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JacksonJsonSerializer.class);

        return new DefaultKafkaProducerFactory<>(
                props,
                new StringSerializer(),
                new JacksonJsonSerializer<Object>()
                        .noTypeInfo()
        );
    }


    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    /* =========================
       Consumer - 1. TransferRequestedEvent
       ========================= */

    @Bean
    public ConsumerFactory<String, TransferRequestedEvent> transferRequestedConsumerFactory() {
        Map<String, Object> props = new HashMap<>();

        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "fds-group");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        return new DefaultKafkaConsumerFactory<>(
                props,
                new StringDeserializer(),
                new JacksonJsonDeserializer<>(TransferRequestedEvent.class)
                        .ignoreTypeHeaders()
                        .trustedPackages("com.example.core.transfer.event")
        );
    }

    /* =========================
       Consumer - 2. TransferResultEvent
       ========================= */

    @Bean
    public ConsumerFactory<String, TransferResultEvent> transferResultEConsumerFactory() {
        Map<String, Object> props = new HashMap<>();

        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "transfer-result-group");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        return new DefaultKafkaConsumerFactory<>(
                props,
                new StringDeserializer(),
                new JacksonJsonDeserializer<>(TransferResultEvent.class)
                        .ignoreTypeHeaders()
                        .trustedPackages("com.example.core.transfer.event")
        );
    }

    /* =========================
       ListenerFactory - 어떤 ConsumerFactory 쓸 지 결정
       ========================= */

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, TransferRequestedEvent>
    transferRequestedKafkaListenerContainerFactory() {

        ConcurrentKafkaListenerContainerFactory<String, TransferRequestedEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(transferRequestedConsumerFactory());

        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, TransferResultEvent>
    transferResultKafkaListenerContainerFactory() {

        ConcurrentKafkaListenerContainerFactory<String, TransferResultEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(transferResultEConsumerFactory());

        return factory;
    }


}
