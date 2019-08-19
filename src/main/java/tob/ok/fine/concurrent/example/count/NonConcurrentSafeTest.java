package tob.ok.fine.concurrent.example.count;

import lombok.extern.slf4j.Slf4j;
import tob.ok.fine.concurrent.annoations.NonThreadSafe;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @program
 * @description: Java代码模拟并发测试
 * 常用的并发测试工具：
 * 1、postMan
 * 2、apache bench
 * 3、apache JMeter
 * 4、Semaphore、CountDownLatch
 * @author: zhengLin
 * @date 2019/08/18
 */

@Slf4j
@NonThreadSafe
public class NonConcurrentSafeTest {

    // 所有请求数
    private static int totalThread = 5000;
    // 并发请求数
    private static int concurrentThread = 200;
    //
    private static int count = 0;

    public static void main(String[] args) throws InterruptedException {
        // 线程池管理
        ExecutorService executorService = Executors.newCachedThreadPool();
        //
        final Semaphore semaphore = new Semaphore(concurrentThread);
        // 计数器
        final CountDownLatch countDownLatch = new CountDownLatch(totalThread);
        for (int i = 0; i < totalThread; i++) {
            executorService.execute(() -> {
                try {
                    semaphore.acquire();
                } catch (InterruptedException e) {
                    log.error("Exception {}", e);
                }
                add();
                semaphore.release();
            });
            countDownLatch.countDown();
        }

        //
        countDownLatch.await();
        log.info("count {}", count);
        // 关闭连接池
        executorService.shutdown();
    }


    private static void add() {
        count++;
    }

}
