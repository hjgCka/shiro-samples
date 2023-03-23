package com.hjg.shiro.springboot.samples;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Description
 * @Author hjg
 * @Date 2023-03-23 16:13
 */
@Component
public class SimpleService {

    private static Logger log = LoggerFactory.getLogger(SimpleService.class);

    @RequiresPermissions("write")
    public void writeRestrictedCall() {
        log.info("executing method that requires the 'write' permission");
    }

    @RequiresPermissions("read")
    public void readRestrictedCall() {
        log.info("executing method that requires the 'read' permission");
    }
}
