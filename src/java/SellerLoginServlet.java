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

@WebServlet("/SellerLoginServlet")
public class SellerLoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Database connection details
    private static final String DB_URL = "jdbc:mysql://localhost:3306/ShopManagement?useSSL=false"; // Change to your database name
    private static final String DB_USER = "root"; // Change to your database username
    private static final String DB_PASSWORD = "silver"; // Change to your database password

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Load the MySQL JDBC driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Load the driver
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<h1>Database Driver Error</h1>");
            out.println("<p>MySQL JDBC Driver not found.</p>");
            return;
        }

        // Initialize database connection
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT * FROM sellers WHERE username = ? AND password = ?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, username);
                ps.setString(2, password);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    // Successful login
                    response.sendRedirect("sellerDashboard.html");
                } else {
                    // Invalid credentials
                    response.setContentType("text/html");
                    PrintWriter out = response.getWriter();
                    out.println("<h1>Login Failed!</h1>");
                    out.println("<p>Invalid username or password.</p>");
                    out.println("<a href='seller.html'>Try Again</a>");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<h1>Database Connection Error</h1>");
            out.println("<p>" + e.getMessage() + "</p>");
        }
    }
}

