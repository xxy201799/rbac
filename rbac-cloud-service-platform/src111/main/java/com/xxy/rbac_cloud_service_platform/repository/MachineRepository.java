package com.xxy.rbac_cloud_service_platform.repository;

import com.xxy.rbac_cloud_service_platform.discovery.MachineInfo;

import java.util.List;

/**
 * @Author: XXY
 * @Date: 2020/5/12 8:12
 */
public interface MachineRepository<T> {

    /**
     * save a machine in database
     * @param machine
     */
    void save(T machine);

    List<T> queryMachineByApp(String app);

    void deleteMachine(String app, String ip, int port);

    List<String> getAllApp();

    void deleteMachineByApp(String app);


}
