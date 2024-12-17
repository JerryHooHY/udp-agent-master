package edu.whut.cs.se.udpagent.service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;

/**
 * udp服务端
 */
@Service
@Slf4j
public class UDPServerService {

    private static final int PORT = 8001;

    @Autowired
    @Qualifier("FiberOpticExecutor")
    private ExecutorService executorService;
    @Autowired
    private FiberOpticTask fiberOpticTask;

    /**
     * 开启UDP server监听8001端口
     */
    public void startUDPServer() {
        DatagramSocket serverSocket = null;
        try {
            serverSocket = new DatagramSocket(PORT);
            log.info("UDP server started on port {}", PORT);
        } catch (SocketException e) {
            log.error("创建监听套接字失败：{}", e.toString());
        }

        // 缓冲区大小1KB
        byte[] bytes = new byte[1024];

        while (true) {
            try {
                // 接收数据
                DatagramPacket response = new DatagramPacket(bytes, bytes.length);
                serverSocket.receive(response);
                byte[] responseData = response.getData();

                // String message = ByteUtils.byteArrayToHex(responseData);
                // log.info("Packet data: {}", message);

                // 将任务提交到线程池
                executorService.submit(() -> {
                    try {
                        fiberOpticTask.parseAndSend(responseData);
                    } catch (Exception e) {
                        log.error("解析发送数据异常: {}", e.toString());
                    }
                });

//                 Thread.sleep(10000);
            } catch (RejectedExecutionException e) {
                log.error("线程池饱和，新任务被抛弃：{}", e.getMessage());
            } catch (Exception e) {
                log.error("Error in UDP server：{}", e.getMessage());
            }
        }
    }
}