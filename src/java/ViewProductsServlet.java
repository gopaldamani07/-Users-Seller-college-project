import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/ViewProductsServlet")
public class ViewProductsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Database connection details
    private static final String DB_URL = "jdbc:mysql://localhost:3306/ShopManagement?useSSL=false";
    private static final String DB_USER = "root";  // Replace with your MySQL username
    private static final String DB_PASSWORD = "silver";  // Replace with your MySQL password

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Set the response content type to HTML
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Load the MySQL JDBC driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish database connection
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                String sql = "SELECT * FROM products";
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ResultSet rs = ps.executeQuery();

                    // HTML to display products in a table
                    out.println("<html>");
                    out.println("<head><title>View Products</title></head>");
                    out.println("<body>");
                    out.println("<h1>Product List</h1>");
                    out.println("<table border='1' cellspacing='0' cellpadding='5'>");
                    out.println("<tr><th>ID</th><th>Product Name</th><th>Availability</th><th>Price</th><th>Actions</th></tr>");

                    // Iterate through the result set and display each product
                    while (rs.next()) {
                        int id = rs.getInt("id");
                        String productName = rs.getString("product_name");
                        boolean availability = rs.getBoolean("availability");
                        double price = rs.getDouble("price");

                        out.println("<tr>");
                        out.println("<td>" + id + "</td>");
                        out.println("<td>" + productName + "</td>");
                        out.println("<td>" + (availability ? "Available" : "Out of Stock") + "</td>");
                        out.println("<td>" + price + "</td>");
                        // Add Edit and Delete buttons for each product
                        out.println("<td>");
                        out.println("<a href='EditProductServlet?id=" + id + "'>Edit</a> | ");
                        out.println("<a href='DeleteProductServlet?id=" + id + "'>Delete</a>");
                        out.println("</td>");
                        out.println("</tr>");
                    }

                    out.println("</table>");
                    out.println("<br><br>");
                    out.println("<button onclick=\"location.href='sellerDashboard.html'\">Back to Dashboard</button>");
                    out.println("</body>");
                    out.println("</html>");
                }
            } catch (SQLException e) {
                out.println("<h2>Database Error: " + e.getMessage() + "</h2>");
            }
        } catch (ClassNotFoundException e) {
            out.println("<h2>Database Driver Not Found: " + e.getMessage() + "</h2>");
        }
    }
}
