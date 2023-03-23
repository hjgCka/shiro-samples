package com.hjg.shiro.springboot.samples;

import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.text.TextConfigurationRealm;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description
 * @Author hjg
 * @Date 2023-03-23 16:11
 */
@Configuration
@SpringBootApplication
public class CliApp {

    public static void main(String[] args) {

        ConfigurableApplicationContext context = SpringApplication.run(CliApp.class, args);

        // Grab the 'QuickStart' bean, call 'run()' to start the example.
        context.getBean(QuickStart.class).run();
    }

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
}
