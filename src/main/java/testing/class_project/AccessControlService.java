package testing.class_project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;

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
        return canExecuteQuery(IpConfig.FELIPE, IpConfig.QUERY_1);
    }

    public boolean canExecuteQuery2() {
        return canExecuteQuery(IpConfig.SEBASTIAN, IpConfig.QUERY_2);
    }

    public boolean canExecuteQuery3() {
        return canExecuteQuery(IpConfig.DIEGO, IpConfig.QUERY_3);
    }

    public boolean canExecuteQuery41() {
        return canExecuteQuery(IpConfig.FRANCISCO, IpConfig.QUERY_4_1);
    }

    public boolean canExecuteQuery42() {
        return canExecuteQuery(IpConfig.FRANCISCO, IpConfig.QUERY_4_2);
    }

    public boolean canExecuteQuery43() {
        return canExecuteQuery(IpConfig.FRANCISCO, IpConfig.QUERY_4_3);
    }

    public boolean canExecuteQuery51() {
        return canExecuteQuery(IpConfig.JUAN, IpConfig.QUERY_5_1);
    }
    
    public boolean canExecuteQuery52() {
        return canExecuteQuery(IpConfig.JUAN, IpConfig.QUERY_5_2);
    }

    public boolean canExecuteQuery53() {
        return canExecuteQuery(IpConfig.JUAN, IpConfig.QUERY_5_3);
    }
	
    public boolean canExecuteQuery54() {
        return canExecuteQuery(IpConfig.JUAN, IpConfig.QUERY_5_4);
    }
    public boolean canExecuteQuery(String allowedUser, String requiredQuery) {
        var userData = ipConfig.getCredentialsForIp(request.getRemoteAddr());
        var userName = userData.name();
        var userQueries = userData.queries();
	System.out.println("Remote IP: " + request.getRemoteAddr());
        return userName.equals(allowedUser) && userQueries.contains(requiredQuery);
    }
}
