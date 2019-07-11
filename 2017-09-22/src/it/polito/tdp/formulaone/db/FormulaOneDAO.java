package it.polito.tdp.formulaone.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import it.polito.tdp.formulaone.model.Adiacenza;
import it.polito.tdp.formulaone.model.Race;
import it.polito.tdp.formulaone.model.Season;

public class FormulaOneDAO {

	public List<Season> getAllSeasons() {
		String sql = "SELECT year, url FROM seasons ORDER BY year";
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			List<Season> list = new ArrayList<>();
			while (rs.next()) {
				list.add(new Season(rs.getInt("year"), rs.getString("url")));
			}
			conn.close();
			return list;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Integer> getAllYears() {
		String sql = "SELECT year FROM seasons ORDER BY year";
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			List<Integer> list = new ArrayList<>();
			while (rs.next()) {
				list.add(rs.getInt("year"));
			}
			conn.close();
			return list;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Race> getRaces(Integer anno) {
		String sql = "SELECT * " + 
				"FROM races " + 
				"WHERE races.YEAR=? ";
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			
			ResultSet rs = st.executeQuery();
			List<Race> list = new ArrayList<>();
			while (rs.next()) {
				
				Race r= new Race(rs.getInt("raceId"), rs.getInt("year"), rs.getInt("circuitId"),rs.getString("name"));
				
				
				list.add(r);
			}
			conn.close();
			return list;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Adiacenza> getEdges(Integer anno) {
		String sql = "SELECT r1.raceId, r2.raceId, COUNT(distinct r1.driverId) as cnt " + 
				"FROM results r1, results r2, races ra, races ra2 " + 
				"WHERE ra.YEAR= ? " + 
				"AND ra2.year=ra.year " + 
				"AND r1.raceId= ra.raceId " + 
				"AND r2.raceId=ra2.raceId " + 
				"AND ra.raceId != ra2.raceId " + 
				"AND r1.driverId= r2.driverId " + 
				"AND r1.statusId=1 " + 
				"AND r2.statusId=1 " + 
				"GROUP BY r1.raceId, r2.raceId ";
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			
			ResultSet rs = st.executeQuery();
			List<Adiacenza> list = new ArrayList<>();
			while (rs.next()) {
				
				Adiacenza a= new Adiacenza(rs.getInt("r1.raceId"), rs.getInt("r2.raceId"), rs.getInt("cnt"));
				list.add(a);
			}
			conn.close();
			return list;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	

}
