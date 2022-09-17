package com.qbh.github.simpleexample;

import com.googlecode.aviator.runtime.function.FunctionUtils;
import com.googlecode.aviator.runtime.type.AviatorObject;
import com.googlecode.aviator.runtime.type.AviatorString;
import com.qbh.github.rlog.processor.ParseFunction;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 *
 * @author QBH
 * @date 2022/8/30
 */
@Component
public class PingParseFunction extends ParseFunction {

    @Override
    public AviatorObject call(Map<String, Object> env, AviatorObject arg1) {
        String ping = FunctionUtils.getStringValue(arg1, env);

        return new AviatorString("pong");
    }

    @Override
    public String getName() {
        return "ping";
    }

}
