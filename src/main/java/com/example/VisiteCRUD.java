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
@RequestMapping("/api/visites")
public class VisiteCRUD{

	@Autowired
	private DataSource dataSource;
	
	//Création de la ressource GET /api/visites/
	@GetMapping("/")
	public ArrayList<Visite> allVisites(HttpServletResponse response){
	
		try (Connection connection = dataSource.getConnection()) {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM visites");
			
			ArrayList<Visite> L = new ArrayList<Visite>();
			while (rs.next()) {
			Visite d = new Visite();
			d.id = rs.getString("id");
			d.visite = rs.getString("visite");
			d.visiteur = rs.getString("visiteur");
			d.datedevisite = rs.getTimestamp("datedevisite");
			d.mode = rs.getString("mode");
			d.statut = rs.getString("statut");
			d.score = rs.getInt("score");
			d.temps = rs.getString("temps");
			d.commentaires = rs.getString("commentaires");
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
	
	
	
	//Création de la ressource GET /api/visites/{defiID}
	@GetMapping("/defi/{visiteId}")
	public ArrayList<Visite> readvisitedefi(@PathVariable(value="visiteId") String id, HttpServletResponse response){
	
		try (Connection connection = dataSource.getConnection()) {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM visites WHERE id='"+id+"'");
			
			ArrayList<Visite> L = new ArrayList<Visite>();
			while (rs.next()) {
			Visite d = new Visite();
			d.id = rs.getString("id");
			d.visite = rs.getString("visite");
			d.visiteur = rs.getString("visiteur");
			d.datedevisite = rs.getTimestamp("datedevisite");
			d.mode = rs.getString("mode");
			d.statut = rs.getString("statut");
			d.score = rs.getInt("score");
			d.temps = rs.getString("temps");
			d.commentaires = rs.getString("commentaires");
			
			L.add(d);
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
	
	
	//Création de la ressource GET /api/visites/{visiteurID}
	@GetMapping("/visiteur/{visiteIde}")
	public ArrayList<Visite> readvisiteur(@PathVariable(value="visiteIde") String id, HttpServletResponse response){
	
		try (Connection connection = dataSource.getConnection()) {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM visites WHERE visiteur='"+id+"'");
			
			ArrayList<Visite> L = new ArrayList<Visite>();
			while (rs.next()) {
			Visite d = new Visite();
			d.id = rs.getString("id");
			d.visite = rs.getString("visite");
			d.visiteur = rs.getString("visiteur");
			d.datedevisite = rs.getTimestamp("datedevisite");
			d.mode = rs.getString("mode");
			d.statut = rs.getString("statut");
			d.score = rs.getInt("score");
			d.temps = rs.getString("temps");
			d.commentaires = rs.getString("commentaires");
			
			L.add(d);
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
	
	
	
	//Création de la ressource GET /api/visites/{visiteID}
	@GetMapping("/{visiteIdes}")
	public Visite read(@PathVariable(value="visiteIdes") String id, HttpServletResponse response){
	
		try (Connection connection = dataSource.getConnection()) {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM visites WHERE visite='"+id+"'");
			
			rs.next();
			Visite d = new Visite();
			d.id = rs.getString("id");
			d.visite = rs.getString("visite");
			d.visiteur = rs.getString("visiteur");
			d.datedevisite = rs.getTimestamp("datedevisite");
			d.mode = rs.getString("mode");
			d.statut = rs.getString("statut");
			d.score = rs.getInt("score");
			d.temps = rs.getString("temps");
			d.commentaires = rs.getString("commentaires");
			
			
			
			
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
	@PostMapping("/{visiteId}")
	public Visite create(@PathVariable(value="visiteId") String id, @RequestBody Visite d, HttpServletResponse response){
	
		try (Connection connection = dataSource.getConnection()){
		
			PreparedStatement stmt = connection.prepareStatement ("INSERT INTO visites VALUES (?,?,?,?,?,?,?,?,?)");
			stmt.setString(1, d.id);
			stmt.setString(2, d.visite);
			stmt.setString(3, d.visiteur);
			stmt.setTimestamp(4, d.datedevisite);
			stmt.setString(5, d.mode);
			stmt.setString(6, d.statut);
			stmt.setInt(7, d.score);
			stmt.setString(8, d.temps);
			stmt.setString(9, d.commentaires);
			
			
			stmt.execute();
		
			
			Visite vis=read(d.visite, response);
			//stmt.close();
        		//connection.close();
			return vis;
		}
		catch (Exception e) {
		if(id!=d.visite){
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
	
	
	//Création de la ressource PUT /api/visites/{userID}
	@PutMapping("/{visiteId}")
	public Visite update(@PathVariable(value="visiteId") String id, @RequestBody Visite d, HttpServletResponse response){
	
		try (Connection connection = dataSource.getConnection()){
		
			PreparedStatement stmt = connection.prepareStatement ("UPDATE visites SET id=?, visite=?,visiteur=?,datedevisite=?, mode=?, statut=?, score=?, temps=?, commentaires=? WHERE visite=?");
			stmt.setString(1, d.id);
			stmt.setString(2, d.visite);
			stmt.setString(3, d.visiteur);
			stmt.setTimestamp(4, d.datedevisite);
			stmt.setString(5, d.mode);
			stmt.setString(6, d.statut);
			stmt.setInt(7, d.score);
			stmt.setString(8, d.temps);
			stmt.setString(9, d.commentaires);
			stmt.setString(10, id);
			stmt.execute();
			
			Visite c=read(d.visite, response);
			//stmt.close();
        		//connection.close();
			return c;
		}
		catch (Exception e) {
		if(id!=d.visite){
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
	
	
	
	//Création de la ressource DELETE /api/visite/{visiteID}
	@DeleteMapping("/{visiteId}")
	public void delete(@PathVariable(value="visiteId") String id, HttpServletResponse response){
		try (Connection connection  = dataSource.getConnection()) { 
		   	Statement stat = connection.createStatement(); 
			stat.executeUpdate("DELETE FROM visite WHERE visite='"+id+"'");
			
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
