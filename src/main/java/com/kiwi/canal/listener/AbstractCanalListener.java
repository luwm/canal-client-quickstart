package com.kiwi.canal.listener;

import com.alibaba.otter.canal.protocol.CanalEntry.Entry;
import com.alibaba.otter.canal.protocol.CanalEntry.RowChange;
import com.alibaba.otter.canal.protocol.CanalEntry.RowData;
import com.google.protobuf.InvalidProtocolBufferException;
import com.kiwi.canal.event.AbstractCanalEvent;
import com.kiwi.canal.utils.reflect.ReflectUtils;
import com.kiwi.canal.utils.spring.SpringUtils;
import com.kiwi.canal.utils.text.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;


/**
 * @author <a href="luwenmin:luwenmin0502@163.com">luwenmin</a>
 * @version 1.0
 * @since 2021-11-23 16:19:00
 */
@Slf4j
public abstract class AbstractCanalListener<EVENT extends AbstractCanalEvent> implements ApplicationListener<EVENT> {

    @Override
    public void onApplicationEvent(EVENT event) {
        Entry entry = event.getEntry();
        String table = entry.getHeader().getTableName();
        RowChange change;
        try {
            change = RowChange.parseFrom(entry.getStoreValue());
        } catch (InvalidProtocolBufferException e) {
            log.error("canalEntry_parser_error,根据CanalEntry获取RowChange失败！", e);
            return;
        }
        change.getRowDatasList().forEach(rowData -> doSync(table, rowData));
    }

    protected void doSync(String table, RowData rowData) {
        String beanName = StringUtils.toCamelCase(table) + "Action";
        try{
            Object bean = SpringUtils.getBean(beanName);
            Class[] paramTypeArr = {RowData.class};
            Object[] args = {rowData};
            ReflectUtils.invokeMethod(bean, getMethod(), paramTypeArr, args);
        } catch (Exception e) {
            log.error("同步处理异常 message:{}", e.getMessage());
        }
    }

    protected abstract String getMethod();
}
