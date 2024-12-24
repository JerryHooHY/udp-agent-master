package edu.whut.cs.se.udpagent;
import edu.whut.cs.se.udpagent.service.AwarenessRecognitionService;
import edu.whut.cs.se.udpagent.service.UDPClientService;
import edu.whut.cs.se.udpagent.service.UDPServerService;
import edu.whut.cs.se.udpagent.service.SourceExplosionService;
import edu.whut.cs.se.udpagent.util.CommandConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import javax.annotation.PreDestroy;
import java.net.UnknownHostException;

@SpringBootApplication
@Slf4j
@EnableScheduling
public class UdpAgentApplication implements CommandLineRunner {

    @Autowired
    private UDPClientService udpClientService;

    @Autowired
    private UDPServerService udpServerService;

    @Autowired
    private SourceExplosionService sourceExplosionService;
    @Autowired
    private AwarenessRecognitionService awarenessRecognitionService;




    public static void main(String[] args) {
        SpringApplication.run(UdpAgentApplication.class, args);
    }

    /**
     * 容器创建发送波长模式指令，并开启UDP server接受数据
     */
    @Override
    public void run(String... args) throws Exception {
        log.info("程序启动成功");
//        udpClientService.sendCommand(CommandConstants.WAVELENGTH_MODE);
//        udpServerService.startUDPServer();
    }

    /**
     * 容器销毁发送结束扫描指令
     */
    @PreDestroy
    public void onShutdown() throws Exception {
        log.info("程序结束");
//        udpClientService.sendCommand(CommandConstants.STOP_SCANNER);
    }

    /**
     * 每分钟发送一次波长模式指令，光纤解调仪如果断线重启之后服务器可以重新发送启动指令
     */
//    @Scheduled(cron = "0 0/1 * * * ? ")
//    public void sendStartCommandTask() throws UnknownHostException {
//        udpClientService.sendCommand(CommandConstants.WAVELENGTH_MODE);
//    }
    @Scheduled(fixedRate = 5000)
    public void getSourceOfExplosion() throws UnknownHostException {
        sourceExplosionService.sendToKafka();

    }

    // 能否合并？
    @Scheduled(fixedRate = 5000)
    public void getAwarenessRecognition() throws UnknownHostException {
        awarenessRecognitionService.sendToKafka();
    }


}
