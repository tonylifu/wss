package com.webstartrek.wss.dao;

import lombok.Getter;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.sql.DataSource;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named
@SessionScoped
public class TestConnectionBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Resource(lookup = "java:/jdbc/wss-db")
    private DataSource dataSource;

    @Getter
    private String connectionStatus;

    private static final Logger logger = Logger.getLogger(TestConnectionBean.class.getName());

    public String testConnection() {
        try (Connection connection = dataSource.getConnection()) {
            connectionStatus = "Connection successful!";
        } catch (SQLException e) {
            connectionStatus = "Connection failed: " + e.getMessage();
            logger.log(Level.SEVERE, "Database connection error", e);
        }
        return connectionStatus;
    }

}
