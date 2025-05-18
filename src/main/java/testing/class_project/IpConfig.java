package testing.class_project;

import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Manages the mapping between client IP addresses and database credentials.
 * <p>
 * Provides a centralized configuration for IP-based database access control.
 */

@Component
public class IpConfig {

    public static final String FELIPE_IP = "200.13.4.227";
    public static final String QUERY1_ALLOWED_USER = "felipe";

    public static final Map<String, String[]> IP_CREDENTIAL_MAP = Map.of(
            FELIPE_IP, new String[]{QUERY1_ALLOWED_USER, "mypass234"}
    );

    public static final String[] DEFAULT_CREDENTIALS =
            new String[]{"francisco", "mypass123"};

    public String[] getCredentialsForIp(String ip) {
        return IP_CREDENTIAL_MAP.getOrDefault(ip, DEFAULT_CREDENTIALS);
    }
}