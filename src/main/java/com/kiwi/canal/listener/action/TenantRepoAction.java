package com.kiwi.canal.listener.action;

import com.alibaba.otter.canal.protocol.CanalEntry;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
@Slf4j
public class TenantRepoAction implements ITableAction{
    private static final String EXCHANGE = "repo";
    private static final String ROUTING_KEY = "";
    @Resource
    private AmqpTemplate amqpTemplate;

    @Override
    public void deleteAction(CanalEntry.RowData rowData) {
        doAction(rowData);
    }

    @Override
    public void updateAction(CanalEntry.RowData rowData) {
        doAction(rowData);
    }

    @Override
    public void insertAction(CanalEntry.RowData rowData) {
        doAction(rowData);
    }

    private void doAction(CanalEntry.RowData rowData) {
        List<CanalEntry.Column> columns = rowData.getBeforeColumnsList();
        String primaryKey = "id";
        String versionKey = "version";
        CanalEntry.Column idColumn = columns.stream().filter(column -> column.getIsKey() && primaryKey.equals(column.getName())).findFirst().orElse(null);
        CanalEntry.Column versionColumn = columns.stream().filter(column -> versionKey.equals(column.getName())).findFirst().orElse(null);
        if (idColumn == null || StringUtils.isBlank(idColumn.getValue())) {
            return;
        }
        if (versionColumn == null || StringUtils.isBlank(versionColumn.getValue())) {
            return;
        }
        String data = idColumn.getValue()+","+versionColumn.getValue();
        amqpTemplate.convertAndSend(EXCHANGE,
                ROUTING_KEY,
                data);
        log.debug("data: {}", data);
    }
}
