package cn.edu.whut.dhdas.service;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * @author huangaokai
 * @date 2024/10/24
 **/
@Component
@Slf4j
public class KafkaService {

    @Value("${kafka.topic}")
    private String topic;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String value){
        // 封装record
        ProducerRecord<String, String> producerRecord = new ProducerRecord<>(topic, value);
        //获取当前时间（毫秒级别）
        String currentTime = getCurrentTime();
        producerRecord.headers().add("sendTime", currentTime.getBytes(StandardCharsets.UTF_8));
        // 发送消息
        kafkaTemplate.send(producerRecord);
        log.info("发送消息至kafka，topic：{}, value：{}", topic, value);
    }

    private String getCurrentTime() {
        // 获取当前时间的Instant对象
        Instant now = Instant.now();

        // 将Instant对象转换为LocalDateTime对象
        LocalDateTime localDateTime = LocalDateTime.ofInstant(now, ZoneId.systemDefault());

        // 定义格式化模式，精度到毫秒
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

        // 格式化时间
        String formattedDateTime = localDateTime.format(formatter);

        // 将结果存储到字符串对象中
        String resultString = formattedDateTime;
        return resultString;
    }
}
