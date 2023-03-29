package com.hjg.security.officeexample;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * @Description
 * @Author hjg
 * @Date 2023-03-15 0:19
 */
public class ExampleShiroUnitTest extends AbstractShiroTest {

    @Test
    public void testSimple() {
        //1.  Create a mock authenticated Subject instance for the test to run:
        Subject subject = Mockito.mock(Subject.class);
        Mockito.when(subject.isAuthenticated()).thenReturn(true);

        //2. Bind the subject to the current thread:
        setSubject(subject);

        //perform test logic here.  Any call to
        //SecurityUtils.getSubject() directly (or nested in the
        //call stack) will work properly.

        Subject testSubject = SecurityUtils.getSubject();
        Assert.assertTrue(testSubject.isAuthenticated());
    }

    @After
    public void tearDownSubject() {
        //3. Unbind the subject from the current thread:
        clearSubject();
    }
}
