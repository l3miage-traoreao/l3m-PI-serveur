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
@RequestMapping("/api/materiels")
public class MaterielCRUD{

	@Autowired
	private DataSource dataSource;
	
	//Création de la ressource GET /api/materiels/
	@GetMapping("/")
	public ArrayList<Defi> allMateriels(HttpServletResponse response){
	
		try (Connection connection = dataSource.getConnection()) {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM materiels");
			
			ArrayList<Materiel> L = new ArrayList<Materiel>();
			while (rs.next()) {
			Materiel m = new Materiel();
			m.id = rs.getString("id");
		    m.label = rs.getInt("label");
			m.description = rs.getString("description");
			m.ressource = rs.getString("ressource");
			L.add(m);
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
	
	
	
	//Création de la ressource GET /api/materiels/{materielID}
	@GetMapping("/{materielId}")
	public Materiel read(@PathVariable(value="materielId") String id, HttpServletResponse response){
	
		try (Connection connection = dataSource.getConnection()) {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM materiels WHERE id='"+id+"'");
			
			rs.next();
			Materiel m = new Materiel();
			m.id = rs.getString("id");
		    m.label = rs.getInt("label");
			m.description = rs.getString("description");
			m.ressource = rs.getString("ressource");
			
			//stmt.close();
        		//connection.close();
			return m;
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
	
	
	//Création de la ressource POST /api/materiels/{materielID}
	@PostMapping("/{materielId}")
	public Materiel create(@PathVariable(value="materielId") String id, @RequestBody Materiel m, HttpServletResponse response){
	
		try (Connection connection = dataSource.getConnection()){
			Statement stmt = connection.createStatement();
			stmt.executeUpdate("INSERT INTO materiels(id,label,description,ressource) VALUES ('"+m.id+"',"+m.label+","+m.description+",'"+m.ressource+"')");
			
			Materiel mat=read(id, response);
			//stmt.close();
        		//connection.close();
			return mat;
		}
		catch (Exception e) {
		if(id!=m.id){
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
	
	
	
	
	
	//Création de la ressource PUT /api/materiels/{materielID}
	@PutMapping("/{materielId}")
	public Materiel update(@PathVariable(value="materielId") String id, @RequestBody Materiel m, HttpServletResponse response){
	
		try (Connection connection = dataSource.getConnection()){
			Statement stmt = connection.createStatement();
			stmt.executeUpdate("UPDATE materiels SET id='"+m.id+"', label="+m.label+",description='"+m.description+"',ressource='"+m.ressource+"' WHERE id='"+id+"'");
			
			Materiel mat=read(id, response);
			//stmt.close();
        		//connection.close();
			return mat;
		}
		catch (Exception e) {
		if(id!=m.id){
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
	
	
	//Création de la ressource DELETE /api/materiels/{materielID}
	@DeleteMapping("/{materielId}")
	public void delete(@PathVariable(value="materielId") String id, HttpServletResponse response){
		try (Connection connection  = dataSource.getConnection()) { 
		   	Statement stat = connection.createStatement(); 
			stat.executeUpdate("DELETE FROM materiels WHERE id='"+id+"'");
			
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
