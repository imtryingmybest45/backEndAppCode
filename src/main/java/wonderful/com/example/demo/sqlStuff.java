package wonderful.com.example.demo;
import javax.xml.transform.Result;
import java.sql.*;

public class sqlStuff {

        public static void main(String[] args) {
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

            try (Connection conn = DriverManager.getConnection(url, username, password)) {
                if (conn != null) {
                    System.out.println("Connected to AWS RDS successfully!");
                    Statement stmt = conn.createStatement();
                    //stmt.execute("USE movies");
                    //ResultSet rs = stmt.executeQuery("SELECT * FROM horrorMovies");
                    ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM horrorMovies");
                    while (rs.next()) {
                        // Retrieve values by column name
                        //int id = rs.getInt("movieId");
                        int count = rs.getInt(1);
                        //String name = rs.getString("name");

                        // Display to console
                        //System.out.println("ID: " + id + ", Name: " + name);
                        System.out.println(count);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
