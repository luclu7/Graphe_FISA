package AdjacencyMatrix;

import GraphAlgorithms.GraphTools;


public class AdjacencyMatrixUndirectedValuedGraph extends AdjacencyMatrixUndirectedGraph {

	//--------------------------------------------------
	// 				Class variables
	//-------------------------------------------------- 

	// No class variable, we use the matrix variable but with costs values

	//--------------------------------------------------
	// 				Constructors
	//-------------------------------------------------- 

	public AdjacencyMatrixUndirectedValuedGraph(int[][] matrixVal) {
		super(matrixVal);
	}


	
	// ------------------------------------------------
	// 					Methods 
	// ------------------------------------------------	
	
	
	/**
     * adds the edge (x,y,cost). If there is already one initial cost, we replace it.
     */
	public void addEdge(int x, int y, int cost ) {
		this.matrix[x][y] = cost;
		this.matrix[y][x] = cost;
	}
	
	public String toString() {
		StringBuilder s = new StringBuilder("\n Matrix of Costs: \n");
		for (int[] lineCost : this.matrix) {
			for (int i : lineCost) {
				s.append(i).append("\t");
			}
			s.append("\n");
		}
		s.append("\n");
		return s.toString();
	}
	
	
	public static void main(String[] args) {
		int[][] matrixValued = GraphTools.generateValuedGraphData(10, false, true, true, false, 100001);
		AdjacencyMatrixUndirectedValuedGraph am = new AdjacencyMatrixUndirectedValuedGraph(matrixValued);
		System.out.println(am);

		// ajoutons un arc (0,1) de valeur 5
		am.addEdge(0, 1, 5);
		System.out.println(am);
		// ajoutons un arc (0,1) de valeur 10
		am.addEdge(0, 1, 10);
		// ça devrait avoir overwrite
		System.out.println(am);

		am.addEdge(1, 0, 50);
		System.out.println(am);
	}

}
