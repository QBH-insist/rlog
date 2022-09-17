package com.qbh.github.rlog.context

import spock.lang.Specification

/**
 *
 * @author QBH
 * @date 2022/9/17
 */
class RLogVariableContextTest extends Specification {

    // 每个用例方法执行前都会执行
    void setup() {
        RLogVariableContext.pushEmptyEle()
    }

    // 每个用例方法执后都会执行
    void cleanup() {
        RLogVariableContext.clear()
    }

    def "PutVariable"() {
        given:
        RLogVariableContext.putVariable("name", "qbh")
    }

    def "GetVariable"() {
        when:
        RLogVariableContext.putVariable("name", "qbh")

        then:
        String value = RLogVariableContext.getVariable("name")
        value == "qbh"
    }

    def "Peek"() {
        when:
        def map = RLogVariableContext.peek()

        then:
        map != null
    }

    def "Clear"() {
        when:
        RLogVariableContext.clear()
        RLogVariableContext.peek()

        then:
        thrown(NullPointerException)
    }
}
