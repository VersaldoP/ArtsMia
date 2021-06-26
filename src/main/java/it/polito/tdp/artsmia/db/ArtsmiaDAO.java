package it.polito.tdp.artsmia.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.artsmia.model.Adiacenza;
import it.polito.tdp.artsmia.model.ArtObject;

public class ArtsmiaDAO {

	public void listObjects(Map<Integer,ArtObject>map) {
		
		String sql = "SELECT * from objects";
		List<ArtObject> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {
                    if (!map.containsKey(res.getInt("object_id"))) {
				ArtObject artObj = new ArtObject(res.getInt("object_id"), res.getString("classification"), res.getString("continent"), 
						res.getString("country"), res.getInt("curator_approved"), res.getString("dated"), res.getString("department"), 
						res.getString("medium"), res.getString("nationality"), res.getString("object_name"), res.getInt("restricted"), 
						res.getString("rights_type"), res.getString("role"), res.getString("room"), res.getString("style"), res.getString("title"));
				
                    map.put(artObj.getId(),artObj);
                    }
                    
				
			}
			conn.close();
			return ;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return ;
		}
	}
  
 
   public List<Adiacenza> listAdiacenze(Map<Integer,ArtObject>map) {
		
	   String sql = "SELECT distinct e1.object_id as obj1,e2.object_id as obj2,COUNT(DISTINCT e1.exhibition_id)as peso "
		   		+ "FROM exhibition_objects e1, exhibition_objects e2 "
		   		+ "WHERE e1.exhibition_id=e2.exhibition_id "
		   		+ "AND e1.object_id<>e2.object_id "
		   		+ "GROUP BY (e1.object_id) "
		   		+ "HAVING COUNT(DISTINCT e1.exhibition_id)>0";
		List<Adiacenza> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {
                  Adiacenza a = new Adiacenza (map.get(res.getInt("obj1")),map.get(res.getInt("obj2")),res.getInt("peso"));
                  result.add(a);
				
				
                   }
                   
			
			
		
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return  null;
		}
	}
}
