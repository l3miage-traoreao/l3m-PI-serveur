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
@RequestMapping("/api/indices")
public class IndiceCRUD{

	@Autowired
	private DataSource dataSource;
	
	//Création de la ressource GET /api/Indices/
	@GetMapping("/")
	public ArrayList<Indice> allIndices(HttpServletResponse response){
	
		try (Connection connection = dataSource.getConnection()) {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Indices");
			
			ArrayList<Indice> L = new ArrayList<Indice>();
			while (rs.next()) {
			Indice m = new Indice();
			m.id = rs.getString("id");
			m.label = rs.getInt("label");
			m.description = rs.getString("description");
			m.points = rs.getInt("points");
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
	
	
	
	//Création de la ressource GET /api/Indices/{IndiceID}
	@GetMapping("/{defiId}/{IndiceId}")
	public Indice read(@PathVariable(value="defiId") String iddef,@PathVariable(value="IndiceId") int id, HttpServletResponse response){
	
		try (Connection connection = dataSource.getConnection()) {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Indices WHERE id='"+iddef+"' AND label="+id);
			
			rs.next();
			Indice m = new Indice();
			m.id = rs.getString("id");
			m.label = rs.getInt("label");
			m.description = rs.getString("description");
			m.points = rs.getInt("points");
			
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
	
	
	//Création de la ressource GET /api/Indices/{IndiceID}
	@GetMapping("/{defiId}")
	public ArrayList<Indice> readIndicesdefi(@PathVariable(value="defiId") String iddef, HttpServletResponse response){
	
		try (Connection connection = dataSource.getConnection()) {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Indices WHERE id='"+iddef+"'");
			
			ArrayList<Indice> L = new ArrayList<Indice>();
			while (rs.next()) {
			
			Indice m = new Indice();
			m.id = rs.getString("id");
			m.label = rs.getInt("label");
			m.description = rs.getString("description");
			m.points = rs.getInt("points");
			
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
	
	
	
	
	//Création de la ressource POST /api/Indices/{IndiceID}
	@PostMapping("/{IndiceId}")
	public Indice create(@PathVariable(value="IndiceId") String id, @RequestBody Indice m, HttpServletResponse response){
	
		try (Connection connection = dataSource.getConnection()){
			PreparedStatement stmt = connection.prepareStatement ("INSERT INTO Indices(id,label,description,points) VALUES (?,?,?,?)");
			stmt.setString(1, m.id);
			stmt.setInt(2, m.label);
			stmt.setString(3, m.description);
			stmt.setInt(4, m.points);
			
			stmt.execute();
			
			Indice ind=read(m.id,m.label, response);
			//stmt.close();
        		//connection.close();
			return ind;
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
	
	
	
	
	
	//Création de la ressource PUT /api/Indices/{IndiceID}
	@PutMapping("/{defiId}/{IndiceId}")
	public Indice update(@PathVariable(value="defiId") String iddef,@PathVariable(value="IndiceId") int id, @RequestBody Indice m, HttpServletResponse response){
	
		try (Connection connection = dataSource.getConnection()){
			
			
			PreparedStatement stmt = connection.prepareStatement ("UPDATE Indices SET id=?, label=?,description=?,points=? WHERE id=? AND label=?");
			stmt.setString(1, m.id);
			stmt.setInt(2, m.label);
			stmt.setString(3, m.description);
			stmt.setInt(4, m.points);
			stmt.setString(5, iddef);
			stmt.setInt(6, id);
			
			stmt.execute();
			
			
			Indice mat=read(m.id,m.label, response);
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
	
	
	//Création de la ressource DELETE /api/Indices/{IndiceID}
	@DeleteMapping("/{defiId}/{IndiceId}")
	public void delete(@PathVariable(value="defiId") String iddef,@PathVariable(value="IndiceId") String id, HttpServletResponse response){
		try (Connection connection  = dataSource.getConnection()) { 
		   	Statement stat = connection.createStatement(); 
			stat.executeUpdate("DELETE FROM Indices WHERE id='"+iddef+"' AND label='"+id+"'");
			
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
