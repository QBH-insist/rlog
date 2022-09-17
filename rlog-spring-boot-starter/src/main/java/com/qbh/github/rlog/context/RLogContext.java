package com.qbh.github.rlog.context;

import com.qbh.github.rlog.annotation.RLog;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author QBH
 * @date 2022/8/29
 */
public class RLogContext implements RLogEnvironment {

    private static final ParameterNameDiscoverer discoverer = new DefaultParameterNameDiscoverer();

    private final Map<String, Object> variableTable = new HashMap<>(8);

    private final Method method;

    private final Object[] args;

    private final RLog rLog;

    RLogContext(Method method, Object[] args, RLog rLog) {
        this.method = method;
        this.args = args;
        this.rLog = rLog;
    }

    public Method getMethod() {
        return method;
    }

    public Object[] getArgs() {
        return args;
    }

    public RLog getLog() {
        return rLog;
    }

    /**
     * parse method args to variable
     *
     * @return
     */
    public RLogContext parseArgs() {
        String[] parameterNames = discoverer.getParameterNames(this.method);

        for (int i = 0; i < args.length; i++) {
            variableTable.put(parameterNames[i], args[i]);
        }

        return this;
    }

    /**
     * add variable
     *
     * @param key
     * @param value
     */
    public RLogContext putVariable(String key, Object value) {
        variableTable.put(key, value);

        return this;
    }

    public Object getVariable(String key) {
        return variableTable.get(key);
    }

    public boolean contain(String key) {
        return variableTable.containsKey(key);
    }

    @Override
    public Map<String, Object> getEnvironment() {
        return variableTable;
    }

    public String getPayload() {
        return rLog.payload();
    }

    public String getUniqueId() {
        return rLog.uniqueId();
    }
}
