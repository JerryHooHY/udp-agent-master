package edu.whut.cs.se.udpagent.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * channel配置类，将application.yml中的关于channel的配置注入到容器中
 * 直接调用ChannelConfig的get方法即可获取哪个通道哪个测点有数据读取
 */
@Data
@Component
@ConfigurationProperties
@Deprecated // 程序写死了，目前没用上
public class ChannelConfig {

    private List<Channel> channels;

    @Data
    public static class Channel {
        private int id;
        private List<Integer> numbers;
    }
}