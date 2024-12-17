package edu.whut.cs.se.udpagent.service;
import edu.whut.cs.se.udpagent.parser.AwarenessRecognitionParser;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class AwarenessRecognitionService {

    @Value("${fiberOptic.kafka.topic}")
    private String topic;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private AwarenessRecognitionParser parser;

    public void sendToKafka() {
        // 生成随机的安全状态和战损程度
        String safetyStatus = parser.getSafetyStatus();
        String consrtuctionData = parser.getConstructionDegree();
        String deviceData = parser.getDeviceDegree();

        // 封装record
        ProducerRecord<String, String> producerRecordSafety = new ProducerRecord<>(topic, safetyStatus);
        ProducerRecord<String, String> producerRecordConstruction = new ProducerRecord<>(topic, consrtuctionData);
        ProducerRecord<String, String> producerRecordDevice = new ProducerRecord<>(topic, deviceData);


        // 发送消息
        kafkaTemplate.send(producerRecordSafety);
        log.info("发送消息至kafka，topic：{}, value：{}", topic, safetyStatus);
        kafkaTemplate.send(producerRecordConstruction);
        log.info("发送消息至kafka，topic：{}, value：{}", topic, consrtuctionData);
        kafkaTemplate.send(producerRecordDevice);
        log.info("发送消息至kafka，topic：{}, value：{}", topic, deviceData);

    }
}

