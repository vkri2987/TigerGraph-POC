package com.tiger.tigerGraph.Config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class DbConfig {

    @Value("${ipAddress}")
    private String ipAddress;

    @Value("${port}")
    private Integer port;

    @Value("${driver}")
    private String driver;

    @Value("${username}")
    private String username;

    @Value("${password}")
    private String password;

    @Value("${graph}")
    private String graph;

    @Value("${baseUrl}")
    private String baseUrl;

    public HikariDataSource getDataSource(){
        HikariConfig config = new HikariConfig();
        config.setDriverClassName(driver);
        config.setUsername(username);
        config.setPassword(password);
        config.addDataSourceProperty("graph", graph);
        config.addDataSourceProperty("filename", "f");
        config.addDataSourceProperty("sep", ",");
        config.addDataSourceProperty("eol", ";");

        StringBuilder sb = new StringBuilder();
        sb.append(baseUrl).append(ipAddress).append(":").append(port);
        config.setJdbcUrl(sb.toString());
        HikariDataSource ds = new HikariDataSource(config);
        return ds;

    }

}
