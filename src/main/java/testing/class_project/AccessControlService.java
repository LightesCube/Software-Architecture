package testing.class_project;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Controls query execution permissions based on client IP
 */
@Service
public class AccessControlService {

    private final HttpServletRequest request;
    private final IpConfig ipConfig;

    @Autowired
    public AccessControlService(HttpServletRequest request, IpConfig ipConfig) {
        this.request = request;
        this.ipConfig = ipConfig;
    }

    /**
     * Checks if current client can execute query1
     * @return true if client IP is mapped to credentials authorized for query1
     */
    public boolean canExecuteQuery1() {
        String currentUser = ipConfig.getCredentialsForIp(request.getRemoteAddr())[0];
        return currentUser.equals(IpConfig.QUERY1_ALLOWED_USER);
    }
}