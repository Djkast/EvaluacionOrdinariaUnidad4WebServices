package mx.edu.utng.ws;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ActividadMaestoWS {
	private Connection connection;
	private Statement statement;
	private ResultSet resulset;
	private PreparedStatement ps;
	
	public ActividadMaestoWS(){
		conectar();
	}
	
	private void conectar(){
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection(
						"jdbc:postgresql://localhost:5432/wstest",
						"postgres","admin");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int addActMaestro(ActMaestro actMaestro){
		int result = 0;
		if(connection != null){
			try {
				ps = connection.prepareStatement("INSERT INTO actividad_maestro(name, description, activ) VALUES "
						+ "(?,?,?);");
				ps.setString(1, actMaestro.getName());
				ps.setString(2, actMaestro.getDescription());
				ps.setString(3, actMaestro.getActiv());
				result = ps.executeUpdate();
			} catch (SQLException e) {
				
			}
		}
		return result;
	}
	
	public int editActMaestro(ActMaestro actMaestro){
		int result =0;
		if (connection != null) {
			try {
				ps = connection.prepareStatement(
						"UPDATE actividad_maestro SET name =? , "
						+ "description = ? ,"
						+ "activ = ? "
						+ "WHERE id = ?;");
				ps.setString(1, actMaestro.getName());
				ps.setString(2, actMaestro.getDescription());
				ps.setString(3, actMaestro.getActiv());
				ps.setInt(4, actMaestro.getId());
				result = ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public int removeActMaestro(int id){
		int result = 0;
		if(connection != null){
			try {
				ps = connection.prepareStatement("DELETE FROM actividad_maestro WHERE id = ?;");
				ps.setInt(1, id);
				result = ps.executeUpdate();
			} catch (SQLException e) {
				
			}
		}
		return result;
	}
	
	public ActMaestro[] getActMaestros(){
		ActMaestro[] result = null;
		List<ActMaestro> actividades = new ArrayList<ActMaestro>();
		if(connection != null){
			try {
				statement = connection.createStatement();
				resulset = statement.executeQuery(
						"SELECT * FROM actividad_maestro;");
				while(resulset.next()){
					ActMaestro actMaestro = new ActMaestro(
							resulset.getInt("id"),
							resulset.getString("name"),
							resulset.getString("description"),
							resulset.getString("activ"));
					actividades.add(actMaestro);
				}
			} catch (SQLException e) {
				
			}
		}
		result = actividades.toArray(new ActMaestro[actividades.size()]);
		return result;
	}
	
	public ActMaestro getMovieByid(int id){
		ActMaestro actMaestro = null;
		if(connection != null){
			try {
				ps = connection.prepareStatement("SELECT * FROM actividad_maestro WHERE id = ?;");
				ps.setInt(1, id);
				resulset = ps.executeQuery();
				if(resulset.next()){
					actMaestro = new ActMaestro(
							resulset.getInt("id"),
							resulset.getString("name"),
							resulset.getString("description"),
							resulset.getString("activ"));
				}
			} catch (SQLException e) {
				
			}
		}
		return actMaestro;
	}
	
	public static void main(String[] args){
		ActividadMaestoWS ws = new ActividadMaestoWS();
		ActMaestro actMaestro = new ActMaestro(0, "Documentacion", "documentar actividades", "S");
	}
	
}

