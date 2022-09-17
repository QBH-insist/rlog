package com.qbh.github.simpleexample;

import com.qbh.github.rlog.processor.RpLogSink;
import com.qbh.github.rlog.type.LogRecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 *
 * @author QBH
 * @date 2022/8/30
 */
@Slf4j
@Component
public class DefaultRLogSink implements RpLogSink {

    @Override
    public void sink(LogRecord logRecord) {
        log.info("log record:{}", logRecord);
    }
}
