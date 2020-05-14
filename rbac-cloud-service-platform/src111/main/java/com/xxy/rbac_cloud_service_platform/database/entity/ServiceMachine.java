package com.xxy.rbac_cloud_service_platform.database.entity;

import com.alibaba.csp.sentinel.util.StringUtil;
import com.xxy.rbac_cloud_service_platform.config.DashboardConfig;
import com.xxy.rbac_cloud_service_platform.discovery.MachineInfo;
import lombok.Data;

import java.util.Objects;

/**
 * @Author: XXY
 * @Date: 2020/5/12 0:24
 */
@Data
public class ServiceMachine implements Comparable<ServiceMachine>{
    private String app = "";
    private Integer appType = 0;
    private String hostname = "";
    private String ip = "";
    private Integer port = -1;
    private long lastHeartbeat;
    private long heartbeatVersion;

    /**
     * Indicates the version of Sentinel client (since 0.2.0).
     */
    private String version;

    public static ServiceMachine of(String app, String ip, Integer port) {
        ServiceMachine machineInfo = new ServiceMachine();
        machineInfo.setApp(app);
        machineInfo.setIp(ip);
        machineInfo.setPort(port);
        return machineInfo;
    }

    public String toHostPort() {
        return ip + ":" + port;
    }

    public ServiceMachine setVersion(String version) {
        this.version = version;
        return this;
    }

    public boolean isHealthy() {
        long delta = System.currentTimeMillis() - lastHeartbeat;
        return delta < DashboardConfig.getUnhealthyMachineMillis();
    }

    /**
     * whether dead should be removed
     *
     * @return
     */
    public boolean isDead() {
        if (DashboardConfig.getAutoRemoveMachineMillis() > 0) {
            long delta = System.currentTimeMillis() - lastHeartbeat;
            return delta > DashboardConfig.getAutoRemoveMachineMillis();
        }
        return false;
    }


    @Override
    public int compareTo(ServiceMachine o) {
        if (this == o) {
            return 0;
        }
        if (!port.equals(o.getPort())) {
            return port.compareTo(o.getPort());
        }
        if (!StringUtil.equals(app, o.getApp())) {
            return app.compareToIgnoreCase(o.getApp());
        }
        return ip.compareToIgnoreCase(o.getIp());
    }

    @Override
    public String toString() {
        return new StringBuilder("MachineInfo {")
                .append("app='").append(app).append('\'')
                .append(",appType='").append(appType).append('\'')
                .append(", hostname='").append(hostname).append('\'')
                .append(", ip='").append(ip).append('\'')
                .append(", port=").append(port)
                .append(", heartbeatVersion=").append(heartbeatVersion)
                .append(", lastHeartbeat=").append(lastHeartbeat)
                .append(", version='").append(version).append('\'')
                .append(", healthy=").append(isHealthy())
                .append('}').toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (!(o instanceof ServiceMachine)) { return false; }
        ServiceMachine that = (ServiceMachine)o;
        return Objects.equals(app, that.app) &&
                Objects.equals(ip, that.ip) &&
                Objects.equals(port, that.port);
    }

    @Override
    public int hashCode() {
        return Objects.hash(app, ip, port);
    }

    /**
     * Information for log
     *
     * @return
     */
    public String toLogString() {
        return app + "|" + ip + "|" + port + "|" + version;
    }
}
