package AdjacencyList;

import GraphAlgorithms.GraphTools;
import Nodes_Edges.Arc;
import Nodes_Edges.DirectedNode;

public class AdjacencyListDirectedValuedGraph extends AdjacencyListDirectedGraph {

	//--------------------------------------------------
    // 				Constructors
    //--------------------------------------------------

	public AdjacencyListDirectedValuedGraph(int[][] matrixVal) {
    	super();
    	this.nbNodes = matrixVal.length;
        for (int i = 0; i < this.nbNodes; i++) {
            this.nodes.add(new DirectedNode(i));
        }
        for (DirectedNode n1 : this.getNodes()) {
            for (int j = 0; j < matrixVal[n1.getLabel()].length; j++) {
            	DirectedNode n2 = this.getNodes().get(j);
                if (matrixVal[n1.getLabel()][j] != 0) {
                	Arc a1 = new Arc(n1,n2,matrixVal[n1.getLabel()][j]);
                    n1.addArc(a1);
                    this.arcs.add(a1);
                	n2.addArc(a1);
                    this.nbArcs ++;
                }
            }
        }            	
    }

    // ------------------------------------------
    // 				Accessors
    // ------------------------------------------
    

    /**
     * Adds the arc (from,to) with cost if it is not already present in the graph. 
     * And adds this arc to the incident list of both extremities (nodes) and into the global list "arcs" of the graph.
     */
    public void addArc(DirectedNode from, DirectedNode to, int cost) {
        if (!this.arcs.contains(new Arc(from, to))) {
            Arc a1 = new Arc(from, to, cost);
            from.addArc(a1);
            to.addArc(a1);
            this.arcs.add(a1);
            this.nbArcs++;
        }
    }
    
    
    
    
    public static void main(String[] args) {
        int[][] matrixValued = GraphTools.generateValuedGraphData(10, false, false, true, false, 100001);
        GraphTools.afficherMatrix(matrixValued);
        AdjacencyListDirectedValuedGraph al = new AdjacencyListDirectedValuedGraph(matrixValued);
        System.out.println(al);        

        // ajoutons un arc (0,1) de valeur 5
        DirectedNode n1 = al.getNodes().get(0);
        DirectedNode n2 = al.getNodes().get(1);


        al.addArc(n1, n2, 5);
        System.out.println(al);

        // ajoutons un arc (0,1) de valeur 10
        al.addArc(n2, n1, 10);
        System.out.println(al);
    }
	
}
