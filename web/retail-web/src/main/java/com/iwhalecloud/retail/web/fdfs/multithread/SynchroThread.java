package com.iwhalecloud.retail.web.fdfs.multithread;

import java.util.ArrayList;
import java.util.List;

public abstract class SynchroThread<T> {


    public void start(List<T> list) {
        List<Thread> threadList = new ArrayList<>(list.size());
        for (T t : list) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        exe(t);
                    } catch (Exception e) {
                    }
                }
            });
            thread.start();
            threadList.add(thread);
        }

        for (Thread t : threadList) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public abstract void exe(T t);

}
