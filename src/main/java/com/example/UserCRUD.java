package com.example;
import java.sql.Connection; 
import java.sql.ResultSet; 
import java.sql.Statement; 
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
			L.add(u);
			}
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
	
	//Création de la ressource PUT /api/users/{userID}
	@PutMapping("/{userId}")
	public User update(@PathVariable(value="userId") String id, @RequestBody User u, HttpServletResponse response){
	
		try (Connection connection = dataSource.getConnection()){
			Statement stmt = connection.createStatement();
			stmt.executeUpdate("UPDATE chamis SET login='"+u.login+"', age="+u.age+" WHERE login="+id);
			
			
			
			
			
			return u;
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
		  
}
