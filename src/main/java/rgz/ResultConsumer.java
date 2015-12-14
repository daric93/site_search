package rgz;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by darya on 11.12.15.
 */
public interface ResultConsumer {
    void accept(ResultSet result) throws SQLException, IOException;
}
