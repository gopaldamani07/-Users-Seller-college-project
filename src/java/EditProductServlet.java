import java.io.IOException;
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

@WebServlet("/EditProductServlet")
public class EditProductServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String DB_URL = "jdbc:mysql://localhost:3306/ShopManagement?useSSL=false";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "silver";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int productId = Integer.parseInt(request.getParameter("id"));

        String sql = "SELECT * FROM products WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, productId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    request.setAttribute("id", productId);
                    request.setAttribute("product_name", rs.getString("product_name"));
                    request.setAttribute("availability", rs.getBoolean("availability"));
                    request.setAttribute("price", rs.getDouble("price"));

                    request.getRequestDispatcher("editProductForm.jsp").forward(request, response);
                } else {
                    response.getWriter().println("<h2>Product not found</h2>");
                }
            }
        } catch (SQLException e) {
            response.getWriter().println("<h2>Database Error: " + e.getMessage() + "</h2>");
        }
    }
}
