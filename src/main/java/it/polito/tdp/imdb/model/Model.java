package it.polito.tdp.imdb.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.imdb.db.ImdbDAO;

public class Model {
	
	private SimpleWeightedGraph<Director, DefaultWeightedEdge> grafo;
	private Map<Integer, Director> idMap;
	private ImdbDAO dao;
	
	public Model() {
		this.dao= new ImdbDAO();
		this.idMap= new HashMap<>();
		dao.loadAllDirectors(idMap);
	}
	
	public void creaGrafo(int anno) {
		this.grafo= new SimpleWeightedGraph<Director, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		Graphs.addAllVertices(this.grafo, dao.getAllDirectorsByYear(anno, idMap));
		for(Arco a: dao.getAllDirectorsByYearAndActors(anno, idMap)) {
			Graphs.addEdgeWithVertices(this.grafo, a.getD1(), a.getD2(), a.getPeso());
		}
	}
	
	public List<RegistiAdiacenti> getRegistiAdiacenti(Director director){
		List<RegistiAdiacenti> result= new ArrayList<>();
		for(Director d: Graphs.neighborListOf(this.grafo, director)) {
			result.add(new RegistiAdiacenti(idMap.get(d.getId()), (int) this.grafo.getEdgeWeight(this.grafo.getEdge(director, idMap.get(d.getId())))));
		}
		result.sort(Comparator.comparing(RegistiAdiacenti:: getPeso).reversed());
		return result;
	}
	
	public SimpleWeightedGraph<Director, DefaultWeightedEdge> getGrafo(){
		return this.grafo;
	}
}
