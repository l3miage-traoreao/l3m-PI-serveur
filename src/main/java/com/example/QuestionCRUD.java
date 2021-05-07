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
@RequestMapping("/api/questions")
public class QuestionCRUD{

	@Autowired
	private DataSource dataSource;
	
	//Création de la ressource GET /api/Questions/
	@GetMapping("/")
	public ArrayList<Question> allQuestions(HttpServletResponse response){
	
		try (Connection connection = dataSource.getConnection()) {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Questions");
			
			ArrayList<Question> L = new ArrayList<Question>();
			while (rs.next()) {
			Question m = new Question();
			m.id = rs.getString("id");
			m.label = rs.getInt("label");
			m.description = rs.getString("description");
			m.secrets = rs.getString("secrets");
			m.points = rs.getInt("points");
			m.type = rs.getString("type");
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
	
	
	
	//Création de la ressource GET /api/Questions/{QuestionID}
	@GetMapping("/{defiId}/{QuestionId}")
	public Question read(@PathVariable(value="defiId") String iddef,@PathVariable(value="QuestionId") int id, HttpServletResponse response){
	
		try (Connection connection = dataSource.getConnection()) {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM questions WHERE id='"+iddef+"' AND label="+id);
			
			rs.next();
			Question m = new Question();
			m.id = rs.getString("id");
			m.label = rs.getInt("label");
			m.description = rs.getString("description");
			m.secrets = rs.getString("secrets");
			m.points = rs.getInt("points");
			m.type = rs.getString("type");
			
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
	
	
	//Création de la ressource GET /api/Questions/{QuestionID}
	@GetMapping("/{defiId}")
	public ArrayList<Question> readQuestionsdefi(@PathVariable(value="defiId") String iddef, HttpServletResponse response){
	
		try (Connection connection = dataSource.getConnection()) {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM questions WHERE id='"+iddef+"'");
			
			ArrayList<Question> L = new ArrayList<Question>();
			while (rs.next()) {
			
			Question m = new Question();
			m.id = rs.getString("id");
			m.label = rs.getInt("label");
			m.description = rs.getString("description");
			m.secrets = rs.getString("secrets");
			m.points = rs.getInt("points");
			m.type = rs.getString("type");
			
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
	
	
	
	
	//Création de la ressource POST /api/Questions/{QuestionID}
	@PostMapping("/{QuestionId}")
	public Question create(@PathVariable(value="QuestionId") String id, @RequestBody Question m, HttpServletResponse response){
	
		try (Connection connection = dataSource.getConnection()){
			PreparedStatement stmt = connection.prepareStatement ("INSERT INTO questions(id,label,description,secrets,points,type) VALUES (?,?,?,?,?,?)");
			stmt.setString(1, m.id);
			stmt.setInt(2, m.label);
			stmt.setString(3, m.description);
			stmt.setString(4, m.secrets);
			stmt.setInt(5, m.points);
			stmt.setString(6, m.type);
			
			stmt.execute();
			
			Question ind=read(m.id,m.label, response);
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
	
	
	
	
	
	//Création de la ressource PUT /api/Questions/{QuestionID}
	@PutMapping("/{defiId}/{QuestionId}")
	public Question update(@PathVariable(value="defiId") String iddef,@PathVariable(value="QuestionId") int id, @RequestBody Question m, HttpServletResponse response){
	
		try (Connection connection = dataSource.getConnection()){
			
			
			PreparedStatement stmt = connection.prepareStatement ("UPDATE Questions SET id=?, label=?,description=?,secrets=?, points=?, type=? WHERE id=? AND label=?");
			stmt.setString(1, m.id);
			stmt.setInt(2, m.label);
			stmt.setString(3, m.description);
			stmt.setString(4, m.secrets);
			stmt.setInt(5, m.points);
			stmt.setString(6, m.type);
			stmt.setString(7, iddef);
			stmt.setInt(8, id);
			
			stmt.execute();
			
			
			Question mat=read(m.id,m.label, response);
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
	
	
	///Création de la ressource DELETE /api/Questions/{QuestionID}
	@DeleteMapping("/{defiId}/{QuestionId}")
	public void delete(@PathVariable(value="defiId") String iddef,@PathVariable(value="QuestionId") String id, HttpServletResponse response){
		try (Connection connection  = dataSource.getConnection()) { 
		   	Statement stat = connection.createStatement(); 
			stat.executeUpdate("DELETE FROM Questions WHERE id='"+iddef+"' AND label='"+id+"'");
			
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
