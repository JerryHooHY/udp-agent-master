package edu.whut.cs.se.udpagent.parser;

import org.springframework.stereotype.Service;

/**
 * 响应解析接口
 */
@Service
public interface ResponseParser {

    /**
     * 将byte[]转为jsonString
     * @param responseData udp返回的byte[]
     * @return jsonString
     */
    String parseResponse(byte[] responseData);
}