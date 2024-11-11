import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/AddProductServlet")
public class AddProductServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Database connection details
    private static final String DB_URL = "jdbc:mysql://localhost:3306/ShopManagement?useSSL=false";
    private static final String DB_USER = "root";  // Replace with your MySQL username
    private static final String DB_PASSWORD = "silver";  // Replace with your MySQL password

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String productName = request.getParameter("product_name");
        boolean availability = request.getParameter("availability") != null; // Checkbox is checked if not null
        double price = Double.parseDouble(request.getParameter("price"));

        // SQL to insert a new product
        String sql = "INSERT INTO products (product_name, availability, price) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, productName);
            ps.setBoolean(2, availability);
            ps.setDouble(3, price);

            int rowsInserted = ps.executeUpdate();
            if (rowsInserted > 0) {
                // Redirect to view products page if insertion is successful
                response.sendRedirect("ViewProductsServlet");
            } else {
                response.getWriter().println("<h2>Failed to add product</h2>");
            }
        } catch (SQLException e) {
            response.getWriter().println("<h2>Database Error: " + e.getMessage() + "</h2>");
        }
    }
}
