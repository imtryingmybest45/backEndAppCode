package wonderful.com.example.demo;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.*;

@RestController

public class EditController {
    //@CrossOrigin(origins="http://localhost:3000")

    @CrossOrigin(origins = {"http://localhost:3000",
            "https://green-smoke-0fa35931e.6.azurestaticapps.net/",
            "https://www.aprilshorrorcorner.com",
            "https://aprilshorrorcorner.com",
            "https://zealous-desert-09313150f.6.azurestaticapps.net/",
            "https://help.aprilshorrorcorner.com"})

    @PostMapping("/editEndpoint")
    public String editData(@RequestBody MyRequestDTO dto) throws IOException, SQLException {
        // AWS RDS Endpoint from the AWS Console
        // AWS RDS Endpoint from the AWS Console
        String endpoint = "lizard.c6de8wseq94u.us-east-1.rds.amazonaws.com";
        String port = "3306"; // Default for MySQL
        String dbName = "mysql";
        String url = "jdbc:mysql://" + endpoint + ":" + port + "/" + dbName;
        String username = "tomthelizard";
        String password = "lizarddd";

        String name;
        String poster = "placeholder";
        String review;
        String tier = "placeholder";
        String fullReview = "0";
        String year;
        String movieQuery;
        String rating;
        String origYear;
        String origRating;
        String movieId;

        CoreFunctions coreFunctions = new CoreFunctions();

        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            if (conn != null) {
                Statement stmt = conn.createStatement();
                stmt.execute("USE movies");
                //ResultSet rs = stmt.executeQuery("SELECT * FROM horrorMovies WHERE name = '"+dto.getMovieName()+"';");
                //rs.next();

                // Retrieve values by column name
                name = dto.getMovieName();
                movieId = dto.getMovieId();


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

                review = dto.getMovieReview();
                year = dto.getMovieYear();
                tier = dto.getMovieTier();
                rating = dto.getMovieRating();
                poster = coreFunctions.getMoviePoster(movieQuery, year);

                //origYear = rs.getString("year");
                //origRating = rs.getString("rating");

                int wordCount = coreFunctions.countWords(review);
                if (wordCount > 500) {
                    fullReview = "1";
                }

                name = "'"+newname+"'";
                year = "'"+year+"'";

                /*if (year.equals(origYear) && rating.equals(origRating)) {
                    review = "'" + review + "'";
                }
                else{*/

                review = "'"+review+"'";

                stmt.executeUpdate(String.format("UPDATE horrorMovies SET name = %s WHERE movieID = %s;",name,movieId));

                String valuesInserted = String.format("UPDATE horrorMovies SET poster = %s, year = %s, review = %s, tier = %s, fullReview = %s, rating = %s WHERE name = %s;",poster, year, review, tier, fullReview,rating,name);
                //System.out.println(valuesInserted);
                stmt.executeUpdate(valuesInserted);

            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return "You have edited your review. Please wait a few minutes for the website to refresh.";
    }
}