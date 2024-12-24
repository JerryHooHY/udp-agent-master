import cn.edu.whut.dhdas.service.FileWriteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
public class TestFileWriterService {
    @Autowired
    FileWriteService fileWriteService;

    @Test
    public void testFileWriter() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(4);

        for (int i = 0; i < 10; i++) {
            String content = "Line " + i + "\n";
            executorService.submit(() -> fileWriteService.writeToFile(content));
//            System.out.println("i=" + i + ", sleep....");
            System.out.println("testFileWriter: " + System.currentTimeMillis());
//            Thread.sleep(5000);
            System.out.println(i);
        }
        Thread.sleep(50000);

    }
}
