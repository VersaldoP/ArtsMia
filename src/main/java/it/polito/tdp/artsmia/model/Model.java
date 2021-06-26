package it.polito.tdp.artsmia.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.traverse.DepthFirstIterator;

import it.polito.tdp.artsmia.db.ArtsmiaDAO;

public class Model {

private ArtsmiaDAO dao;
private Graph<ArtObject,DefaultWeightedEdge> grafo;
private Map<Integer,ArtObject> idMap;
private List<Adiacenza> archi;
private Set<ArtObject> compConn;

public Model() {
	dao = new ArtsmiaDAO();
}

public void creaGrafo() {
	grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
	idMap = new HashMap<>();
	
	dao.listObjects(idMap);
	
	// inserisco i vertici
	Graphs.addAllVertices(this.grafo, idMap.values());
	System.out.println("Vertici creati "+this.grafo.vertexSet().size());
	
	// creo gli archi
	archi = new ArrayList<>(dao.listAdiacenze(idMap));
	
	for(Adiacenza a :archi) {
		Graphs.addEdge(this.grafo, a.getObj1(), a.getObj2(), a.getPeso());
	}
	
	System.out.println("Archi creati "+this.grafo.edgeSet().size());
	
	
}

public String getNvertex() {
	return "#Vertici ="+this.grafo.vertexSet().size();
}
public String getNedge() {
	return "#Vertici ="+this.grafo.edgeSet().size();
}
public String check(int check) {
	if(idMap.containsKey(check))
		return"Trovato";
	else
		return "Non trovato, riprova";
}

public String trovaComponenteConnessa(int s) {
	ArtObject start = idMap.get(s);
	DepthFirstIterator<ArtObject,DefaultWeightedEdge> dfv = new DepthFirstIterator<>(this.grafo,start);
	compConn = new HashSet<>();
	while(dfv.hasNext()) {
		ArtObject o = dfv.next();
		compConn.add(o);
	}
	return"la dimensione della Componente Connessa ="+compConn.size()+"\n"+compConn.toString();
	
	
}
public int compConnN() {
	return compConn.size();
}





}
