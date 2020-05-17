/**
 * 定期清理无效的http连接
 * @author viruser
 *
 */
package com.xxy.rbac_cloud_monitor.config;


import java.util.concurrent.TimeUnit;

import javax.annotation.PreDestroy;

import org.apache.http.conn.HttpClientConnectionManager;

/**
 * 定期清理无效的http连接
 * @author viruser
 *
 */
public class IdleConnectionEvictor extends Thread {

    private final HttpClientConnectionManager manager;
    
    private Integer waitTime;
    
    private Integer idleConTime;
    
    private volatile boolean shutdown = true;
    
    public IdleConnectionEvictor(HttpClientConnectionManager manager, Integer waitTime, Integer idleConTime) {
        this.manager = manager;
        this.waitTime = waitTime;
        this.idleConTime = idleConTime;
        this.start();
    }
    
    @Override
    public void run() {
        try {
            if (shutdown) {
                synchronized (this) {
                    wait(waitTime);
                    manager.closeIdleConnections(idleConTime, TimeUnit.SECONDS);
                    // 关闭失效的连接
                    manager.closeExpiredConnections();
                }
            }
        } catch (Exception e) {
            
        }
    }
    
    @PreDestroy
    public void shutdown() {
        shutdown = false;
        synchronized (this) {
            notifyAll();
        }
    }
    
}