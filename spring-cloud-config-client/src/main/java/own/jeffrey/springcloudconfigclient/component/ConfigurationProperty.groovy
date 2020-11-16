package own.jeffrey.springcloudconfigclient.component

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.cloud.context.config.annotation.RefreshScope
import org.springframework.context.annotation.Configuration

@RefreshScope
@Configuration
@ConfigurationProperties(prefix = "application")
class ConfigurationProperty {
    User user;

    static class User{
        String name;
        String role;
        String team;
        List<String> group = [];
        Map<String, List> departmentRoles = [:];
    }

    void setUser(User user) {
        this.user = user
    }

    User getUser() {
        return user
    }
}
