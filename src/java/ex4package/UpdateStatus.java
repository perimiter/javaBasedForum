package ex4package;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * update the status for a user
 *
 * @author 
 */
public class UpdateStatus extends HttpServlet {

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
        response.sendError(HttpServletResponse.SC_NOT_FOUND);   //accept post request anly
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
        if (request.getHeader("x-requested-with") != null) {//check if request is ajax
            ServletContext context = getServletContext();
            HttpSession session = request.getSession();
            HashMap<String, UserData> usersList = (HashMap<String, UserData>) context.getAttribute("usersList");
            String usermail = (String) session.getAttribute("user");
            synchronized (usersList) {
                UserData usr = usersList.get(usermail);
                if (usr != null) {
                    usr.setStatus(escapeHTML((request.getParameter("stat"))));  //escaping user input befor seting it
                    long usersStamp = new Date().getTime();
                    context.setAttribute("usersStamp", usersStamp);
                }
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    /**
     * input sanitization
     * @param s user original input
     * @return sanitized input
     */
    public static String escapeHTML(String s) {
        StringBuilder out = new StringBuilder(Math.max(16, s.length()));
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c > 127 || c == '"' || c == '<' || c == '>' || c == '&') {
                out.append("&#");
                out.append((int) c);
                out.append(';');
            } else {
                out.append(c);
            }
        }
        return out.toString();
    }
}
