package com.hjg.shiro.sample.springmvc;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;

/**
 * @Description
 * @Author hjg
 * @Date 2023-03-18 18:39
 */
public interface SampleManager {

    /**
     * Returns the value stored in the user's session.
     *
     * @return the value.
     */
    String getValue();


    /**
     * Sets a value to be stored in the user's session.
     *
     * @param newValue the new value to store in the user's session.
     */
    void setValue(String newValue);

    /**
     * Method that requires <tt>role1</tt> in order to be invoked.
     */
    @RequiresRoles("role1")
    void secureMethod1();

    /**
     * Method that requires <tt>role2</tt> in order to be invoked.
     */
    @RequiresRoles("role2")
    void secureMethod2();

    /**
     * Method that requires <tt>permission1</tt> in order to be invoked.
     */
    @RequiresPermissions("permission2")
    void secureMethod3();
}
