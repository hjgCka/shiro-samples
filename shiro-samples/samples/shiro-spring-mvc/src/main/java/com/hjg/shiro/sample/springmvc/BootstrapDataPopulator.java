package com.hjg.shiro.sample.springmvc;

import org.apache.shiro.crypto.hash.Sha256Hash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * @Description
 * @Author hjg
 * @Date 2023-03-18 18:40
 */
public class BootstrapDataPopulator implements InitializingBean {

    private static final String CREATE_TABLES = "create table users (\n" +
            "    username varchar(255) primary key,\n" +
            "    password varchar(255) not null\n" +
            ");\n" +
            "\n" +
            "create table roles (\n" +
            "    role_name varchar(255) primary key\n" +
            ");\n" +
            "\n" +
            "create table user_roles (\n" +
            "    username varchar(255) not null,\n" +
            "    role_name varchar(255) not null,\n" +
            "    constraint user_roles_uq unique ( username, role_name )\n" +
            ");\n" +
            "\n" +
            "create table roles_permissions (\n" +
            "    role_name varchar(255) not null,\n" +
            "    permission varchar(255) not null,\n" +
            "    primary key (role_name, permission)\n" +
            ");";

    private static final Logger log = LoggerFactory.getLogger(BootstrapDataPopulator.class);

    protected DataSource dataSource = null;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void afterPropertiesSet() throws Exception {
        //because we're using an in-memory hsqldb for the sample app, a new one will be created each time the
        //app starts, so create the tables and insert the 2 sample users on bootstrap:

        JdbcTemplate jdbcTemplate = new JdbcTemplate(this.dataSource);
        jdbcTemplate.execute(CREATE_TABLES);

        //password is 'user1' SHA hashed and base64 encoded:
        //The first argument to the hash constructor is the actual value to be hased.  The 2nd is the
        //salt.  In this simple demo scenario, the username and the password are the same, but to clarify the
        //distinction, you would see this in practice:
        //new Sha256Hash( <password>, <cryptographically strong randomly generated salt> (not the username!) )
        String query = "insert into users values ('user1', '" + new Sha256Hash("user1", "user1").toBase64() + "' )";
        jdbcTemplate.execute(query);
        log.debug("Created user1.");

        //password is 'user2' SHA hashed and base64 encoded:
        query = "insert into users values ( 'user2', '"  + new Sha256Hash("user2", "user2").toBase64() + "' )";
        jdbcTemplate.execute(query);
        log.debug("Created user2.");

        query = "insert into roles values ( 'role1' )";
        jdbcTemplate.execute(query);
        log.debug("Created role1");

        query = "insert into roles values ( 'role2' )";
        jdbcTemplate.execute(query);
        log.debug("Created role2");

        query = "insert into roles_permissions values ( 'role1', 'permission1')";
        jdbcTemplate.execute(query);
        log.debug("Created permission 1 for role 1");

        query = "insert into roles_permissions values ( 'role1', 'permission2')";
        jdbcTemplate.execute(query);
        log.debug("Created permission 2 for role 1");

        query = "insert into roles_permissions values ( 'role2', 'permission1')";
        jdbcTemplate.execute(query);
        log.debug("Created permission 1 for role 2");

        query = "insert into user_roles values ( 'user1', 'role1' )";
        jdbcTemplate.execute(query);
        query = "insert into user_roles values ( 'user1', 'role2' )";
        jdbcTemplate.execute(query);
        log.debug("Assigned user1 roles role1 and role2");

        query = "insert into user_roles values ( 'user2', 'role2' )";
        jdbcTemplate.execute(query);
        log.debug("Assigned user2 role role2");
    }
}
