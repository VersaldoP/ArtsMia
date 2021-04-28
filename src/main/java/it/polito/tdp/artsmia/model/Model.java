package it.polito.tdp.artsmia.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.artsmia.db.ArtsmiaDAO;

public class Model {

	//dobbiamo creare un grafo orientato pesato, come veritici usiamo gli Oggetti della Mostra
	private Graph<ArtObject,DefaultWeightedEdge> grafo;
	private ArtsmiaDAO dao;
	private Map<Integer,ArtObject> idMap;
	
	//se dobbiamo crare + gravi è meglio fare un void creaGrafo perchè posso ricrearlo idipendemte dal model
	public Model() {
		dao = new ArtsmiaDAO();
		idMap = new HashMap<Integer,ArtObject>();
		
	}
	public void creaGrafo() {
		grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		//Aggiungere i vertici( potrei avere degli oggetti specifici e tal caso mi verrebbe passato come parametro
		// 1. -> recupero tutti gli art OBject
		//2.-> li inserisco come vertici
		dao.listObjects(idMap);
		Graphs.addAllVertices(grafo,idMap.values());
		
		
		//Aggiungere gli Archi
		//Appoccio 1 (+ semplice ma più lungo) troppo lungo non ottengo nulla a meno che non abbia circa 600 giorni quindi dobbiamo ottimizzarlo
		//-> doppio ciclio for sui vertici 
		//-> dati due vertici, controllo se sono collegati
		/*
		for(ArtObject a1 :this.grafo.vertexSet()) {
			for(ArtObject a2 :this.grafo.vertexSet()) {
				if(!a1.equals(a2)&& !this.grafo.containsEdge(a1,a2)) {
					// devo collegare a1 ad a2 ?
					int peso = dao.getPeso(a1,a2);
					if(peso>0)
					Graphs.addEdge(this.grafo,a1,a2,peso);
				}
			}
		}
		*/
		// anche l'approccio 2 è troppo lungo ci mette circa 35 minuti
		// esso consiste nel fare un unico ciclo for e interrogo il db con 
//		SELECT e1.object_id COUNT(*) AS peso
//		FROM exhibition_objects e1,exhibition_objects e2
//		WHERE e1.exhibition_id=e2.exhibition_id
//		AND  e1.object_id=8485
//		AND e2.object_id!=e1.object_id
//		GROUP BY e2.object_id
		
		// approccio 3
		for(Adiacenza a :dao.getAdiacenze()) {
			Graphs.addEdge(this.grafo,idMap.get(a.getId1()),idMap.get(a.getId2()),a.getPeso());
			
		}
		
//		System.out.println("GRAFO CREATO");
//		System.out.println("# VERTICI: "+ grafo.vertexSet().size());
//		System.out.println("#ARCHI: "+grafo.edgeSet().size());
	}
	public String getNVertici() {
		// TODO Auto-generated method stub
		return ""+grafo.vertexSet().size();
	}
	public String getNArchi() {
		// TODO Auto-generated method stub
		return ""+grafo.edgeSet().size();
	}
	
}
