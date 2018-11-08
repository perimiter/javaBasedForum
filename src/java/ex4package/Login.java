package ex4package;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * login to website
 * @author  
 */
public class Login extends HttpServlet {

    
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ServletContext context = getServletContext();
        //web basic parameters created ones, and its done synchronized.
        synchronized (this) {
            //no user exist so far
            if (context.getAttribute("usersList") == null) {
                HashMap<String, UserData> usersList = new HashMap<>();
                context.setAttribute("usersList", usersList);
               long usersStamp = new Date().getTime();
                context.setAttribute("usersStamp", usersStamp);
            }
        }

        HttpSession session = request.getSession();
        String user = (String) session.getAttribute("user");
        //if user is logged in then redirect to user list
        if (user != null && !user.equals("")) {
            response.sendRedirect("ForumView.jsp");
        } else {
            request.getRequestDispatcher("LoginView.jsp").forward(request, response);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        ServletContext context = getServletContext();
        String newEmail = request.getParameter("email").trim();
        HashMap<String, UserData> usersList = (HashMap<String, UserData>) context.getAttribute("usersList");

        if (!isValidEmailAddress(newEmail)) {   //wrong input, error msg
            Message errorMsg = new Message("Login error, please try again");
            request.setAttribute("loginError", errorMsg);
            request.getRequestDispatcher("LoginView.jsp").forward(request, response);
        } else {   //good input
            synchronized (usersList) {
                UserData checkUser = usersList.get(newEmail);   //search for user in the DB, to check if user is already exist
                if (checkUser == null) {  //it is a new user, add it to DB
                    UserData newUser = new UserData(newEmail, Boolean.TRUE, "Hello");
                    usersList.put(newEmail, newUser);
                    long usersStamp =  new Date().getTime();
                    context.setAttribute("usersStamp", usersStamp);
                    session.setAttribute("user", newEmail);
                    response.sendRedirect("ForumView.jsp");
                } else {   //it is existing user, check if he is online or offline 
                    if (!checkUser.getConnectionState()) {     //it is existing user that offline, set it to online
                        usersList.get(newEmail).setConnectionState(Boolean.TRUE);
                        long usersStamp =  new Date().getTime();
                        context.setAttribute("usersStamp", usersStamp);
                        session.setAttribute("user", newEmail);
                        response.sendRedirect("ForumView.jsp");
                    } else {  //it is a new user that try to log in with existing name. show err msg
                        Message errorMsg = new Message("User exist, please try another email address");
                        request.setAttribute("loginError", errorMsg);
                        request.getRequestDispatcher("LoginView.jsp").forward(request, response);
                    }
                }
            }
        }
    }
    /**
     * checks if user inputed email is valid 
     * @param email user email
     * @return True if email is valid, else false
     */
    public static boolean isValidEmailAddress(String email) {
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }
}
