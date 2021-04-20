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
@RequestMapping("/api/materiels")
public class MaterielCRUD{

	@Autowired
	private DataSource dataSource;
	
	//Création de la ressource GET /api/materiels/
	@GetMapping("/")
	public ArrayList<Materiel> allMateriels(HttpServletResponse response){
	
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
	@GetMapping("/{defiId}/{materielId}")
	public Materiel read(@PathVariable(value="defiId") String iddef,@PathVariable(value="materielId") int id, HttpServletResponse response){
	
		try (Connection connection = dataSource.getConnection()) {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM materiels WHERE id='"+iddef+"' AND label="+id);
			
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
	
	
	//Création de la ressource GET /api/materiels/{materielID}
	@GetMapping("/{defiId}")
	public ArrayList<Materiel> readmaterielsdefi(@PathVariable(value="defiId") String iddef, HttpServletResponse response){
	
		try (Connection connection = dataSource.getConnection()) {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM materiels WHERE id='"+iddef+"'");
			
			ArrayList<Materiel> L = new ArrayList<Materiel>();
			while (rs.next()) {
			
			Materiel m = new Materiel();
			m.id = rs.getString("id");
			m.label = rs.getInt("label");
			m.description = rs.getString("description");
			m.ressource = rs.getString("ressource");
			
			L.add(m);
			}
			//stmt.close();
        		//connection.close();
			return L;
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
			PreparedStatement stmt = connection.prepareStatement ("INSERT INTO materiels(id,label,description,ressource) VALUES (?,?,?,?)");
			stmt.setString(1, m.id);
			stmt.setInt(2, m.label);
			stmt.setString(3, m.description);
			stmt.setString(4, m.ressource);
			
			stmt.execute();
			
			Materiel mat=read(m.id,m.label, response);
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
	@PutMapping("/{defiId}/{materielId}")
	public Materiel update(@PathVariable(value="defiId") String iddef,@PathVariable(value="materielId") int id, @RequestBody Materiel m, HttpServletResponse response){
	
		try (Connection connection = dataSource.getConnection()){
			
			
			PreparedStatement stmt = connection.prepareStatement ("UPDATE materiels SET id=?, label=?,description=?,ressource=? WHERE id=? AND label=?");
			stmt.setString(1, m.id);
			stmt.setInt(2, m.label);
			stmt.setString(3, m.description);
			stmt.setString(4, m.ressource);
			stmt.setString(5, iddef);
			stmt.setInt(6, id);
			
			stmt.execute();
			
			
			Materiel mat=read(m.id,m.label, response);
			//stmt.close();
        		//connection.close();
			return mat;
		}
		catch (Exception e) {
		if(iddef!=m.id){
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
	
	
	//Création de la ressource DELETE /api/Materiels/{MaterielID}
	@DeleteMapping("/{defiId}/{MaterielId}")
	public void delete(@PathVariable(value="defiId") String iddef,@PathVariable(value="MaterielId") String id, HttpServletResponse response){
		try (Connection connection  = dataSource.getConnection()) { 
		   	Statement stat = connection.createStatement(); 
			stat.executeUpdate("DELETE FROM Materiels WHERE id='"+iddef+"' AND label='"+id+"'");
			
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
