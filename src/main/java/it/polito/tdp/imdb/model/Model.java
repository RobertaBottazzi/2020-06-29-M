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
	private List<Director> percorsoMassimo;
	
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
	
	public List<Director> trovaPercorso(int attoriCondivisi, Director director){
		this.percorsoMassimo = new ArrayList<>();
		List<Director> parziale=new ArrayList<>();
		int sommaPesi=0;
		parziale.add(director);
		cerca(attoriCondivisi, parziale, sommaPesi);
		return percorsoMassimo;
	}
	
	private void cerca(int attoriCondivisi, List<Director> parziale, int sommaPesi) {
		if(sommaPesi>attoriCondivisi)
			return;
		if(parziale.size()>percorsoMassimo.size()) {
			this.percorsoMassimo=new ArrayList<>(parziale);
			//return;
		}
		
		Director ultimo=parziale.get(parziale.size()-1);
		for(Director d: Graphs.neighborListOf(this.grafo, ultimo)) {
			sommaPesi+=this.grafo.getEdgeWeight(this.grafo.getEdge(d, ultimo));
			if(!parziale.contains(d)) {
				parziale.add(d);
				cerca(attoriCondivisi,parziale, sommaPesi);
				parziale.remove(parziale.size()-1);
				sommaPesi-=this.grafo.getEdgeWeight(this.grafo.getEdge(d, ultimo));
			}
		}
	}
	
	public SimpleWeightedGraph<Director, DefaultWeightedEdge> getGrafo(){
		return this.grafo;
	}
}
