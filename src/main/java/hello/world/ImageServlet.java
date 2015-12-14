package hello.world;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

import static com.google.common.io.ByteStreams.copy;

/**
 * Created by darya on 09.12.15.
 */
public class ImageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        try {
            ImageQuery.fetchData(id, data -> {
                if (!data.next()) {
                    resp.setContentType("text/html");
                    resp.setStatus(404);
                    resp.getWriter().println("Not found");
                    return;
                }
                resp.setContentType("image/jpeg");
                InputStream image = data.getBinaryStream("Image");
                try (ServletOutputStream respOutputStream = resp.getOutputStream()) {
                    copy(image, respOutputStream);
                }
            });
        } catch (ClassNotFoundException | SQLException e) {
            throw new ServletException(e);
        }
    }
}
