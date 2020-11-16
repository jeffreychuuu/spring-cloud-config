package own.jeffrey.springcloudconfigclient.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import own.jeffrey.springcloudconfigclient.component.ConfigurationProperty;

@RestController
public class GitController {

    @Autowired
    ConfigurationProperty configurationProperty;

    @GetMapping(value = "/whoami")
    ConfigurationProperty.User whoami() {
        return configurationProperty.getUser();
    }
}
