// To save as "ebookshop\WEB-INF\classes\QueryServlet.java".
import java.io.*;
import java.sql.*;
import jakarta.servlet.*;            // Tomcat 10 (Jakarta EE 9)
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
//import javax.servlet.*;            // Tomcat 9 (Java EE 8 / Jakarta EE 8)
//import javax.servlet.http.*;
//import javax.servlet.annotation.*;

@WebServlet("/eshoporder")
public class EshopOrderServlet extends HttpServlet {

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
        out.println("<title>Order Confirmation</title>");
        out.println("<style>");
        out.println("body { font-family: Arial, sans-serif; margin: 0; padding: 20px; background-color: #f4f4f4; }");
        out.println("h2, h3 { color: #333; }");
        out.println("p { margin: 5px 0; }");
        out.println("strong { color: #e74c3c; }");
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");
        
        try (
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/ebookshop?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
                "myuser", "xxxx");

            Statement stmt = conn.createStatement();
        ) {
            String[] ids = request.getParameterValues("id");
            String custName = request.getParameter("cust_name");
            String custPhone = request.getParameter("cust_phone");
            String custEmail = request.getParameter("cust_email");

            if (ids != null && ids.length > 0) {
                for (String id : ids) {
                    // Update the quantity of the book in stock
                    String updateSql = "UPDATE books SET qty = qty - 1 WHERE id = " + id;
                    int count = stmt.executeUpdate(updateSql);
                    out.println("<p>" + count + " record(s) updated for book ID: " + id + ".</p>");

                    // Create a transaction record
                    String insertSql = "INSERT INTO order_records (id, qty_ordered, cust_name, cust_phone, cust_email) VALUES ("
                            + id + ", 1, '" + custName + "', '" + custPhone + "', '" + custEmail + "')";
                    count = stmt.executeUpdate(insertSql);
                    out.println("<p>" + count + " record(s) inserted into order records for book ID: " + id + ".</p>");

                    out.println("<h3>Your order for book ID " + id + " has been confirmed.</h3>");
                }
                out.println("<h3>Thank you for your order!</h3>");
            } else {
                out.println("<h3><strong>Please go back and select a book...</strong></h3>");
            }
        } catch (SQLException ex) {
            out.println("<p><strong>Error processing your order:</strong> " + ex.getMessage() + "</p>");
            out.println("<p>Please check Tomcat console for details.</p>");
            ex.printStackTrace();
        }

        out.println("</body></html>");
        out.close();
    }
}
