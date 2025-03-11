// To save as "ebookshop\WEB-INF\classes\QueryServlet.java".
import java.io.*;
import java.sql.*;
import jakarta.servlet.*;            // Tomcat 10 (Jakarta EE 9)
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
//import javax.servlet.*;            // Tomcat 9 (Java EE 8 / Jakarta EE 8)
//import javax.servlet.http.*;
//import javax.servlet.annotation.*;

@WebServlet("/eshopquery")
public class EshopQueryServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        out.println("<!DOCTYPE html>");
        out.println("<html lang='en'>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("<title>Query Response</title>");
        out.println("<style>");
        out.println("body { font-family: Arial, sans-serif; margin: 0; padding: 20px; background-color: #f4f4f4; }");
        out.println("h2, h3 { color: #333; }");
        out.println("table { width: 100%; border-collapse: collapse; margin-bottom: 20px; }");
        out.println("th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }");
        out.println("th { background-color: #f2f2f2; }");
        out.println("input[type='text'] { padding: 5px; width: 50%; }");
        out.println("input[type='submit'], input[type='button'] { margin-top: 10px; padding: 10px 15px; }");
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");
        
        try (
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/ebookshop?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
                    "myuser", "xxxx"); 

            Statement stmt = conn.createStatement();
        ) {
            String[] authors = request.getParameterValues("author");
            String priceStr = request.getParameter("price");
            double price = priceStr != null ? Double.parseDouble(priceStr) : Double.MAX_VALUE;

            if (authors == null) {
                out.println("<h2>No author selected. Please go back to select author(s)</h2>");
                out.println("</body></html>");
                return;
            }

            StringBuilder sqlStr = new StringBuilder("SELECT * FROM books WHERE author IN (");
            for (int i = 0; i < authors.length; ++i) {
                sqlStr.append("'").append(authors[i]).append(i < authors.length - 1 ? "', " : "'");
            }
            sqlStr.append(") AND price < ").append(price).append(" AND qty > 0 ORDER BY author ASC, title ASC");

            out.println("<h3>Thank you for your query.</h3>");
            out.println("<p>Your SQL statement is: " + sqlStr + "</p>"); // Echo for debugging
            
            ResultSet rset = stmt.executeQuery(sqlStr.toString());

            if (!rset.isBeforeFirst()) {
                out.println("<h2>No books found for the selected authors under the price of $" + price + "0. Please select again.</h2>");
            } else {
                out.println("<form method='get' action='eshoporder'>");
                out.println("<table>");
                out.println("<tr><th>Select</th><th>Author</th><th>Title</th><th>Price</th></tr>");

                while (rset.next()) {
                    out.println("<tr>");
                    out.println("<td><input type='checkbox' name='id' value='" + rset.getString("id") + "' /></td>");
                    out.println("<td>" + rset.getString("author") + "</td>");
                    out.println("<td>" + rset.getString("title") + "</td>");
                    out.println("<td>$" + rset.getString("price") + "</td>");
                    out.println("</tr>");
                }
                out.println("</table>");

                out.println("<p><label for='cust_name'>Enter your Name: </label><input type='text' id='cust_name' name='cust_name' required /></p>");
                out.println("<p><label for='cust_email'>Enter your Email: </label><input type='text' id='cust_email' name='cust_email' required /></p>");
                out.println("<p><label for='cust_phone'>Enter your Phone Number: </label><input type='text' id='cust_phone' name='cust_phone' required /></p>");

                out.println("<p><input type='submit' value='ORDER' />");
                out.println("</form>");
            }
        } catch (SQLException ex) {
            out.println("<p>Error: " + ex.getMessage() + "</p>");
            out.println("<p>Check Tomcat console for details.</p>");
            ex.printStackTrace();
        }

        out.println("</body></html>");
        out.close();
    }
}
