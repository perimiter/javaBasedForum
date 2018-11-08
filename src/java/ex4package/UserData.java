package ex4package;

/**
 * userBean holds user data
 * @author Amichai Tribitch and Michael Ziskin
 */
public class UserData {
    
    private String  email;
    private Boolean connectionState;
    private String status;

    /**
     * constructor
     * @param email user email
     * @param connectionState user online/offline
     * @param status text user shares with otherss
     */
    public UserData(String email, Boolean connectionState, String status) {
        this.email = email;
        this.connectionState = connectionState;
        this.status = status;
    }
    
    /**
     * return user email
     * @return user email 
     */
    public String getEmail() {
        return email;
    }
    /**
     * change user email
     * @param email new user email
     */
    public void setEmail(String email) {
        this.email = email;
    }
    
    /**
     * get user connection state
     * @return user offline/online
     */
    public Boolean getConnectionState() {
        return connectionState;
    }

    /**
     * change connection status
     * @param connectionState True is online,False is offline
     */
    public void setConnectionState(Boolean connectionState) {
        this.connectionState = connectionState;
    }

    /**
     * get the user text
     * @return user text
     */
    public String getStatus() {
        return status;
    }

    /**
     * change user saved status
     * @param status new user status
     */
    public void setStatus(String status) {
        this.status = status;
    }
}
