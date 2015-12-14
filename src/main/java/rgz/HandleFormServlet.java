package rgz;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Map;

/**
 * Created by darya on 11.12.15.
 */
public class HandleFormServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String[]> parameterMap = req.getParameterMap();
        try {
            QueryToDatabase.fetchData(parameterMap, data -> {
                PrintWriter respWriter = resp.getWriter();
                if (!data.next()) {
                    resp.setContentType("text/html");
                    respWriter.println("Results not found");
                    return;
                }
                resp.setContentType("text/html");
                data.beforeFirst();
                int i = 0;
                while (data.next()) {
                    int id = data.getInt("ID");
                    respWriter.print("<div id=\"img\" style=\"width:200px;height:275px; position:absolute; left:" + (i % 5 * 150 + 50 * (i % 5) + 65) + "px; top:" + (i / 5 * 350) + "px \">" +
                            "<p><img src = \"/fetchImg?id=" + id + "\"" +
                            " alt = \"id: " + id + "\"" +
                            "style=\"width:110px;height:225px\"/></p>" +
                            "<p>" + data.getString("Producer") + " " + data.getString("Model") + "<br>" +
                            "Operating system: " + data.getString("OperatingSystem") + "<br>" +
                            "Number of cores: " + data.getString("NumberOfCores") + "<br>" +
                            "Price: " + data.getString("Price") +
                            "</p>" +
                            "</div>");
                    i++;
                }
            });
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
