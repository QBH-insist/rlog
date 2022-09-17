package com.qbh.github.simpleexample;

import com.qbh.github.rlog.annotation.RLog;
import com.qbh.github.rlog.context.RLogVariableContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@SpringBootApplication
@RestController
public class SimpleExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimpleExampleApplication.class, args);
    }

    @RequestMapping("/ping")
    @RLog(bizNo = "0xxx0", uniqueId = "#user.id", payload = "'我是日志:' +' name:' + #user.name + ' sex:' + #user.sex + ' age:' + #age + ' uri:' + #getRequestURI(request)")
    public String ping(HttpServletRequest request, User user) {
        RLogVariableContext.putVariable("age", 18);
        return "pong";
    }

}
