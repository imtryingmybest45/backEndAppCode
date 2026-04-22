package wonderful.com.example.demo;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.sql.*;
import java.util.*;

@RestController

public class WatchController {

    //@CrossOrigin(origins="http://localhost:3000")

    @CrossOrigin(origins = {"http://localhost:3000",
            "https://green-smoke-0fa35931e.6.azurestaticapps.net/",
            "https://www.aprilshorrorcorner.com",
            "https://aprilshorrorcorner.com",
            "https://zealous-desert-09313150f.6.azurestaticapps.net/",
            "https://help.aprilshorrorcorner.com"})

    @GetMapping("/watchEndpoint")

    public Map<String, Object> getData() throws IOException {

        //MyRequestDTO dto = new MyRequestDTO();

        // AWS RDS Endpoint from the AWS Console
        String endpoint = "lizard.c6de8wseq94u.us-east-1.rds.amazonaws.com";
        String port = "3306"; // Default for MySQL
        String dbName = "mysql";

        // JDBC URL format: jdbc:<engine>://<endpoint>:<port>/<dbName>
        String url = "jdbc:mysql://" + endpoint + ":" + port + "/" + dbName;
        String username = "tomthelizard";
        String password = "lizarddd";
        String name;
        String poster;
        String blurb;

        int year;
        int movieId;
        Map<String, Object> response = new HashMap<>();

        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            if (conn != null) {
                System.out.println("Connected to AWS RDS successfully!");
                Statement stmt = conn.createStatement();
                stmt.execute("USE movies");
                ResultSet rs = stmt.executeQuery("SELECT * FROM watchList ORDER BY name");
                while (rs.next()) {

                    HashMap<String, String> details = new HashMap<>();
                    // Retrieve values by column name
                    name = rs.getString("name");
                    poster = rs.getString("poster");
                    blurb = rs.getString("blurb");
                    year = rs.getInt("year");
                    movieId = rs.getInt("movieID");

                    //System.out.println(name);

                    details.put("name",name);
                    details.put("year",Integer.toString(year));
                    details.put("poster",poster);
                    details.put("blurb",blurb);
                    details.put("movieId",Integer.toString(movieId));

                    String movieName = name;
                    response.put(movieName, details);

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return response;

    }
}
