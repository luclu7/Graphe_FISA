package AdjacencyMatrix;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import AdjacencyList.AdjacencyListUndirectedGraph;
import GraphAlgorithms.BinaryHeapEdge;
import GraphAlgorithms.GraphTools;
import Nodes_Edges.Edge;
import Nodes_Edges.UndirectedNode;
import javafx.scene.effect.Effect;

/**
 * This class represents the undirected graphs structured by an adjacency matrix.
 * We consider only simple graph
 */
public class AdjacencyMatrixUndirectedGraph {
	
	//--------------------------------------------------
    // 				Class variables
    //--------------------------------------------------

    protected int nbNodes;		// Number of vertices
    protected int nbEdges;		// Number of edges/arcs
    protected int[][] matrix;	// The adjacency matrix

  
   
	
	//--------------------------------------------------
	// 				Constructors
	//-------------------------------------------------- 
	
	public AdjacencyMatrixUndirectedGraph() {
		this.matrix = new int[0][0];
        this.nbNodes = 0;
        this.nbEdges = 0;
	}
	
	public AdjacencyMatrixUndirectedGraph(int[][] mat) {
		this.nbNodes=mat.length;
		this.nbEdges = 0;
		this.matrix = new int[this.nbNodes][this.nbNodes];
		for(int i = 0; i<this.nbNodes; i++){
			for(int j = i; j<this.nbNodes; j++){
				this.matrix[i][j] = mat[i][j];
				this.matrix[j][i] = mat[i][j];
				this.nbEdges += mat[i][j];
			}
		}	
	}
	
	public AdjacencyMatrixUndirectedGraph(AdjacencyListUndirectedGraph g) {
		this.nbNodes = g.getNbNodes(); 				
		this.nbEdges = g.getNbEdges(); 				
		this.matrix = g.toAdjacencyMatrix(); 
	}

	//--------------------------------------------------
	// 					Accessors
	//--------------------------------------------------

	/**
     * @return the matrix modeling the graph
     */
    public int[][] getMatrix() {
        return this.matrix;
    }

    /**
     * @return the number of nodes in the graph (referred to as the order of the graph)
     */
    public int getNbNodes() {
        return this.nbNodes;
    }
	
    /**
	 * @return the number of edges in the graph
 	 */	
	public int getNbEdges() {
		return this.nbEdges;
	}

	/**
	 * 
	 * @param v the vertex selected
	 * @return a list of vertices which are the neighbours of x
	 */
	public List<Integer> getNeighbours(int v) {
		List<Integer> l = new ArrayList<>();
		for(int i = 0; i<matrix[v].length; i++){
			if(matrix[v][i]>0){
				l.add(i);
			}
		}
		return l;
	}
	
	// ------------------------------------------------
	// 					Methods 
	// ------------------------------------------------		
	
	/**
     	* @return true if the edge is in the graph.
     	*/
	public boolean isEdge(int x, int y) {
		if (x < 0 || x >= nbNodes || y < 0 || y >= nbNodes) {
			return false;
		}
		return this.matrix[x][y] > 0;
	}
	
	/**
     	* removes the edge (x,y) if there exists one between these nodes in the graph.
    	 */
	public void removeEdge(int x, int y) {
		if (x < 0 || x >= nbNodes || y < 0 || y >= nbNodes) {
			return;
		}
		this.matrix[x][y] = 0;
		this.matrix[y][x] = 0;
		this.nbEdges--;
	}

	/**
     	* adds the edge (x,y) if there is not already one.
     	*/
	public void addEdge(int x, int y) {
		if (x < 0 || x >= nbNodes || y < 0 || y >= nbNodes) {
			return;
		}
		if(this.matrix[x][y] == 0){
			this.matrix[x][y] = 1;
			this.matrix[y][x] = 1;
			this.nbEdges++;
		}
	}

	
	/**
    	* @return the adjacency matrix representation int[][] of the graph
    	*/
	public int[][] toAdjacencyMatrix() {
		return this.matrix;
	}
	
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder("\nAdjacency Matrix: \n");
		for (int[] ints : this.matrix) {
			for (int anInt : ints) {
				s.append(anInt).append("\t");
			}
			s.append("\n");
		}
		s.append("\n");
		return s.toString();
	}

	public static List<Edge> prim(AdjacencyListUndirectedGraph graph, UndirectedNode start) {
		Set<UndirectedNode> visited = new HashSet<>();
		List<Edge> mst = new ArrayList<>();
		BinaryHeapEdge heap = new BinaryHeapEdge();
		visited.add(start);
		for(Edge arete : graph.getEdges()) {
			heap.insert(arete.getFirstNode(), arete.getSecondNode(), arete.getWeight());
		}
		while (!heap.isEmpty()) {
			Edge minEdge = heap.remove();
			UndirectedNode n1 = minEdge.getFirstNode();
			UndirectedNode n2 = minEdge.getSecondNode();
			if (visited.contains(n1) && visited.contains(n2)) continue;
			UndirectedNode newNode = visited.contains(n1) ? n2 : n1;
			mst.add(minEdge);
			visited.add(newNode);
			for (Edge arete : graph.getEdges()) {
				UndirectedNode neighbor;
				if (arete.getFirstNode().equals(start)) {
					neighbor = arete.getFirstNode();
				} else if (arete.getSecondNode().equals(start)) {
					neighbor = arete.getSecondNode();
				} else {
					continue;
				}
				if (!visited.contains(neighbor)) {
					heap.insert(arete.getFirstNode(), arete.getSecondNode(), arete.getWeight());
				}
			}
		}
		return mst;
	}

	public static void main(String[] args) {
		int[][] mat2 = GraphTools.generateGraphData(10, 35, false, true, false, 100001);
		AdjacencyMatrixUndirectedGraph am = new AdjacencyMatrixUndirectedGraph(mat2);
		System.out.println(am);
		System.out.println("n = " + am.getNbNodes() + "\nm = " + am.getNbEdges() + "\n");

		// Neighbours of vertex 2 :
		System.out.println("Neighbours of vertex 2 : ");
		List<Integer> t2 = am.getNeighbours(2);
		for (Integer integer : t2) {
			System.out.print(integer + ", ");
		}

		// We add three edges {3,5} :
		System.out.println("\n\nisEdge(3, 5) ? " + am.isEdge(3, 5));
		for (int i = 0; i < 3; i++)
			am.addEdge(3, 5);

		System.out.println("\n" + am);

		System.out.println("\nAfter removing one edge {3,5} :");
		am.removeEdge(3, 5);
		System.out.println(am);
		// A completer
		int x = 8;
		int y = 9;
		System.out.println("======== Tests des méthodes ========");
		System.out.println("isEdge(" + x + ", " + y + ") ? " + am.isEdge(x, y) + " (nb d'edges: " + am.getNbEdges() + ")");
		System.out.println("addEdge(" + x + ", " + y + ")");
		am.addEdge(x, y);
		System.out.println("isEdge(" + x + ", " + y + ") ? " + am.isEdge(x, y) + " (nb d'edges: " + am.getNbEdges() + ")");
		;
		System.out.println("removeEdge(" + x + ", " + y + ")");
		am.removeEdge(x, y);
		System.out.println("isEdge(" + x + ", " + y + ") ? " + am.isEdge(x, y) + " (nb d'edges: " + am.getNbEdges() + ")");
		;


		AdjacencyListUndirectedGraph graph = new AdjacencyListUndirectedGraph(mat2);
		List<Edge> mst = prim(graph, graph.getNodes().get(0)); // Replace 'YourClass' with actual class name

		// Print the result
		System.out.println("MST Edges:");
		for (Edge e : mst) {
			System.out.println(e.getFirstNode().getLabel() + " -- " + e.getWeight() + " -- " + e.getSecondNode().getLabel());
		}

		// Optionally verify total weight
		int totalWeight = mst.stream().mapToInt(Edge::getWeight).sum();
		System.out.println("Total MST Weight: " + totalWeight);
	}
}
