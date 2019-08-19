package tob.ok.fine.concurrent.example.volat;

import lombok.extern.slf4j.Slf4j;
import tob.ok.fine.concurrent.annoations.NonThreadSafe;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @program
 * @description: volatile主要的使用场景有如下两种
 * 1、一个线程下入一个flag变量，另一个线程读取其值并进行其它操作
 * 2、如同单例模式中的双重检查
 * @author: zhengLin
 * @date 2019/08/18
 */
@Slf4j
@NonThreadSafe
public class VolatileCountTest {

    // 所有请求数
    private static int totalThread = 5000;
    // 并发请求数
    private static int concurrentThread = 200;
    // 使用volatile关键字保证随时刷新数据至主内存中
    private static volatile int count = 0;

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
        // 这里的++其实等同于下面三步操作
        // read count // 这一步操作可能出现多个线程同时执行，读取到相同的值，这样就会出现问题了
        // add count
        // write count
    }
}
