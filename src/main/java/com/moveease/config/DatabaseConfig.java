package com.moveease.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {

    @Value("${DATABASE_URL}")
    private String databaseUrl;

    @Bean
    public DataSource dataSource() {
        // Render gives "postgres://" but Spring Boot needs "postgresql://"
        String url = databaseUrl.replace("postgres://", "postgresql://");

        try {
            String withoutPrefix = url.replace("postgresql://", "");
            String userInfo = withoutPrefix.substring(0, withoutPrefix.indexOf("@"));
            String hostAndDb = withoutPrefix.substring(withoutPrefix.indexOf("@") + 1);

            String username = userInfo.substring(0, userInfo.indexOf(":"));
            String password = userInfo.substring(userInfo.indexOf(":") + 1);

            String jdbcUrl = "jdbc:postgresql://" + hostAndDb + "?sslmode=require";

            return DataSourceBuilder.create()
                    .url(jdbcUrl)
                    .username(username)
                    .password(password)
                    .driverClassName("org.postgresql.Driver")
                    .build();
        } catch (Exception e) {
            return DataSourceBuilder.create()
                    .url("jdbc:" + url + "?sslmode=require")
                    .driverClassName("org.postgresql.Driver")
                    .build();
        }
    }
}