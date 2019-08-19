package tob.ok.fine.concurrent.example.atomic;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @program
 * @description:
 * @author: zhengLin
 * @date 2019/08/18
 */

@Slf4j
public class AtomicBooleanTest {
    // 所有请求数
    private static int totalThread = 5000;
    // 并发请求数
    private static int concurrentThread = 200;
    //
    private static AtomicBoolean isHappened = new AtomicBoolean(false);

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
                test();
                semaphore.release();
            });
            countDownLatch.countDown();
        }

        //
        countDownLatch.await();
        log.info("count {}", isHappened);
        // 关闭连接池
        executorService.shutdown();
    }


    private static void test() {
        if (isHappened.compareAndSet(false, true)) {
            log.info("execute this ");
        }
    }


}
