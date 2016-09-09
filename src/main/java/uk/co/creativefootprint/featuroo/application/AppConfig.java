package uk.co.creativefootprint.featuroo.application;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppConfig {

    @Value("${defaultTrafficFraction}")
    private double defaultTrafficFraction;

    public double getDefaultTrafficFraction() {
        return defaultTrafficFraction;
    }
}
