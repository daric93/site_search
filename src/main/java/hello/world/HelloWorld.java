package hello.world;

/**
 * Created by darya on 06.12.15.
 */

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

// Extend HttpServlet class
public class HelloWorld extends HttpServlet {

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws ServletException, IOException {
        // Set response content type
        response.setContentType("text/html");

        PrintWriter out = response.getWriter();
        out.println("Hello world!!!");
        try {
            Query.fetchData(data -> {
                response.setContentType("text/html");
                if(!data.next()){
                    response.setStatus(404);
                    response.getWriter().print("Dataset is empty");
                    return;
                }
                data.beforeFirst();
                while (data.next()) {
                    out.println("<h1>" + "LastName: " + data.getString("LastName") + ", City: " + data.getString("City") + "</h1");
                }
            });
        } catch (SQLException | ClassNotFoundException e) {
            throw new ServletException(e);
        }
    }
}
