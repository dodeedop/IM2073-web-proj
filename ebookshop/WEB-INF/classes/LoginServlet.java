/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author adver
 */
@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
   
    private static final long serialVersionUID = 1L;
    private String dburl="jdbc:mysql://localhost:3306/ebookshop";
    private String dbuname="myuser";
    private String dbpassword="xxxx";
    private String dbdriver="com.mysql.jdbc.Driver";
    
    public Connection getConnection(){
        Connection con=null;
        try {
            con=DriverManager.getConnection(dburl, dbuname, dbpassword);
        } catch (SQLException ex) {
            Logger.getLogger(RegisterDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return con;
    }
    
    public void loadDriver(String dbDriver){
        try {
            Class.forName(dbDriver);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(RegisterDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
   
    // From login.jsp, as a post method only the credentials are passed
    // Hence the parameters should match both in jsp and servlet and 
      // then only values are retrieved properly
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // We can able to get the form data by means of the below ways. 
        // Form arguments should be matched and then only they are recognised
        // login.jsp component names should match and then only
          // by using request.getParameter, it is matched
        loadDriver(dbdriver);
        Connection con=getConnection();
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select email, password from member");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            boolean SuccessfulLogin=false;
            while(rs.next()){
                // Here the business validations goes. As a sample, 
                // we can check against a hardcoded value or pass 
                // the values into a database which can be available in local/remote  db
                // For easier way, let us check against a hardcoded value
                String checkemail=rs.getString("email");
                String checkpassword=rs.getString("password");
                if (email != null && email.equalsIgnoreCase(checkemail) && password != null && password.equalsIgnoreCase(checkpassword)) {
                    SuccessfulLogin=true;
                }
            }
            if(SuccessfulLogin=true){
                /* We can redirect the page to a welcome page
                    Need to pass the values in session in order to carry forward that one to next pages*/
                    HttpSession httpSession = request.getSession();
                    // By setting the variable in session, it can be forwarded
                    httpSession.setAttribute("email", email);
                    request.getRequestDispatcher("welcome.jsp").forward(request, response); //end redirect
            }
        } catch (SQLException ex) {
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}