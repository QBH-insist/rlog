package com.qbh.github.rlog.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Stack;

/**
 * context of the storage variables in the multi-thread environment
 *
 * and must be used in the method of @RLog modification and enable RLog config，or-else throw NullPointerException
 *
 * @author QBH
 * @date 2022/8/26
 */
public class RLogVariableContext {

    private static final Logger log = LoggerFactory.getLogger(RLogVariableContext.class);

    private static final InheritableThreadLocal<Stack<Map<String, Object>>> variableTable =
            new InheritableThreadLocal<>();


    /** 每执行一个被 @RLog 修饰的方法前手动压栈一个 Map, 防止连续调用 @RLog 修饰的方法时出现参数覆盖、参数误释放等异常情况 */
    public static void pushEmptyEle() {
        Stack<Map<String, Object>> stack = variableTable.get();

        if (Objects.isNull(stack)) {
            stack = new Stack<>();
            variableTable.set(stack);
        }

        stack.push(new HashMap<>(4));
    }

    public static void putVariable(String key, Object value) {
        if (Objects.isNull(key) || Objects.isNull(value)) {
            return;
        }
        // fix NullPointerException when rLog disable
        if (Objects.isNull(variableTable.get()) || Objects.isNull(variableTable.get().peek())) {
            return;
        }

        variableTable.get().peek().put(key, value);
    }

    public static Object getVariable(String key) {
        return peek().get(key);
    }

    public static Map<String, Object> peek() {
        Stack<Map<String, Object>> stack = variableTable.get();
        
        return stack.peek();
    }

    public static Map<String, Object> clear() {
        Stack<Map<String, Object>> stack = variableTable.get();

        if (Objects.isNull(stack) || stack.isEmpty()) {
            return null;
        }

        Map<String, Object> map = stack.pop();

        if (stack.isEmpty()) {
            log.debug("rLog variable context stack pop");
            variableTable.remove();
        }

        return map;
    }
}
