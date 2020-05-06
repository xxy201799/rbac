package com.xxy.rbac_cloud_service_platform.register;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.SPI;

/**
 * @Author: XXY
 * @Date: 2020/5/2 21:24
 */
@SPI("zookeeper")
public interface RegisterOperationConfiguration {
    void init();

    void setUrl(URL url);

    URL getUrl();
    String setConfig(String key, String value);

    String getConfig(String key);

    boolean deleteConfig(String key);

    String setConfig(String group, String key, String value);

    String getConfig(String group, String key);

    boolean deleteConfig(String group, String key);

    String getPath(String key);

    String getPath(String group, String key);
    void destory();
}
