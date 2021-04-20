package com.example;
import java.sql.*;
import java.util.ArrayList; 
import javax.servlet.http.HttpServletResponse; 
import javax.sql.DataSource; 
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.web.bind.annotation.CrossOrigin; 
import org.springframework.web.bind.annotation.DeleteMapping; 
import org.springframework.web.bind.annotation.GetMapping; 
import org.springframework.web.bind.annotation.PathVariable; 
import org.springframework.web.bind.annotation.PostMapping; 
import org.springframework.web.bind.annotation.PutMapping; 
import org.springframework.web.bind.annotation.RequestBody; 
import org.springframework.web.bind.annotation.RequestMapping; 
import org.springframework.web.bind.annotation.RestController;
@CrossOrigin
@RestController
@RequestMapping("/api/users")
public class UserCRUD{

	@Autowired
	private DataSource dataSource;

	
	//Création de la ressource GET /api/users/
	@GetMapping("/")
	public ArrayList<User> allUsers(HttpServletResponse response){
	
		try (Connection connection = dataSource.getConnection()) {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM chamis");
			
			ArrayList<User> L = new ArrayList<User>();
			while (rs.next()) {
			User u = new User();
			u.login = rs.getString("login");
			u.age = rs.getInt("age");
			u.ville = rs.getString("ville");
			u.description = rs.getString("description");
			L.add(u);
			}
			stmt.close();
        		connection.close();
			return L;
		}
		catch (Exception e) {
			response.setStatus(500);
			try{
				response.getOutputStream().print(e.getMessage());
			}
			catch (Exception e2) {
				System.err.println(e2.getMessage());
			}
			System.err.println(e.getMessage());
			return null;
		} 
	}
	
	//Création de la ressource GET /api/users/{userID}
	@GetMapping("/{userId}")
	public User read(@PathVariable(value="userId") String id, HttpServletResponse response){
	
		try (Connection connection = dataSource.getConnection()) {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM chamis WHERE login='"+id+"'");
			
			rs.next();
			User u = new User();
			u.login = rs.getString("login");
			u.age = rs.getInt("age");
			u.ville = rs.getString("ville");
			u.description = rs.getString("description");
			
			//stmt.close();
        		//connection.close();
			return u;
		}
		catch (Exception e) {
			response.setStatus(404);
			try{
				response.getOutputStream().print(e.getMessage());
			}
			catch (Exception e2) {
				System.err.println(e2.getMessage());
			}
			System.err.println(e.getMessage());
			return null;
		} 
	}
	
	
	
	//Création de la ressource POST /api/users/{userID}
	@PostMapping("/{userId}")
	public User create(@PathVariable(value="userId") String id, @RequestBody User u, HttpServletResponse response){
	
		try (Connection connection = dataSource.getConnection()){
		
			PreparedStatement stmt = connection.prepareStatement ("INSERT INTO chamis VALUES (?,?,?,?)");
			stmt.setString(1, u.login);
			stmt.setInt(2, u.age);
			stmt.setString(3, u.ville);
			stmt.setString(4, u.description);
			
			
			
			stmt.execute();
			
			User c=read(u.login, response);
			//stmt.close();
        		//connection.close();
			return c;
		}
		catch (Exception e) {
		if(id!=u.login){
			response.setStatus(412);
		}
		else{
			response.setStatus(403);
		}
			try{
				response.getOutputStream().print(e.getMessage());
			}
			catch (Exception e2) {
				System.err.println(e2.getMessage());
			}
			System.err.println(e.getMessage());
			return null;
		
		}
	
	}
	
	
	
	//Création de la ressource PUT /api/users/{userID}
	@PutMapping("/{userId}")
	public User update(@PathVariable(value="userId") String id, @RequestBody User u, HttpServletResponse response){
	
		try (Connection connection = dataSource.getConnection()){
		
			PreparedStatement stmt = connection.prepareStatement ("UPDATE chamis SET login=?, age=?,ville=?,description=? WHERE login=?");
			stmt.setString(1, u.login);
			stmt.setInt(2, u.age);
			stmt.setString(3, u.ville);
			stmt.setString(4, u.description);
			stmt.setString(5, id);
			
			stmt.execute();
			
			User c=read(u.login, response);
			//stmt.close();
        		//connection.close();
			return c;
		}
		catch (Exception e) {
		if(id!=u.login){
			response.setStatus(412);
		}
		else{
			response.setStatus(404);
		}
			try{
				response.getOutputStream().print(e.getMessage());
			}
			catch (Exception e2) {
				System.err.println(e2.getMessage());
			}
			System.err.println(e.getMessage());
			return null;
		
		} 
	}
	
	//Création de la ressource DELETE /api/users/{userID}
	@DeleteMapping("/{userId}")
	public void delete(@PathVariable(value="userId") String id, HttpServletResponse response){
		try (Connection connection  = dataSource.getConnection()) { 
		   	Statement stat = connection.createStatement(); 
			stat.executeUpdate("DELETE FROM chamis WHERE login='"+id+"'");
			//stat.close();
        		//connection.close();
		}
		catch(Exception e){
			response.setStatus(404);
			try{
				response.getOutputStream().print(e.getMessage());
			}
			catch (Exception e2) {
				System.err.println(e2.getMessage());
			}
			System.err.println(e.getMessage());
			
		}
	}
		  






 
}
