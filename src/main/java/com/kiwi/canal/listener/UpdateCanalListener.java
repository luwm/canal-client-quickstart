package com.kiwi.canal.listener;

import com.kiwi.canal.event.UpdateAbstractCanalEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


/**
 * @author <a href="luwenmin:luwenmin0502@163.com">luwenmin</a>
 * @version 1.0
 * @since 2021-11-23 16:19:00
 */
@Component
@Slf4j
public class UpdateCanalListener extends AbstractCanalListener<UpdateAbstractCanalEvent> {
    private static final String SYNC_METHOD = "updateAction";

    @Override
    protected String getMethod() {
        return SYNC_METHOD;
    }
}
