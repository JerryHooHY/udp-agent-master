package cn.edu.whut.dhdas.service;

import cn.edu.whut.dhdas.entity.DataColumn;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 线程安全的List，用于存储数据
 */
@Service
public class ScheduledTask {
    // 数据持久化暂存
    private final List<DataColumn> tmpData = Collections.synchronizedList(
            new ArrayList<DataColumn>());

    @Value("${datapath}")
    private String dataPath;

    // 主线程向List添加数据
    public void addData(DataColumn dataColumn) {
        System.out.println("----------------------addData");
        tmpData.add(dataColumn);
    }

    @Scheduled(fixedRate = 5000)
    public void persistDataList() {
        synchronized (tmpData) {
            if (tmpData.isEmpty()) {
                System.out.println("No data to persist");
                return;
            }

            StringBuffer content = new StringBuffer();
            for (int i = 0; i < tmpData.size(); ++i) {
                content.append(tmpData.get(i).toString());
            }
            System.out.println("持久化数据: " + content);

            // 注意，对文件的操作未加锁
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(dataPath + "\\" + "data_dh.txt", true))) {
                System.out.println("当前线程id" + Thread.currentThread().getId() + "writeToFile data_dh.txt: " + System.currentTimeMillis());

                writer.write(content.toString());

            } catch (IOException e) {
                e.printStackTrace();
            }

            // 注意，对文件的操作未加锁
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(dataPath + "\\" + "data_tmp.txt", false))) {
                System.out.println("当前线程id" + Thread.currentThread().getId() + "writeToFile data_tmp.txt: " + System.currentTimeMillis());

                writer.write(content.toString());

            } catch (IOException e) {
                e.printStackTrace();
            }



            tmpData.clear();
        }
    }
}
