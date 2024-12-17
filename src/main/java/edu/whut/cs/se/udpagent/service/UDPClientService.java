package edu.whut.cs.se.udpagent.service;
import edu.whut.cs.se.udpagent.util.ByteUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * UDP客户端
 */
@Service
@Slf4j
public class UDPClientService {

    private final DatagramSocket socket;

    @Value("${fiberOptic.server.ip}")
    private String serverAddress;
    @Value("${fiberOptic.server.port}")
    private int serverPort;

    public UDPClientService() throws Exception {
        this.socket = new DatagramSocket();
    }

    /**
     * 发送指令
     * @param commandData
     */
    public void sendCommand(byte[] commandData) throws UnknownHostException {
        InetAddress address = InetAddress.getByName(serverAddress);
        DatagramPacket packet = new DatagramPacket(commandData, commandData.length, address, serverPort);
        try {
            socket.send(packet);
        } catch (IOException e) {
            log.error("发送指令失败：{}", e.getMessage());
        }
        log.info("发送指令: {}", ByteUtils.byteArrayToHex(commandData));
    }

}