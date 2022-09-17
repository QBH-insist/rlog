package com.qbh.github.rlog.processor;

import com.qbh.github.rlog.type.LogRecord;

/**
 * @author QBH
 * @date 2022/8/30
 */
public interface RpLogSink {

    /**
     * persistence rLog
     *
     * @param logRecord
     */
    void sink(LogRecord logRecord);
}
