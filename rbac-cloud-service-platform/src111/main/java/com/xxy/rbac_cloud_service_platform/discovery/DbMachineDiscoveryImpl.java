package com.xxy.rbac_cloud_service_platform.discovery;

import java.util.List;
import java.util.Set;

/**
 * @Author: XXY
 * @Date: 2020/5/12 0:15
 */
public class DbMachineDiscoveryImpl implements MachineDiscovery {
    @Override
    public List<String> getAppNames() {
        return null;
    }

    @Override
    public Set<AppInfo> getBriefApps() {
        return null;
    }

    @Override
    public AppInfo getDetailApp(String app) {
        return null;
    }

    @Override
    public void removeApp(String app) {

    }

    @Override
    public long addMachine(MachineInfo machineInfo) {
        return 0;
    }

    @Override
    public boolean removeMachine(String app, String ip, int port) {
        return false;
    }
}
