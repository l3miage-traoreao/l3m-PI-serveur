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
@RequestMapping("/api/defis")
public class DefiCRUD{

	@Autowired
	private DataSource dataSource;
	
	//Création de la ressource GET /api/defis/
	@GetMapping("/")
	public ArrayList<Defi> allDefis(HttpServletResponse response){
	
		try (Connection connection = dataSource.getConnection()) {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM defis");
			
			ArrayList<Defi> L = new ArrayList<Defi>();
			while (rs.next()) {
			Defi d = new Defi();
			d.id = rs.getString("id");
			d.titre = rs.getString("titre");
			d.datedecreation = rs.getTimestamp("datedecreation");
			d.description = rs.getString("description");
			d.datedemodification = rs.getTimestamp("datedemodification");
			d.type = rs.getString("type");
			d.auteur = rs.getString("auteur");
			d.arret = rs.getString("arret");
			d.codearret = rs.getString("codearret");
			L.add(d);
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
	
	
	
	//Création de la ressource GET /api/defis/{defiID}
	@GetMapping("/{defiId}")
	public Defi read(@PathVariable(value="defiId") String id, HttpServletResponse response){
	
		try (Connection connection = dataSource.getConnection()) {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM defis WHERE id='"+id+"'");
			
			rs.next();
			Defi d = new Defi();
			d.id = rs.getString("id");
			d.titre = rs.getString("titre");
			d.datedecreation = rs.getTimestamp("datedecreation");
			d.description = rs.getString("description");
			d.datedemodification = rs.getTimestamp("datedemodification");
			d.type = rs.getString("type");
			d.arret = rs.getString("arret");
			d.codearret = rs.getString("codearret");
			
			//stmt.close();
        		//connection.close();
			return d;
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
	
	
	//Création de la ressource POST /api/defis/{defiID}
	@PostMapping("/{defiId}")
	public Defi create(@PathVariable(value="defiId") String id, @RequestBody Defi d, HttpServletResponse response){
	
		try (Connection connection = dataSource.getConnection()){
			Statement stmt = connection.createStatement();
			stmt.executeUpdate("INSERT INTO defis VALUES ('"+d.id+"','"+d.titre+"',"+d.datedecreation+",'"+d.description+"',"+d.datedemodification+",'"+d.type+"','"+d.auteur+"','"+d.arret+"','"+d.codearret+"')");
			
			Defi def=read(id, response);
			//stmt.close();
        		//connection.close();
			return def;
		}
		catch (Exception e) {
		if(id!=d.id){
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
	
	
	
	
	
	//Création de la ressource PUT /api/defis/{defiID}
	@PutMapping("/{defiId}")
	public Defi update(@PathVariable(value="defiId") String id, @RequestBody Defi d, HttpServletResponse response){
	
		try (Connection connection = dataSource.getConnection()){
			Statement stmt = connection.createStatement();
			stmt.executeUpdate("UPDATE defis SET id='"+d.id+"', titre='"+d.titre+"',datedecreation="+d.datedecreation+",description='"+d.description+"' WHERE id='"+id+"'");
			
			Defi def=read(id, response);
			//stmt.close();
        		//connection.close();
			return def;
		}
		catch (Exception e) {
		if(id!=d.id){
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
	
	
	//Création de la ressource DELETE /api/defis/{defiID}
	@DeleteMapping("/{defiId}")
	public void delete(@PathVariable(value="defiId") String id, HttpServletResponse response){
		try (Connection connection  = dataSource.getConnection()) { 
		   	Statement stat = connection.createStatement(); 
			stat.executeUpdate("DELETE FROM defis WHERE id='"+id+"'");
			
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
