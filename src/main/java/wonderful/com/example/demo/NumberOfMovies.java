package wonderful.com.example.demo;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.sql.*;
import java.util.*;

@RestController

public class NumberOfMovies {

    //@CrossOrigin(origins="http://localhost:3000")

    @CrossOrigin(origins = {"http://localhost:3000",
            "https://green-smoke-0fa35931e.6.azurestaticapps.net/",
            "https://www.aprilshorrorcorner.com",
            "https://aprilshorrorcorner.com",
            "https://zealous-desert-09313150f.6.azurestaticapps.net/",
            "https://help.aprilshorrorcorner.com"})

    @GetMapping("/numMoviesEndpoint")

    public String getData() throws IOException {
        // AWS RDS Endpoint from the AWS Console
        String endpoint = "lizard.c6de8wseq94u.us-east-1.rds.amazonaws.com";
        String port = "3306"; // Default for MySQL
        String dbName = "mysql";

        // JDBC URL format: jdbc:<engine>://<endpoint>:<port>/<dbName>
        /*String url = "jdbc:mysql://" + endpoint + ":" + port + "/" + dbName;
        String username = "tomthelizard";
        String password = "lizarddd";*/
        String url = "jdbc:postgresql://aws-0-us-west-2.pooler.supabase.com:5432/movies";
        String username = "postgres.nfdbdmippwvzyuonxnpg";
        String password = "helpmekevinimdrowning";
        String response = new String();

        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            if (conn != null) {
                //System.out.println("Connected to AWS RDS successfully!");
                Statement stmt = conn.createStatement();
                //stmt.execute("USE movies");
                ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM horrorMovies");
                while (rs.next()) {
                    response = Integer.toString(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return response;

    }
}
