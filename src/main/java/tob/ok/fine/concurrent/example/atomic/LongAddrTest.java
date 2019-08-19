package tob.ok.fine.concurrent.example.atomic;

import lombok.extern.slf4j.Slf4j;
import tob.ok.fine.concurrent.annoations.ThreadSafe;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.LongAdder;

/**
 * @program
 * @description: LongAdder对AtomicLong操作的cas自旋进行了改进，具体实现较为复杂
 * 备注：
 *  AtomicStampedReference正对Atomic*的cas操作下的ABA问题进行了优化处理
 * @author: zhengLin
 * @date 2019/08/18
 */

@Slf4j
@ThreadSafe
public class LongAddrTest {

    // 所有请求数
    private static int totalThread = 5000;
    // 并发请求数
    private static int concurrentThread = 200;
    //
    private static LongAdder count = new LongAdder();

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
        count.increment();
    }

}
