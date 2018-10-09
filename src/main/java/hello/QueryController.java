package hello;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import java.sql.*;
import java.lang.*; 
import java.util.Properties; 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class QueryController {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
    static final String DB_URL = "jdbc:postgresql://"+System.getenv("POSTGRES_SERVICE_HOST")+":"+System.getenv("POSTGRES_PORT_5432_TCP_PORT")+"/docker";
//  Database credentials
	static final String USER = "springboot";
	static final String PASS = "springboot";

    @RequestMapping("/query")
    public String index() {
    	Connection conn = null;
   		Statement stmt = null;
   		String first = null;
   		String last = null;
   		String state = null;
   		try{
   			//STEP 2: Register JDBC driver
			Class.forName("org.postgresql.Driver");

			//STEP 3: Open a connection
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
			//STEP 4: Execute a query

			System.out.println("Creating statement...");
			stmt = conn.createStatement();
			String sql;
			sql = "SELECT id, firstname, lastname FROM customer";

			ResultSet rs = stmt.executeQuery(sql);
			rs.next();
	         //Retrieve by column name
			int id  = rs.getInt("id");
			first = rs.getString("firstname");
			last = rs.getString("lastname");

			//Display values
			log.info("ID: " + id);
			log.info(", First: " + first);
			log.info(", Last: " + last);
			return sql;
			
      	}catch(SQLException se){
			//Handle errors for JDBC
			se.printStackTrace();
			return se.getSQLState();
			
		}finally{
	      //finally block used to close resources
	      try{
	         if(stmt!=null)
	            stmt.close();
	      }catch(SQLException se2){
	      }// nothing we can do
	      try{
	         if(conn!=null)
	            conn.close();
	      }catch(SQLException se){
	         se.printStackTrace();
	      }//end finally try
	      // return first + " " + last;
	      if (conn == null){
	      	return DB_URL;
	      }
	      return first + " " + last ;
	   }//end try
        // return "Greetings from Spring Boot222!";
    }

}