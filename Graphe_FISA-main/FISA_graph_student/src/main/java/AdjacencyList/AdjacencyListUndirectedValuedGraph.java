package AdjacencyList;

import java.util.ArrayList;

import GraphAlgorithms.GraphTools;
import Nodes_Edges.Edge;
import Nodes_Edges.UndirectedNode;

public class AdjacencyListUndirectedValuedGraph extends AdjacencyListUndirectedGraph{

	//--------------------------------------------------
    // 				Constructors
    //--------------------------------------------------

    public AdjacencyListUndirectedValuedGraph(int[][] matrixVal) {
    	super();
    	this.nbNodes = matrixVal.length;
        
        for (int i = 0; i < this.nbNodes; i++) {
            this.nodes.add(new UndirectedNode(i));            
        }
        for (UndirectedNode n1 : this.getNodes()) {
            for (int j = n1.getLabel(); j < matrixVal[n1.getLabel()].length; j++) {
            	UndirectedNode n2 = this.getNodes().get(j);
                if (matrixVal[n1.getLabel()][j] != 0) {
                	Edge e1 = new Edge(n1,n2,matrixVal[n1.getLabel()][j]);
                    n1.addEdge(e1);
                    this.edges.add(e1);
                	n2.addEdge(new Edge(n2,n1,matrixVal[n1.getLabel()][j]));
                    this.nbEdges++;
                }
            }
        }
    }

    //--------------------------------------------------
    // 				Methods
    //--------------------------------------------------
    

    /**
     * Adds the edge (from,to) with cost if it is not already present in the graph.
     * And adds this edge to the incident list of both extremities (nodes) and into the global list "edges" of the graph.
     */
    public void addEdge(UndirectedNode x, UndirectedNode y, int cost) {
    	if (!this.edges.contains(new Edge(x, y))) {
            Edge e1 = new Edge(x, y, cost);
            x.addEdge(e1);
            y.addEdge(new Edge(y, x, cost));
            this.edges.add(e1);
            this.nbEdges++;
        }
    }
    
    
    
    public static void main(String[] args) {
        int[][] matrixValued = GraphTools.generateValuedGraphData(10, false, true, true, false, 100001);
        GraphTools.afficherMatrix(matrixValued);
        AdjacencyListUndirectedValuedGraph al = new AdjacencyListUndirectedValuedGraph(matrixValued);
        System.out.println(al);
        System.out.println("Does edge (n_5,n_6) exist ? "+ al.getEdges().contains(new Edge(al.getNodes().get(6),al.getNodes().get(5))));
        // ajoutons une arête (0,1) de valeur 5
        al.addEdge(al.getNodes().get(0), al.getNodes().get(1), 5);
        System.out.println(al);

        // ajoutons une arête (5,2) de valeur 10
        al.addEdge(al.getNodes().get(5), al.getNodes().get(2), 10);
        System.out.println(al);

    }
}
