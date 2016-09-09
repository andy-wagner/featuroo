package uk.co.creativefootprint.featuroo.application;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DbConfig {

    @Value("${db.driver}")
    private String driver;

    @Value("${db.connection}")
    private String connection;

    @Value("${db.user}")
    private String user;

    @Value("${db.password}")
    private String password;

    public String getDriver() {
        return driver;
    }
    public String getConnection() {
        return connection;
    }

    public String getUser() {
        return user;
    }
    public String getPassword() {
        return password;
    }
}
