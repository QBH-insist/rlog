package com.qbh.github.simpleexample;

import com.qbh.github.rlog.processor.OperatorAccessor;
import org.springframework.stereotype.Component;

/**
 *
 * @author QBH
 * @date 2022/8/30
 */
@Component
public class OperatorAccessorImpl implements OperatorAccessor {

    @Override
    public Object getOperator() {
        User user = new User();
        user.setName("i am operator");

        return user;
    }
}
