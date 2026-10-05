package org.example.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

@WebServlet("/display")
public class connectdb extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req,
                         HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("text/html");

        PrintWriter out = resp.getWriter();

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/login_schema",
                    "root",
                    "manager"
            );

            try (Statement st = con.createStatement()) {

                ResultSet rs = st.executeQuery(
                        "select * from students"
                );

                out.println("<html>");
                out.println("<body>");

                out.println("<table border='1'>");

                out.println("<tr>");
                out.println("<th>id</th>");
                out.println("<th>name</th>");
                out.println("<th>marks</th>");
                out.println("</tr>");

                while (rs.next()) {

                    out.println("<tr>");

                    out.println(
                            "<td>" + rs.getInt("id") + "</td>"
                    );

                    out.println(
                            "<td>" + rs.getString("name") + "</td>"
                    );

                    out.println(
                            "<td>" + rs.getInt("marks") + "</td>"
                    );

                    out.println("</tr>");
                }

                out.println("</table>");

                out.println("</body>");
                out.println("</html>");

                rs.close();
                st.close();
            }
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}