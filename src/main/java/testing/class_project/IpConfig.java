package testing.class_project;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Manages the mapping between client IP addresses and database credentials.
 * <p>
 * Provides a centralized configuration for IP-based database access control.
 */

@Component
public class IpConfig {

    public record IpData(String name, List<String> queries, String password) {}

    public static final String FELIPE_IP = "200.13.4.227";
    public static final String SEBASTIAN_IP = "200.13.4.196";
    public static final String DIEGO_IP = "200.13.4.238";
    public static final String FRANCISCO_IP = "200.13.4.240";

    public static final String FELIPE = "felipe";
    public static final String SEBASTIAN = "sebastian";
    public static final String DIEGO = "diego";
    public static final String FRANCISCO = "francisco";
    public static final String JUAN = "juan";

    public static final String QUERY_1 = "query1";
    public static final String QUERY_2 = "query2";
    public static final String QUERY_3 = "query3";
    public static final String QUERY_4_1 = "query41";
    public static final String QUERY_4_2 = "query42";
    public static final String QUERY_4_3 = "query43";

    public static final Map<String, IpData> IP_CREDENTIAL_MAP = Map.of(
            FELIPE_IP, new IpData(FELIPE, List.of(QUERY_1), "mypass234"),
            SEBASTIAN_IP, new IpData(SEBASTIAN, List.of(QUERY_2), "mypass432"),
            DIEGO_IP, new IpData(DIEGO, List.of(QUERY_3), "mypass321"),
            FRANCISCO_IP, new IpData(FRANCISCO, List.of(QUERY_4_1, QUERY_4_2, QUERY_4_3), "mypass123")
    );

    public static final IpData DEFAULT_CREDENTIALS =
            new IpData(FELIPE, List.of(QUERY_1), "mypass234");

    public IpData getCredentialsForIp(String ip) {
        return IP_CREDENTIAL_MAP.getOrDefault(ip, DEFAULT_CREDENTIALS);
    }
}