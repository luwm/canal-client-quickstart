package com.kiwi.canal.event;

import com.alibaba.otter.canal.protocol.CanalEntry.Entry;

/**
 * @author <a href="luwenmin:luwenmin0502@163.com">luwenmin</a>
 * @version 1.0
 * @since 2021-11-23 16:19:00
 */
public class DeleteAbstractCanalEvent extends AbstractCanalEvent {
    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public DeleteAbstractCanalEvent(Entry source) {
        super(source);
    }
}
