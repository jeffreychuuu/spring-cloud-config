package own.jeffrey.springcloudconfigclient.component;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


@RefreshScope
@Configuration
@ConfigurationProperties(prefix = "application")
public class ConfigurationProperty {
    User user;

    public static class User{
        String name;
        String role;
        String team;
        ArrayList<String> group = new ArrayList<>();
        Map<String, ArrayList> departmentRoles = new HashMap<>();

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getTeam() {
            return team;
        }

        public void setTeam(String team) {
            this.team = team;
        }

        public ArrayList<String> getGroup() {
            return group;
        }

        public void setGroup(ArrayList<String> group) {
            this.group = group;
        }

        public Map<String, ArrayList> getDepartmentRoles() {
            return departmentRoles;
        }

        public void setDepartmentRoles(Map<String, ArrayList> departmentRoles) {
            this.departmentRoles = departmentRoles;
        }
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
