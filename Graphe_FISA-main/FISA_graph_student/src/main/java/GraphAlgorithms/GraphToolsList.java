package GraphAlgorithms;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import AdjacencyList.AdjacencyListDirectedGraph;
import AdjacencyList.AdjacencyListDirectedValuedGraph;
import AdjacencyList.AdjacencyListUndirectedValuedGraph;
import AdjacencyMatrix.AdjacencyMatrixDirectedGraph;
import Collection.Triple;
import Nodes_Edges.DirectedNode;
import Nodes_Edges.UndirectedNode;

public class GraphToolsList  extends GraphTools {

	private static int _DEBBUG =0;

	private static int[] visite;
	private static int[] debut;
	private static int[] fin;
	private static List<Integer> order_CC;
	private static int cpt=0;

	//--------------------------------------------------
	// 				Constructors
	//--------------------------------------------------

	public GraphToolsList(){
		super();
	}

	// ------------------------------------------
	// 				Accessors
	// ------------------------------------------



	// ------------------------------------------
	// 				Methods
	// ------------------------------------------

	// A completer
	public static List<Integer> BFS(AdjacencyListDirectedGraph graph) {
		boolean[] visited = new boolean[graph.getNbNodes()];
		Arrays.fill(visited, false);
		Queue<Integer> fifo = new LinkedList<>();
		List<Integer> nodes = new ArrayList<>();

		int s = 0;
		fifo.add(s);
		visited[s] = true;

		while (!fifo.isEmpty()) {
			s = fifo.poll();
			nodes.add(s);
			AdjacencyMatrixDirectedGraph matrix = new AdjacencyMatrixDirectedGraph(graph.toAdjacencyMatrix());
			for (Integer i : matrix.getPredecessors(s)){
				if (!visited[i]) {
					visited[i] = true;
					fifo.add(i);
				}
			}
		}
		return nodes;
	}

	public static List<Integer> explorerGraphe(AdjacencyListDirectedGraph graph) {
		return new ArrayList<Integer>();
	}

	public static void main(String[] args) {
		int[][] Matrix = GraphTools.generateGraphData(10, 20, false, false, true, 100001);
		//GraphTools.afficherMatrix(Matrix);
		AdjacencyListDirectedGraph al = new AdjacencyListDirectedGraph(Matrix);
		//System.out.println(al);

		// A completer
		List<Integer> result = BFS(al);
		String resultString = "";
		for (Integer s : result) {
			resultString += s + " ";
		}
		System.out.println(resultString);
	}
}
