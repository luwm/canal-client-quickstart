package com.kiwi.canal.listener.action;

import com.alibaba.otter.canal.protocol.CanalEntry;

public interface ITableAction {
    void deleteAction(CanalEntry.RowData rowData);
    void updateAction(CanalEntry.RowData rowData);
    void insertAction(CanalEntry.RowData rowData);
}
