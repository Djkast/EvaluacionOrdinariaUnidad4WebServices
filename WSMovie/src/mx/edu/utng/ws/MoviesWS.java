package mx.edu.utng.ws;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MoviesWS {
	private Connection connection;
	private Statement statement;
	private ResultSet resultSet;
	private PreparedStatement ps;
	
	
	public MoviesWS() {
		conectar();
	}
	private void conectar(){
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/wstest",
					"postgres","admin");
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public int addMovie(Movie movie){
		int result =0;
		if (connection != null) {
			try {
				ps = connection.prepareStatement(
						"INSERT INTO movie (name,sinopsis,type,price) "
						+ "VALUES (?,?,?,?);");
				ps.setString(1, movie.getName());
				ps.setString(2, movie.getSinopsis());
				ps.setInt(3,movie.getType());
				ps.setFloat(4, movie.getPrice());
				result = ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public int editMovie(Movie movie){
		int result =0;
		if (connection != null) {
			try {
				ps = connection.prepareStatement(
						"UPDATE movie SET name =? , "
						+ "sinopsis = ? ,"
						+ "type = ? , "
						+ "price = ? "
						+ "WHERE id = ?;");
				ps.setString(1, movie.getName());
				ps.setString(2, movie.getSinopsis());
				ps.setInt(3,movie.getType());
				ps.setFloat(4, movie.getPrice());
				ps.setInt(5, movie.getId());
				result = ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	public int remuveMovie(int id){
		int result =0;
		if (connection != null) {
			try {
				ps = connection.prepareStatement(
						"DELETE FROM movie WHERE id = ?;");
				ps.setInt(1, id);
				result =ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public Movie[] getMovies(){
		Movie [] result = null;
		List<Movie> movies = new ArrayList<Movie>();
		
		if (connection != null) {
			try {
				statement = connection.createStatement();
				resultSet = statement.executeQuery(
						"SELECT * FROM movie;");
				while (resultSet.next()) {
					Movie movie = new Movie(
							resultSet.getInt("id"),
							resultSet.getString("name"),
							resultSet.getString("sinopsis"),
							resultSet.getInt("type"),
							resultSet.getFloat("price"));
					movies.add(movie);
					
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return movies.toArray(new Movie[movies.size()]);
	}
	public Movie getOneMovie(int id){
		Movie movie = null;
		
		if (connection != null) {
			try {
				ps = connection.prepareStatement("SELECT * FROM movie WHERE id = ?;");
				ps.setInt(1, id);
			    resultSet = ps.executeQuery();
				if(resultSet.next()) {
					movie = new Movie(
							resultSet.getInt("id"),
							resultSet.getString("name"),
							resultSet.getString("sinopsis"),
							resultSet.getInt("type"),
							resultSet.getFloat("price"));
					
					
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return movie;
	}
}
