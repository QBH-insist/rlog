package com.qbh.github.rlog.autoconfigure;

import com.qbh.github.rlog.aspect.RLogAviatorAspect;
import com.qbh.github.rlog.processor.OperatorAccessor;
import com.qbh.github.rlog.processor.ParseFunction;
import com.qbh.github.rlog.processor.RLogEvaluator;
import com.qbh.github.rlog.processor.RpLogSink;
import com.qbh.github.rlog.type.RLogProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author QBH
 * @date 2022/8/30
 */
@Configuration
@EnableConfigurationProperties(RLogProperties.class)
public class RLogAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "rlog", name = "enable", havingValue = "true")
    public RLogEvaluator rLogEvaluator(@Autowired List<ParseFunction> functions) {
        RLogEvaluator rLogEvaluator = new RLogEvaluator();

        rLogEvaluator.registerFunctions(functions);

        return rLogEvaluator;
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "rlog", name = "enable", havingValue = "true")
    public RLogAviatorAspect rLogAviatorAspect(
            @Autowired OperatorAccessor operatorAccessor,
            @Autowired RpLogSink rpLogSink,
            @Autowired RLogEvaluator rLogEvaluator) {
        return new RLogAviatorAspect(operatorAccessor, rpLogSink, rLogEvaluator);
    }
}
