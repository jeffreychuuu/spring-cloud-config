package own.jeffrey.springcloudconfigclient.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import own.jeffrey.springcloudconfigclient.component.ConfigurationProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class GitController {
    @Autowired
    ConfigurationProperty configurationProperty;

    @Value("${application.user.name}")
    private String name;

    @GetMapping(value = "/whoami")
    public ConfigurationProperty.User whoami() {
        return configurationProperty.getUser();
    }

    @GetMapping(value = "/name")
    public String getName() {
        return name;
    }
}
