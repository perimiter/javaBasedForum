package ex4package;

import java.io.IOException;
import java.util.HashMap;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.PrintWriter;

/**
 * update user list that is shown on the page
 * @author Amichai Tribitch and Michael Ziskin
 */
public class UpdateView extends HttpServlet {

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
        if (request.getHeader("x-requested-with") != null) {//check if reqest is ajax
            ServletContext context = getServletContext();
            JsonObject jsnObject = new JsonObject();
            try (PrintWriter out = response.getWriter()) {
                //get timestamps to tell if update is needed
                long usersStamp = (long) context.getAttribute("usersStamp");
                String viewstampstring = (String) request.getParameter("timestamp");
                long viewStamp = Long.parseLong(viewstampstring);
                //update if user made a change
                if (usersStamp > viewStamp) {
                    HashMap<String, UserData> usersList = (HashMap<String, UserData>) context.getAttribute("usersList");
                    //prepare the json obj to be sent for ajax response
                    Gson gson = new Gson();
                    JsonElement jsonElement = gson.toJsonTree(usersList);
                    //used to tell if the list needs to be changed
                    jsnObject.addProperty("changed", Boolean.TRUE);
                    jsnObject.add("map", jsonElement);
                    jsnObject.addProperty("timestamp", usersStamp);
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    
                } else {    //no cahnge needed
                    jsnObject.addProperty("changed", Boolean.FALSE);
                }
                //send json
                out.write(jsnObject.toString());
                out.close();
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

}
