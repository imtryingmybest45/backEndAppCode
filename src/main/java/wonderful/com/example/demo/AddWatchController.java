package wonderful.com.example.demo;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.sql.*;
import java.util.*;

@RestController

public class AddWatchController {

    //@CrossOrigin(origins="http://localhost:3000")
    @CrossOrigin(origins = {"http://localhost:3000",
            "https://green-smoke-0fa35931e.6.azurestaticapps.net/",
            "https://www.aprilshorrorcorner.com",
            "https://aprilshorrorcorner.com",
            "https://zealous-desert-09313150f.6.azurestaticapps.net/",
            "https://help.aprilshorrorcorner.com"})

    @PostMapping("/watchSubEndpoint")

    public String addData(@RequestBody MyRequestDTO dto) throws IOException, SQLException {

        // AWS RDS Endpoint from the AWS Console
        String endpoint = "lizard.c6de8wseq94u.us-east-1.rds.amazonaws.com";
        String port = "3306"; // Default for MySQL
        String dbName = "mysql";
        String url = "jdbc:mysql://" + endpoint + ":" + port + "/" + dbName;
        String username = "tomthelizard";
        String password = "lizarddd";

        String name;
        String poster = "placeholder";
        String blurb = "placeholder";
        String year;
        String movieQuery;

        Map<String, Object> response = new HashMap<>();
        CoreFunctions coreFunctions = new CoreFunctions();

        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            if (conn != null) {
                System.out.println("Connected to AWS RDS successfully!");
                Statement stmt = conn.createStatement();
                stmt.execute("USE movies");
                //stmt.execute("INSERT INTO horrorMovies (name, poster, year, review, tier, fullReview)");

                // Retrieve values by column name
                name = dto.getMovieName();
                movieQuery = name;
                String newname = name.replaceAll("'","''");

                if (movieQuery.contains("(")){
                    int n = 0;
                    int index = movieQuery.indexOf("(");
                    for (int i = 1; i < 5; i++) {
                        char V =  movieQuery.charAt(index+i);
                        if (Character.isDigit(V)){
                            n = n+1;
                        }
                    }
                    if (n==4){
                        Integer[] indices = {index-1, index, index+1, index+2, index+3, index+4, index+5};
                        movieQuery = coreFunctions.removeCharsAtIndices(movieQuery,indices);
                    }
                }

                System.out.println(movieQuery);

                blurb = dto.getMovieBlurb();
                year = dto.getMovieYear();
                if (year.length()<4){
                    year = "0";
                }
                poster = coreFunctions.getMoviePoster(movieQuery, year);

                name = "'"+newname+"'";
                year = "'"+year+"'";

                String valuesInserted = String.format("INSERT INTO watchList (name, poster, year, blurb) VALUES (%s, %s, %s, %s);",name, poster, year, blurb);
                stmt.executeUpdate(valuesInserted);

            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return "You have submitted your review. Please wait a few minutes for the website to refresh.";
    }
}
