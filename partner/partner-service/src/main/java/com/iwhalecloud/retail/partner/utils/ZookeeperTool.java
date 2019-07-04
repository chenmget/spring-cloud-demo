//package com.iwhalecloud.retail.partner.utils;
//
//
//
////10.45.47.87:2285
//
//import org.apache.curator.RetryPolicy;
//import org.apache.curator.framework.CuratorFramework;
//import org.apache.curator.framework.CuratorFrameworkFactory;
//import org.apache.curator.framework.recipes.locks.InterProcessLock;
//import org.apache.curator.framework.recipes.locks.InterProcessMutex;
//import org.apache.curator.retry.ExponentialBackoffRetry;
//
//public class ZookeeperTool {
//
//
//    private static ZookeeperTool zookeeperTool;
//    private static final String  LOCKPATH = "/distribute-lock";
//    private static final String TYPE = "ZK";
//    private CuratorFramework cf; // zk是线程安全的，作为应用级
//    private static String CONNECTSTRING ;
//
//    public static ZookeeperTool getInstance(String connectStr) {
//        if (zookeeperTool == null) {
//            zookeeperTool = new ZookeeperTool(connectStr);
//        }
//        return zookeeperTool;
//    }
//
//    private ZookeeperTool() {}
//    private ZookeeperTool(String connectStr) {
//        CONNECTSTRING = connectStr;
//        initZk();
//    }
//
//    private void initZk() {
//        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
//        cf = CuratorFrameworkFactory.builder()
//                .connectString(CONNECTSTRING)
//                .sessionTimeoutMs(2000)
//                .retryPolicy(retryPolicy)
//                .build();
//        cf.start();
//    }
//    public InterProcessLock getLock(){
//        return new InterProcessMutex(cf, LOCKPATH);
//    }
//
//    public void release(InterProcessMutex lock) throws Exception {
//       lock.release();
//    }
//
//
//}
