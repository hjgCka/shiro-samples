package com.hjg.security.officeexample;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.support.SubjectThreadState;
import org.apache.shiro.util.LifecycleUtils;
import org.apache.shiro.util.ThreadState;
import org.junit.AfterClass;

/**
 * @Description
 * @Author hjg
 * @Date 2023-03-14 23:25
 */
public abstract class AbstractShiroTest {

    private static ThreadState subjectThreadState;

    public AbstractShiroTest() {}

    protected void setSubject(Subject subject) {
        clearSubject();
        subjectThreadState = createThreadState(subject);
        subjectThreadState.bind();
    }
    protected Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    protected void clearSubject() {
        doClearSubject();
    }

    protected static void doClearSubject() {
        if (subjectThreadState != null) {
            subjectThreadState.clear();
            subjectThreadState = null;
        }
    }

    protected ThreadState createThreadState(Subject subject) {
        return new SubjectThreadState(subject);
    }

    protected static void setSecurityManager(SecurityManager securityManager) {
        SecurityUtils.setSecurityManager(securityManager);
    }

    protected static SecurityManager getSecurityManager() {
        return SecurityUtils.getSecurityManager();
    }

    @AfterClass
    public static void tearDown() {
        doClearSubject();

        SecurityManager securityManager = getSecurityManager();
        LifecycleUtils.destroy(securityManager);

        setSecurityManager(null);
    }
}
