package com.qbh.github.rlog.type;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author QBH
 * @date 2022/8/30
 */
@ConfigurationProperties(prefix = "rlog")
public class RLogProperties {
    private Boolean enable;

    public Boolean isEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }
}
