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
        
        out.println("<html lang='en'>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("<title>Yet Another e-Bookshop</title>");
        out.println("<style>");
        out.println("body { font-family: Arial, sans-serif; margin: 0; padding: 20px; background-color: #f4f4f4; }");
        out.println("h2 { color: #333; }");
        out.println("form { background: #fff; padding: 20px; border-radius: 5px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); }");
        out.println("input[type='checkbox'], input[type='radio'] { margin-right: 10px; }");
        out.println("label { display: block; margin-bottom: 5px; }");
        out.println("input[type='submit'], input[type='reset'] { margin-top: 10px; padding: 10px 15px; }");
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h2>Welcome to Yet Another e-Bookshop</h2>");
        out.println("<form method='get' action='eshopquery'>");
        out.println("<h3>Choose an author:</h3>");
        
        try (
            Connection conn = DriverManager.getConnection(
                  "jdbc:mysql://localhost:3306/ebookshop?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
                  "myuser", "xxxx");

            Statement stmt = conn.createStatement();
        ) {
            String sqlStr = "SELECT DISTINCT author FROM books";
            ResultSet rs = stmt.executeQuery(sqlStr);
            
            while (rs.next()) {
                    out.println("<label><input type='checkbox' name='author' value='" + rs.getString("author") + "' />"  + rs.getString("author") + "</label>");
                }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        out.println("<h3>Choose a price range:</h3>");
        out.println("<label><input type='radio' name='price' value='50' required />less than $50</label>");
        out.println("<label><input type='radio' name='price' value='100' required />less than $100</label>");
        out.println("<input type='submit' value='Search' />");
        out.println("<input type='reset' value='Clear' />");
        out.println("</form>");
        out.println("</body></html>");
        out.close();
    }
}
