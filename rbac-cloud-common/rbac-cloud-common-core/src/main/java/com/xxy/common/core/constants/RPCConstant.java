package com.xxy.common.core.constants;

/**
 * @Author: XXY
 * @Date: 2020/5/2 11:47
 */
public class RPCConstant {
    //session超时
    public static final  int sessionTimeout = 5000;
    //连接超时
    public static final int connectionTimeout = 5000;

    //创建节点的基础路径
    public static final String ZK_REGISTRY_PATH = "/curator";

    //重试时间
    public static final int regryTime = 1000;
   //重试次数
    public static final int regryTimes = 3;
}
