package edu.whut.cs.se.udpagent.service;

import edu.whut.cs.se.udpagent.parser.WavelengthModeResponseParser;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

/**
 * 光纤解调仪服务类
 **/
@Service
@Slf4j
public class FiberOpticTask {

    @Value("${fiberOptic.kafka.topic}")
    private String topic;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private WavelengthModeResponseParser parser;

    /**
     * 解析数据并发送给kafka
     * @param bytes
     * return void
     */
    public void parseAndSend(byte[] bytes) {
        // 解析报文为jsonString
        String value = parser.parseResponse(bytes);

        // 封装record
        ProducerRecord<String, String> producerRecord = new ProducerRecord<>(topic, value);
//        producerRecord.headers().add("device_id", "sensor.test:fiberoptics".getBytes(StandardCharsets.UTF_8));
//        producerRecord.headers().add("content-type", "application/merge-patch+json".getBytes(StandardCharsets.UTF_8));
//        producerRecord.headers().add("correlation-id", "090397".getBytes(StandardCharsets.UTF_8));


        // 发送消息
        kafkaTemplate.send(producerRecord);
        log.info("发送消息至kafka，topic：{}, value：{}", topic, value);

    }
}
