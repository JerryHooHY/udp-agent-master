package edu.whut.cs.se.udpagent.service;
import edu.whut.cs.se.udpagent.parser.explosionSourceParser;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class SourceExplosionService {

    @Value("${fiberOptic.kafka.topic}")
    private String topic;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private explosionSourceParser parser;

    public void sendToKafka() {
        // 生成随机的爆源信息
        String value = parser.getRandomPosition();

        // 封装record
        ProducerRecord<String, String> producerRecord = new ProducerRecord<>(topic, value);

        // 发送消息
        kafkaTemplate.send(producerRecord);
        log.info("发送消息至kafka，topic：{}, value：{}", topic, value);

    }
}
