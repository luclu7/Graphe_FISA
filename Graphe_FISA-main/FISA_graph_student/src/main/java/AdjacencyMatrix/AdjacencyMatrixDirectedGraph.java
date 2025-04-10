package AdjacencyMatrix;


import GraphAlgorithms.GraphTools;
import Nodes_Edges.AbstractNode;
import Nodes_Edges.DirectedNode;

import java.util.ArrayList;
import java.util.List;

import AdjacencyList.AdjacencyListDirectedGraph;
import Visualizer.DirectedGraphVisualizer;

/**
 * This class represents the directed graphs structured by an adjacency matrix.
 * We consider only simple graph
 */
public class AdjacencyMatrixDirectedGraph {

	//--------------------------------------------------
    // 				Class variables
    //--------------------------------------------------

    protected int nbNodes;		// Number of vertices
    protected int nbArcs;		// Number of edges/arcs
    protected int[][] matrix;	// The adjacency matrix
	
	//--------------------------------------------------
	// 				Constructors
	//-------------------------------------------------- 

    public AdjacencyMatrixDirectedGraph() {
        this.matrix = new int[0][0];
        this.nbNodes = 0;
        this.nbArcs = 0;
    }

      
	public AdjacencyMatrixDirectedGraph(int[][] mat) {
		this.nbNodes = mat.length;
		this.nbArcs = 0;
		this.matrix = new int[this.nbNodes][this.nbNodes];
		for(int i = 0; i<this.nbNodes; i++){
			for(int j = 0; j<this.nbNodes; j++){
				this.matrix[i][j] = mat[i][j];
				this.nbArcs += mat[i][j];
			}
		}
	}

	public AdjacencyMatrixDirectedGraph(AdjacencyListDirectedGraph g) {
		this.nbNodes = g.getNbNodes();
		this.nbArcs = g.getNbArcs();
		this.matrix = g.toAdjacencyMatrix();
	}

	//--------------------------------------------------
	// 					Accessors
	//--------------------------------------------------


    /**
     * Returns the matrix modeling the graph
     */
    public int[][] getMatrix() {
        return this.matrix;
    }

    /**
     * Returns the number of nodes in the graph (referred to as the order of the graph)
     */
    public int getNbNodes() {
        return this.nbNodes;
    }
	
    /**
	 * @return the number of arcs in the graph
 	 */	
	public int getNbArcs() {
		return this.nbArcs;
	}

	
	/**
	 * @param u the vertex selected
	 * @return a list of vertices which are the successors of u
	 */
	public List<Integer> getSuccessors(int u) {
		List<Integer> succ = new ArrayList<Integer>();
		for(int v =0;v<this.matrix[u].length;v++){
			if(this.matrix[u][v]>0){
				succ.add(v);
			}
		}
		return succ;
	}

	/**
	 * @param v the vertex selected
	 * @return a list of vertices which are the predecessors of v
	 */
	public List<Integer> getPredecessors(int v) {
		List<Integer> pred = new ArrayList<Integer>();
		for(int u =0;u<this.matrix.length;u++){
			if(this.matrix[u][v]>0){
				pred.add(u);
			}
		}
		return pred;
	}
	
	
	// ------------------------------------------------
	// 					Methods 
	// ------------------------------------------------		
	
	/**
	 * @return true if the arc (from,to) exists in the graph.
 	 */
	public boolean isArc(int from, int to) {
		if (from < 0 || from >= nbNodes || to < 0 || to >= nbNodes) {
			return false;
		}
		return matrix[from][to] > 0;
	}

	/**
	 * removes the arc (from,to) if there exists one between these nodes in the graph.
	 */
	public void removeArc(int from, int to) {
		if (from < 0 || from >= nbNodes || to < 0 || to >= nbNodes) {
			return;
		}
		if (matrix[from][to] > 0) {
			nbArcs --;
			matrix[from][to] = 0;
		}
	}

	/**
	 * Adds the arc (from,to). 
	 */
	public void addArc(int from, int to) {
		if (from < 0 || from >= nbNodes || to < 0 || to >= nbNodes) {
			return;
		}
		if (matrix[from][to] == 0) {
			matrix[from][to] = 1;
			nbArcs++;
		}
	}

	/**
	 * @return a new graph which is the inverse graph of this.matrix
 	 */
	public AdjacencyMatrixDirectedGraph computeInverse() {
		int[][] inverseMatrix = new int[nbNodes][nbNodes];
		for (int i = 0; i < nbNodes; i++) {
			for (int j = 0; j < nbNodes; j++) {
				inverseMatrix[j][i] = matrix[i][j];
			}
		}
		return new AdjacencyMatrixDirectedGraph(inverseMatrix);
	}

	@Override
	public String toString(){
		StringBuilder s = new StringBuilder("Adjacency Matrix: \n");
		for (int[] ints : matrix) {
			for (int anInt : ints) {
				s.append(anInt).append("\t");
			}
			s.append("\n");
		}
		s.append("\n");
		return s.toString();
	}

	public static void main(String[] args) throws InterruptedException {
		int[][] matrix2 = GraphTools.generateGraphData(10, 20, false, false, false, 100001);
		AdjacencyMatrixDirectedGraph am = new AdjacencyMatrixDirectedGraph(matrix2);
		System.out.println(am);
		System.out.println("n = "+am.getNbNodes()+ "\nm = "+am.getNbArcs() +"\n");
		
		// Successors of vertex 1 :
		System.out.println("Sucesssors of vertex 1 : ");
		List<Integer> t = am.getSuccessors(1);
		for (Integer integer : t) {
			System.out.print(integer + ", ");
		}
		
		// Predecessors of vertex 2 :
		System.out.println("\n\nPredecessors of vertex 2 : ");
		List<Integer> t2 = am.getPredecessors(2);
		for (Integer integer : t2) {
			System.out.print(integer + ", ");
			}

		System.out.println("\n\n--- Tests des méthodes ajoutées ---");

		// Test isArc
		int nodeFrom = 3;
		int nodeTo = 5;

		System.out.println("isArc(" + nodeFrom + ", " + nodeTo + ") ? " + am.isArc(nodeFrom, nodeTo) + " (nb d'arcs: " + am.getNbArcs() + ")");

		// Test addArc
		System.out.println("addArc(" + nodeFrom + ", " + nodeTo + ")");
		am.addArc(nodeFrom, nodeTo);
		System.out.println("isArc(" + nodeFrom + ", " + nodeTo + ") ? " + am.isArc(nodeFrom, nodeTo) + " (nb d'arcs: " + am.getNbArcs() + ")");

		// Test removeArc
		System.out.println("removeArc(" + nodeFrom + ", " + nodeTo + ")");
		am.removeArc(nodeFrom, nodeTo);
		System.out.println("isArc(" + nodeFrom + ", " + nodeTo + ") ? " + am.isArc(nodeFrom, nodeTo) + " (nb d'arcs: " + am.getNbArcs() + ")");

		// test matrix inverse
		System.out.println("\n\n--- Test de la matrice inverse ---");
		AdjacencyMatrixDirectedGraph inverseGraph = am.computeInverse();
		System.out.println("Matrice inverse : \n" + inverseGraph);

		if (am.getNbArcs() == inverseGraph.getNbArcs()) {
			System.out.println("Le nombre d'arcs est le même dans les deux graphes");
		} else {
			System.out.println("Le nombre d'arcs est différent dans les deux graphes?");
		}

		// test getSuccessors
		System.out.println("\n\n--- Test de la méthode getSuccessors ---");
		// get successors of node 2
		int node = 2;
		System.out.println("Successeurs du noeud " + node + " : ");
		List<Integer> successors = inverseGraph.getSuccessors(node);
		for (Integer successor : successors) {
			System.out.print(successor + " ");
		}

		if (successors.equals(am.getPredecessors(node))) {
			System.out.println("\nLes successeurs du noeud " + node + " dans le graphe original sont les prédécesseurs dans le graphe inverse: c'est bon !");
		} else {
			System.out.println("\nPas normal: Les successeurs du noeud " + node + " dans le graphe original ne sont pas les prédécesseurs dans le graphe inverse.");
		}


		// visu !
		System.out.println("\n\n--- Test avec visualiation ---");
		DirectedGraphVisualizer visualizer = new DirectedGraphVisualizer(am.getMatrix());
		visualizer.display();

		Thread.sleep(3000);
		// on supprime un arc
		System.out.println("addArc("+ nodeFrom +", "+ nodeTo +")");
		am.removeArc(nodeFrom, nodeTo);
		visualizer.setGraph(am.getMatrix());


	}
}
