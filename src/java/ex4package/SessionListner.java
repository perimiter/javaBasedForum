package ex4package;

import java.util.Date;
import java.util.HashMap;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * handle session destruction event
 * @author Michael Ziskin
 */
public class SessionListner implements HttpSessionListener {

    /**
     * sessionCreated
     * @param se session event
     */
    @Override
    public void sessionCreated(HttpSessionEvent se) {
    }

    /**
     * set session's user offline in the DB before then destroy the session 
     * sessionDestroyed
     * @param se session event
     */
    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        ServletContext context = session.getServletContext();
        String usermail = (String) session.getAttribute("user");
        HashMap<String, UserData> usersList = (HashMap<String, UserData>) context.getAttribute("usersList");

        synchronized (usersList) {
            usersList.get(usermail).setConnectionState(Boolean.FALSE);
            long usersStamp = new Date().getTime();
            context.setAttribute("usersStamp", usersStamp);
        }
    }
}
