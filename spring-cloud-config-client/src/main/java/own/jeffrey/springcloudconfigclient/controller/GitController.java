package own.jeffrey.springcloudconfigclient.controller;

import org.springframework.beans.factory.annotation.Autowired;
import own.jeffrey.springcloudconfigclient.component.ConfigurationProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GitController {
    @Autowired
    ConfigurationProperty configurationProperty;

    @GetMapping(value = "/whoami")
    public ConfigurationProperty.User whoami() {
        return configurationProperty.getUser();
    }
}
