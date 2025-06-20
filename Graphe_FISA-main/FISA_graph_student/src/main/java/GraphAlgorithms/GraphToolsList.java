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
import Nodes_Edges.Arc;
import Visualizer.DirectedGraphVisualizer;

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
	public List<DirectedNode> BFS(AdjacencyListDirectedGraph graph) {
		boolean[] visited = new boolean[graph.getNbNodes()];
		Arrays.fill(visited, false);
		Queue<DirectedNode> fifo = new LinkedList<>();
		List<DirectedNode> nodes = new ArrayList<>();

		DirectedNode s = graph.getNodes().get(5);
		fifo.add(s);
		visited[s.getLabel()] = true;

		while (!fifo.isEmpty()) {
			s = fifo.poll();
			nodes.add(s);
			for (Arc arc : s.getArcSucc()) {
				s = arc.getSecondNode();
				if (!visited[s.getLabel()]) {
					visited[s.getLabel()] = true;
					fifo.add(s);
				}
			}
		}
		return nodes;
	}

	public void explorerSommet(DirectedNode sommet, List<DirectedNode> atteints) {
		atteints.add(sommet);
		for (Arc arc : sommet.getArcSucc()) {
			DirectedNode voisin = arc.getSecondNode();
			if (!atteints.contains(voisin)) {
				explorerSommet(voisin, atteints);
			}
		}
	}

	public List<DirectedNode> explorerGraphe(AdjacencyListDirectedGraph graph) {
		List<DirectedNode> atteints = new ArrayList<DirectedNode>();
		DirectedNode sommet = graph.getNodes().get(5);
		atteints.add(sommet);
		for (Arc arc : sommet.getArcSucc()) {
			DirectedNode voisin = arc.getSecondNode();
			if (!atteints.contains(voisin)) {
				explorerSommet(voisin, atteints);
			}
		}
		return atteints;
	}

	public static void main(String[] args) {
		int[][] Matrix = GraphTools.generateGraphData(10, 20, false, false, true, 100002);
		//GraphTools.afficherMatrix(Matrix);
		AdjacencyListDirectedGraph al = new AdjacencyListDirectedGraph(Matrix);
		//System.out.println(al);

		// A completer
		GraphToolsList gtl = new GraphToolsList();
		System.out.println("BFS on the graph: ");
		for (DirectedNode n : gtl.BFS(al)) {
			System.out.print(n+" ");
		}
		System.out.println("\nExpected: \nn_0 n_4 n_2 n_6 n_9 n_5 n_3 n_8 n_1");

		System.out.println("\nDFS on the graph: ");
		for (DirectedNode n : gtl.explorerGraphe(al)) {
			System.out.print(n+" ");
		}
		System.out.println("\nExpected: \nn_0 n_4 n_2 n_6 n_9 n_5 n_3 n_8 n_1");
		DirectedGraphVisualizer am = new DirectedGraphVisualizer(Matrix);
		am.display("Directed Graph");
	}
}
