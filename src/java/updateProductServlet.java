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

@WebServlet("/updateProductServlet")
public class updateProductServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String DB_URL = "jdbc:mysql://localhost:3306/ShopManagement?useSSL=false";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "silver";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int productId = Integer.parseInt(request.getParameter("id"));
        String productName = request.getParameter("product_name");
        boolean availability = request.getParameter("availability") != null;
        double price = Double.parseDouble(request.getParameter("price"));

        String sql = "UPDATE products SET product_name = ?, availability = ?, price = ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, productName);
            ps.setBoolean(2, availability);
            ps.setDouble(3, price);
            ps.setInt(4, productId);

            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated > 0) {
                response.sendRedirect("ViewProductsServlet");
            } else {
                response.getWriter().println("<h2>Failed to update product</h2>");
            }
        } catch (SQLException e) {
            response.getWriter().println("<h2>Database Error: " + e.getMessage() + "</h2>");
        }
    }
}
