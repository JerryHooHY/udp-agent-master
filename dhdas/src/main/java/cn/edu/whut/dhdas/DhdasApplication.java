package cn.edu.whut.dhdas;

import cn.edu.whut.dhdas.protocol.InstrumentProtocol;
import cn.edu.whut.dhdas.protocol.ParseBuffer;
import cn.edu.whut.dhdas.service.TcpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.net.Socket;

@SpringBootApplication
public class DhdasApplication implements CommandLineRunner {
    @Value("${dhdas.ip}")
    String ip;

    @Value("${dhdas.port}")
    String port;

    @Autowired
    TcpClient tcpClient;
    @Autowired
    InstrumentProtocol protocolCore;
    @Autowired
    ParseBuffer parseBuffer;

    public static void main(String[] args) {
        SpringApplication.run(DhdasApplication.class, args);
    }

    @Override
    public void run(String... args){
        // 建立tcp连接
        tcpClient.Connect(ip, Integer.parseInt(port));
        Socket socket = tcpClient.getM_SocketClient();

        // 启动采集
        byte[] cmdArray = protocolCore.GetStartSampleCmd("");
        tcpClient.SendCmdToServer(cmdArray, cmdArray.length);
        // 获取当前采样频率
        cmdArray = protocolCore.GetSampleFreqCmd();
        tcpClient.SendCmdToServer(cmdArray, cmdArray.length);

        byte[] byteData = new byte[1024];
        while (socket.isConnected())
        {
            try {
                int nLength = socket.getInputStream().read(byteData);
                if (nLength <= 0)
                    continue;
                parseBuffer.appendNetData(byteData, nLength);
                // Thread.sleep(10);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
