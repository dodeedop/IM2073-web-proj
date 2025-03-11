import java.io.*;
import java.sql.*;
import jakarta.servlet.*;            // Tomcat 10 (Jakarta EE 9)
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
//import javax.servlet.*;            // Tomcat 9 (Java EE 8 / Jakarta EE 8)
//import javax.servlet.http.*;
//import javax.servlet.annotation.*;

@WebServlet("/eshopdisplay")
public class EShopDisplayServlet extends HttpServlet {
   
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        out.println("<html>");
        out.println("<head><title>Yet Another e-Bookshop</title></head>");
        out.println("<body>");
        out.println("<h2>Yet Another e-Bookshop</h2>");
        out.println("<form method='get' action='eshopquery'>");
        out.println("Choose an author:<br /><br />");
        
        try (
            // Step 1: Allocate a database 'Connection' object
            Connection conn = DriverManager.getConnection(
                  "jdbc:mysql://localhost:3306/ebookshop?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
                  "myuser", "xxxx");   // For MySQL
                  // The format is: "jdbc:mysql://hostname:port/databaseName", "username", "password"
   
            // Step 2: Allocate a 'Statement' object in the Connection
            Statement stmt = conn.createStatement();
         ) {
            // Step 3: Execute a SQL SELECT query
            String sqlStr = "SELECT DISTINCT author FROM books";
            ResultSet rs = stmt.executeQuery(sqlStr);
             
            // Step 4: Process the ResultSet by scrolling the cursor forward via next().
            while (rs.next()) {
                out.println("<input type='checkbox' name='author' value='" + rs.getString("author") + "' />" + rs.getString("author"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        out.println("<br /><br />");
        out.println("Choose a price range:");
        out.println("<input type='radio' name='price' value='50' />less than $50");
        out.println("<input type='radio' name='price' value='100' />less than $100");
        out.println("<br /><br />");
        out.println("<input type='submit' value='Search' />");
        out.println("<input type='reset' value='Clear' />");
        out.println("</form>");
        out.println("</body></html>");
        out.close();
    }
}
