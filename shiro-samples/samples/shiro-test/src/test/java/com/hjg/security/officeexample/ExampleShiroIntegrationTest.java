package com.hjg.security.officeexample;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description
 * @Author hjg
 * @Date 2023-03-15 9:24
 */
public class ExampleShiroIntegrationTest extends AbstractShiroTest {

    private static final Logger log = LoggerFactory.getLogger(ExampleShiroIntegrationTest.class);

    @BeforeClass
    public static void beforeClass() {
        //0.  Build and set the SecurityManager used to build Subject instances used in your tests
        //    This typically only needs to be done once per class if your shiro.ini doesn't change,
        //    otherwise, you'll need to do this logic in each test that is different
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
        setSecurityManager(factory.getInstance());
    }

    @Test
    public void testSimple() {
        //1.  Build the Subject instance for the test to run:
        Subject subjectUnderTest = new Subject.Builder(getSecurityManager()).buildSubject();

        //2. Bind the subject to the current thread:
        setSubject(subjectUnderTest);

        //perform test logic here.  Any call to
        //SecurityUtils.getSubject() directly (or nested in the
        //call stack) will work properly.

        Subject currentUser = SecurityUtils.getSubject();
        if (!currentUser.isAuthenticated()) {
            UsernamePasswordToken token = new UsernamePasswordToken("lonestarr", "vespa");
            token.setRememberMe(true);
            try {
                currentUser.login(token);
            } catch (UnknownAccountException uae) {
                log.info("There is no user with username of " + token.getPrincipal());
            } catch (IncorrectCredentialsException ice) {
                log.info("Password for account " + token.getPrincipal() + " was incorrect!");
            } catch (LockedAccountException lae) {
                log.info("The account for username " + token.getPrincipal() + " is locked.  " +
                        "Please contact your administrator to unlock it.");
            }
            // ... catch more exceptions here (maybe custom ones specific to your application?
            catch (AuthenticationException ae) {
                //unexpected condition?  error?
            }
        }

        log.info("User [" + currentUser.getPrincipal() + "] logged in successfully.");

        //test a role:
        if (currentUser.hasRole("schwartz")) {
            log.info("May the Schwartz be with you!");
        } else {
            log.info("Hello, mere mortal.");
        }

        //test a typed permission (not instance-level)
        if (currentUser.isPermitted("lightsaber:wield")) {
            log.info("You may use a lightsaber ring.  Use it wisely.");
        } else {
            log.info("Sorry, lightsaber rings are for schwartz masters only.");
        }

        //a (very powerful) Instance Level permission:
        if (currentUser.isPermitted("winnebago:drive:eagle5")) {
            log.info("You are permitted to 'drive' the winnebago with license plate (id) 'eagle5'.  " +
                    "Here are the keys - have fun!");
        } else {
            log.info("Sorry, you aren't allowed to drive the 'eagle5' winnebago!");
        }

        //all done - log out!
        currentUser.logout();
    }

    @After
    public void tearDownSubject() {
        //3. Unbind the subject from the current thread:
        clearSubject();
    }
}
