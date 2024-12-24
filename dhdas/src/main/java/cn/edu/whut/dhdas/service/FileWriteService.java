package cn.edu.whut.dhdas.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class FileWriteService {
    @Value("${datapath}")
    private String dataPath;
    // 锁对象
    private final ReentrantLock lock = new ReentrantLock();
    private final ReentrantLock lock4TmpFile = new ReentrantLock();

    @Async
    public void writeToFile(String content) {
        lock.lock();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(dataPath + "\\" + "data_dh.txt", true))) {
            System.out.println("当前线程id" + Thread.currentThread().getId() + "writeToFile data_dh.txt: " + System.currentTimeMillis());
//            Thread.sleep(5000);
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
        } finally {
            System.out.println("当前线程id" + Thread.currentThread().getId() + "lock释放");
            lock.unlock();
        }

        lock4TmpFile.lock();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(dataPath+ "\\" + "data_dh_tmp.txt", false))) {
            System.out.println("当前线程id" + Thread.currentThread().getId() + "writeToFile data_dh_tmp.txt: " + System.currentTimeMillis());
//            Thread.sleep(5000);
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
        } finally {
            System.out.println("当前线程id" + Thread.currentThread().getId() + "lock4TmpFile释放");
            lock4TmpFile.unlock();
        }
    }
}
