package rgz;

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
 * Created by darya on 11.12.15.
 */
public class ImageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        try {
            QueryToDatabase.fetchImage(Integer.parseInt(id), data -> {
                if (!data.next()) {
                    resp.setContentType("text/html");
                    resp.setStatus(404);
                    resp.getWriter().print("Error. Try to fetch image.");
                    return;
                }
                data.beforeFirst();
                while (data.next()) {
                    resp.setContentType("image/jpeg");
                    InputStream image = data.getBinaryStream("Image");
                    ServletOutputStream respOutputStream = resp.getOutputStream();
                    copy(image, respOutputStream);
                }
            });
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
