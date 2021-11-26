package com.kiwi.canal.client;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.google.common.collect.Lists;
import com.kiwi.canal.config.property.CanalProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.net.InetSocketAddress;

/**
 * CanalClient客户端
 * @author <a href="luwenmin:luwenmin0502@163.com">luwenmin</a>
 * @version 1.0
 * @since 2021-11-23 16:19:00
 */

@Component
@Slf4j
public class CanalClient implements DisposableBean {
    private CanalConnector canalConnector;

    @Resource
    private CanalProperty canalProperty;

    @Bean
    public CanalConnector getCanalConnector() {
        canalConnector = CanalConnectors.newClusterConnector(Lists.newArrayList(new InetSocketAddress(canalProperty.getCanalHost(), canalProperty.getCanalPort())), canalProperty.getCanalDestination(), canalProperty.getCanalUsername(), canalProperty.getCanalPassword());
        canalConnector.connect();
        // 指定filter，格式 {database}.{table}，这里不做过滤，过滤操作留给用户
        canalConnector.subscribe();
        // 回滚寻找上次中断的位置
        canalConnector.rollback();
        log.info("canal客户端启动成功");
        return canalConnector;
    }

    @Override
    public void destroy() throws Exception {
        if (canalConnector != null) {
            canalConnector.disconnect();
        }
    }
}
