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
@RequestMapping("/api/arrets")
public class ArretCRUD{

	@Autowired
	private DataSource dataSource;

	
	//Création de la ressource GET /api/Arrets/
	@GetMapping("/")
	public ArrayList<Arret> allArrets(HttpServletResponse response){
	
		try (Connection connection = dataSource.getConnection()) {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM arrets");
			
			ArrayList<Arret> L = new ArrayList<Arret>();
			while (rs.next()) {
			Arret u = new Arret();
			u.arret = rs.getString("arret");
			u.codearret = rs.getString("codearret");
			u.streetmap = rs.getString("streetmap");
			
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
	
	//Création de la ressource GET /api/Arrets/{ArretID}
	@GetMapping("/{ArretId}")
	public Arret read(@PathVariable(value="ArretId") String id, HttpServletResponse response){
	
		try (Connection connection = dataSource.getConnection()) {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM arrets WHERE codearret='"+id+"'");
			
			rs.next();
			Arret u = new Arret();
			u.arret = rs.getString("arret");
			u.codearret = rs.getString("codearret");
			u.streetmap = rs.getString("streetmap");
			
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
	
	
	
	//Création de la ressource POST /api/Arrets/{ArretID}
	@PostMapping("/{ArretId}")
	public Arret create(@PathVariable(value="ArretId") String id, @RequestBody Arret u, HttpServletResponse response){
	
		try (Connection connection = dataSource.getConnection()){
		
			PreparedStatement stmt = connection.prepareStatement ("INSERT INTO arrets VALUES (?,?,?)");
			stmt.setString(1, u.arret);
			stmt.setString(2, u.codearret);
			stmt.setString(3, u.streetmap);
			
			
			
			stmt.execute();
			
			Arret c=read(u.codearret, response);
			//stmt.close();
        		//connection.close();
			return c;
		}
		catch (Exception e) {
		if(id!=u.codearret){
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
	
	
	
	//Création de la ressource PUT /api/Arrets/{ArretID}
	@PutMapping("/{ArretId}")
	public Arret update(@PathVariable(value="ArretId") String id, @RequestBody Arret u, HttpServletResponse response){
	
		try (Connection connection = dataSource.getConnection()){
		
			PreparedStatement stmt = connection.prepareStatement ("UPDATE arrets SET arret=?, codearret=?,streetmap=? WHERE codearret=?");
			stmt.setString(1, u.arret);
			stmt.setString(2, u.codearret);
			stmt.setString(3, u.streetmap);
			stmt.setString(4, id);
			
			stmt.execute();
			
			Arret c=read(u.codearret, response);
			//stmt.close();
        		//connection.close();
			return c;
		}
		catch (Exception e) {
		if(id!=u.codearret){
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
	
	//Création de la ressource DELETE /api/Arrets/{ArretID}
	@DeleteMapping("/{ArretId}")
	public void delete(@PathVariable(value="ArretId") String id, HttpServletResponse response){
		try (Connection connection  = dataSource.getConnection()) { 
		   	Statement stat = connection.createStatement(); 
			stat.executeUpdate("DELETE FROM arrets WHERE codearret='"+id+"'");
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
