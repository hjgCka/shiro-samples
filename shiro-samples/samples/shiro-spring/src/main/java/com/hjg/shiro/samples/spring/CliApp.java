package com.hjg.shiro.samples.spring;

import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.text.TextConfigurationRealm;
import org.apache.shiro.spring.config.ShiroAnnotationProcessorConfiguration;
import org.apache.shiro.spring.config.ShiroBeanConfiguration;
import org.apache.shiro.spring.config.ShiroConfiguration;
import org.springframework.context.annotation.*;

/**
 * @Description
 * @Author hjg
 * @Date 2023-03-18 18:18
 */
@Configuration
@Import({ShiroBeanConfiguration.class,
        ShiroConfiguration.class,
        ShiroAnnotationProcessorConfiguration.class})
@ComponentScan("com.hjg.shiro.samples.spring")
public class CliApp {

    /**
     * Example hard coded Realm bean.
     * @return hard coded Realm bean
     */
    @Bean
    public Realm realm() {
        TextConfigurationRealm realm = new TextConfigurationRealm();
        realm.setUserDefinitions("joe.coder=password,user\n" +
                "jill.coder=password,admin");

        realm.setRoleDefinitions("admin=read,write\n" +
                "user=read");
        realm.setCachingEnabled(true);
        return realm;
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(CliApp.class);
        context.getBean(QuickStart.class).run();
//        System.exit(0);
    }
}
