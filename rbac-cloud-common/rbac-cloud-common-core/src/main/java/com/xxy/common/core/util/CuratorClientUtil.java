package com.xxy.common.core.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xxy.common.core.constants.RPCConstant;
import com.xxy.common.core.constants.RbacMessageKeyConstant;
import com.xxy.common.core.exception.RbacBizException;
import io.netty.util.internal.StringUtil;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.framework.api.CuratorListener;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.curator.framework.api.GetChildrenBuilder;
import org.apache.curator.framework.api.GetDataBuilder;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.ZKPaths;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import com.google.common.base.Charsets;
import org.springframework.util.StringUtils;

/**
 * zookeeper工具类
 */
public class CuratorClientUtil {
    private static final Logger LOG = LoggerFactory.getLogger(CuratorClientUtil.class);
    private volatile static CuratorFramework client; // 客户端

    public static CuratorFramework getInstance(String zkAddress) {
        if (client == null) {
            synchronized (CuratorFramework.class) {
                if (client == null) {
                    client = CuratorFrameworkFactory.builder().connectString(zkAddress).sessionTimeoutMs(RPCConstant.sessionTimeout)
                            .connectionTimeoutMs(RPCConstant.connectionTimeout).canBeReadOnly(true).namespace(RPCConstant.ZK_REGISTRY_PATH)
                            .retryPolicy(new ExponentialBackoffRetry(RPCConstant.regryTime, RPCConstant.regryTimes)).defaultData(null).build();
//client.getCuratorListenable().addListener(new ZKNodeEventListener());
                    client.start();
                }
            }
        }
        return client;
    }

    /**
     * 创建节点
     *
     * @param nodeName
     * @param value
     * @return
     */
    public static boolean createNode(CuratorFramework client, String nodeName, String value) {
        boolean isSuccessFlag = false; // 标志
        try {
            Stat stat = client.checkExists().forPath(nodeName); // 检查节点是否存在
            if (stat == null) {
                String opResult = null;
                if (StringUtil.isNullOrEmpty(value)) // 判断节点数据是否是空的
                    opResult = client.create().creatingParentsIfNeeded().forPath(nodeName);
                else
// 不为空就设置节点的数据值
                    opResult = client.create().creatingParentsIfNeeded().forPath(nodeName, value.getBytes(Charsets.UTF_8));
                isSuccessFlag = StringUtils.pathEquals(nodeName, opResult);
            }
        } catch (Exception e) {
            LOG.error("create node in zookeeper failed, please check your arg, caused by:{}", e.getMessage());
            throw new RbacBizException(RbacMessageKeyConstant.ZK_OP_NODE_FAILED, e.getCause());
        }
        return isSuccessFlag;
    }

    /**
     * 更新节点
     *
     * @param path
     * @param value
     * @return
     */
    public static boolean updateNode(CuratorFramework client, String path, String value) {
        boolean isSuccessFlag = false;
        try {
            Stat stat = client.checkExists().forPath(path); // 校验一下是否这个节点是否存在
            if (!StringUtils.isEmpty(stat)) { // 存在就开始更新节点数据
                Stat returnResut = client.setData().forPath(path, value.getBytes(Charsets.UTF_8));
                if (!StringUtils.isEmpty(returnResut)) {
                    isSuccessFlag = true;
                }
            }
        } catch (Exception e) {
            LOG.error("update node in zookeeper failed, please check your arg, caused by:{}", e.getMessage());
            throw new RbacBizException(RbacMessageKeyConstant.ZK_OP_NODE_FAILED, e.getCause());
        }

        return isSuccessFlag;
    }

    /**
     * 删除节点
     *
     * @param path
     */
    public static void delNode(CuratorFramework client, String path) {
        try {
            client.delete().deletingChildrenIfNeeded().forPath(path);
        } catch (Exception e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 获取指定节点下的所有子节点的名称与值
     *
     * @param path
     * @return
     */
    public static Map<String, String> showChildrenDetail(CuratorFramework client, String path) {
        Map<String, String> nodeMap = new HashMap<String, String>();
        try {
            GetChildrenBuilder childrenBuilder = client.getChildren();
            List<String> childrenList = childrenBuilder.forPath(path);
            GetDataBuilder dataBuilder = client.getData();
            if (!CollectionUtils.isEmpty(childrenList)) {
                childrenList.forEach(item -> {
                    String propPath = ZKPaths.makePath(path, item);
                    try {
                        nodeMap.put(item, new String(dataBuilder.forPath(propPath), Charsets.UTF_8));
                    } catch (Exception e) {
// TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                });
            }
        } catch (Exception e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        }
        return nodeMap;
    }

    /**
     * 列出节点下所有的子节点，但是不带子节点的数据
     *
     * @param path
     * @return
     */
    public static List<String> showChildren(CuratorFramework client, String path) {
        List<String> childenList = new ArrayList<String>();
        try {
            GetChildrenBuilder childrenBuilder = client.getChildren();
            childenList = childrenBuilder.forPath(path);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return childenList;
    }

    /**
     * 对节点则增加监听，flag如果是true，就对节点本身监听，false是该节点的子节点增加监听
     *
     * @param path
     * @param flag
     * @throws Exception
     */
    public static void addWatch(CuratorFramework client, String path, boolean flag) throws Exception {
        if (flag)
            client.getData().watched().forPath(path);
        else
            client.getChildren().watched().forPath(path);
    }

    /**
     * 销毁资源
     */
    public static void destory() {
        if (client != null) {
            client.close();
        }
    }

    /**
     * 对节点则增加监听，flag如果是true，就对节点本身监听，false是该节点的子节点增加监听
     *
     * @param path
     * @param flag
     * @param watcher 监视节点
     * @throws Exception
     */
    public static void addWatch(CuratorFramework client, String path, boolean flag, CuratorWatcher watcher) throws Exception {
        if (flag)
            client.getData().usingWatcher(watcher).forPath(path);
        else
            client.getChildren().usingWatcher(watcher).forPath(path);
    }

    // 自定义监听器
    final class ZKNodeEventListener implements CuratorListener {
        @Override
        public void eventReceived(CuratorFramework client, CuratorEvent event) throws Exception {
            System.out.println(event.toString() + "............");
            final WatchedEvent wathEvent = event.getWatchedEvent();
            if (!StringUtils.isEmpty(wathEvent)) {
                System.out.println(wathEvent.getState() + "----------" + wathEvent.getType());
                if (wathEvent.getState() == KeeperState.SyncConnected) {
                    switch (wathEvent.getType()) {
                        case NodeChildrenChanged:
// do
                            break;
                        case NodeDataChanged:
// do
                            break;
                        default:
                            break;
                    }
                }
            }
        }
    }

    // 监视
    final class ZKNodeWather implements CuratorWatcher {
        @Override
        public void process(WatchedEvent event) throws Exception {
// TODO Auto-generated method stub
            if (event.getType() == EventType.NodeChildrenChanged) {
                System.out.println("监视节点");
            }
        }
    }
}
