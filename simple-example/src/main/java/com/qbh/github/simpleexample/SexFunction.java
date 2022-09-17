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
 * @date 2022/9/1
 */
@Component
public class SexFunction extends ParseFunction {
    @Override
    public AviatorObject call(Map<String, Object> env, AviatorObject arg1) {
        String sex = FunctionUtils.getStringValue(arg1, env);

        return "male".equals(sex) ? new AviatorString("男") : new AviatorString("女");
    }

    @Override
    public String getName() {
        return "getSexTxt";
    }
}
