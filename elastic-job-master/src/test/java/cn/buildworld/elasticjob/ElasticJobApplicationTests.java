package cn.buildworld.elasticjob;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ElasticJobApplicationTests {

    @Test
    public void contextLoads() {
    }


    public static void main(String[] args) {
        System.out.println("----------------->" + test());
    }

    private static String test() {
        ExecutorService fixedThreadPool = getExecutorService();
        //根据实际情况填写
        //for (int i = 0; i < 2; i++) {
            fixedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    //具体操作
                    try {

                        ExecutorService fixedThreadPool2 = getExecutorService();
                        fixedThreadPool2.execute(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(10000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                System.out.println("fixedThreadPool2 thread done"+System.currentTimeMillis());
                            }
                        });
                        fixedThreadPool2.shutdown();

                        Thread.sleep(1000);
                        System.out.println("fixedThreadPool thread done");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        //}

        fixedThreadPool.shutdown();

        ExecutorService fixedThreadPool3 = getExecutorService();
        fixedThreadPool3.execute(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    System.out.println("doing" + System.currentTimeMillis());
                    if (fixedThreadPool.isTerminated()) {
                        break;
                    }
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("done" + System.currentTimeMillis());
            }


        });
        fixedThreadPool3.shutdown();
        System.out.println("over" + System.currentTimeMillis());
        return "aaaaaaaaa";
    }

    private static ExecutorService getExecutorService() {
        int processors = Runtime.getRuntime().availableProcessors();
        ExecutorService executorService = Executors.newFixedThreadPool(processors + 1);
        return executorService;
    }

}
