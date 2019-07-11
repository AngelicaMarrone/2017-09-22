package it.polito.tdp.formulaone.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.formulaone.db.FormulaOneDAO;

public class Model {
	
	FormulaOneDAO dao= new FormulaOneDAO();
	SimpleWeightedGraph<Race, DefaultWeightedEdge> graph;
	private List<Race> vertex;
	private List<Adiacenza> edges;
	private Map<Integer, Race> idMap;
	private List<Adiacenza> best;
	int peso= 0;
	
	

	public Model() {
		super();
		idMap = new HashMap<Integer, Race>();
	}

	public List<Integer> getSeasons() {
		
		List<Integer> years= new ArrayList<Integer>(dao.getAllYears());
		
		
		return years;
	}

	public List<Adiacenza> creaGrafo(Integer anno) {
		
		graph= new SimpleWeightedGraph<Race, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		vertex= new ArrayList<Race>(dao.getRaces(anno));
		
		for (Race v: vertex)
		{
			idMap.put(v.getRaceId(), v);
		}
		
		Graphs.addAllVertices(this.graph, vertex);
		
		edges = new ArrayList<Adiacenza>(dao.getEdges(anno));

		

		for(Adiacenza a : edges) {


			Race source = idMap.get(a.getId1());

			Race target = idMap.get(a.getId2());

			double peso = a.getPeso();

			Graphs.addEdge(graph,source,target,peso);

			System.out.println("AGGIUNTO ARCO TRA: "+source.toString()+" e "+target.toString());
			
			
			

		}

		

		System.out.println("#vertici: " + graph.vertexSet().size());

		System.out.println("#archi: " + graph.edgeSet().size());
		
		
		best= trovaMigliori();
		
		return best;


		

	}

	private List<Adiacenza> trovaMigliori() {
		
		int temp = 0;

		List<Adiacenza> result = new LinkedList<Adiacenza>();

		for(DefaultWeightedEdge d: this.graph.edgeSet()) {

			if(graph.getEdgeWeight(d)>temp)

				temp=(int) graph.getEdgeWeight(d);

		}

		for(DefaultWeightedEdge d: this.graph.edgeSet()) {

			if(graph.getEdgeWeight(d)==temp) {

				result.add(new Adiacenza(graph.getEdgeSource(d).getRaceId(), graph.getEdgeTarget(d).getRaceId(),(int)(graph.getEdgeWeight(d))));

				

			}

		}

	return result;
	}
		
		
		
		
		
	}


