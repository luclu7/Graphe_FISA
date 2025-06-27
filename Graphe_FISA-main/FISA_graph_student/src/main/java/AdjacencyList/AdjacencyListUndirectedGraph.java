package AdjacencyList;

import java.util.ArrayList;
import java.util.List;

import AdjacencyMatrix.AdjacencyMatrixUndirectedGraph;
import GraphAlgorithms.GraphTools;
import Nodes_Edges.Edge;
import Nodes_Edges.UndirectedNode;


public class AdjacencyListUndirectedGraph {

	//--------------------------------------------------
    // 				Class variables
    //--------------------------------------------------

	protected List<UndirectedNode> nodes; // list of the nodes in the graph
	protected List<Edge> edges; // list of the edges in the graph
    protected int nbNodes; // number of nodes
    protected int nbEdges; // number of edges

    public record DijkstraResult(int[] distances, UndirectedNode[] predecessors) {

        public int getDistance(int nodeIndex) {
                return distances[nodeIndex];
            }

        public UndirectedNode getPredecessor(int nodeIndex) {
                return predecessors[nodeIndex];
            }

        @Override
            public String toString() {
                StringBuilder sb = new StringBuilder();
                sb.append("Résultats de Dijkstra:\n");
                for (int i = 0; i < distances.length; i++) {
                    sb.append("Sommet ").append(i).append(": distance=").append(distances[i]);
                    if (predecessors[i] != null) {
                        sb.append(", prédécesseur=").append(predecessors[i].getLabel());
                    } else {
                        sb.append(", prédécesseur=<indéfini>");
                    }
                    sb.append("\n");
                }
                return sb.toString();
            }
        }


    //--------------------------------------------------
    // 				Constructors
    //--------------------------------------------------
    
	public AdjacencyListUndirectedGraph() {
		 this.nodes = new ArrayList<UndirectedNode>();
		 this.edges = new ArrayList<Edge>();
		 this.nbNodes = 0;
	     this.nbEdges = 0;
	}
	
		
	public AdjacencyListUndirectedGraph(List<UndirectedNode> nodes,List<Edge> edges) {
		this.nodes = nodes;
		this.edges = edges;
        this.nbNodes = nodes.size();
        this.nbEdges = edges.size();
        
    }

    public AdjacencyListUndirectedGraph(int[][] matrix) {
        this.nbNodes = matrix.length;
        this.nodes = new ArrayList<UndirectedNode>();
        this.edges = new ArrayList<Edge>();
        
        for (int i = 0; i < this.nbNodes; i++) {
            this.nodes.add(new UndirectedNode(i));
        }
        for (UndirectedNode n1 : this.getNodes()) {
            for (int j = n1.getLabel(); j < matrix[n1.getLabel()].length; j++) {
            	UndirectedNode n2 = this.getNodes().get(j);
                if (matrix[n1.getLabel()][j] != 0) {
                    Edge e1 = new Edge(n1,n2);
                    n1.addEdge(e1);
                    this.edges.add(e1);
                	n2.addEdge(new Edge(n2,n1));
                    this.nbEdges++;
                }
            }
        }
    }

    public AdjacencyListUndirectedGraph(AdjacencyListUndirectedGraph g) {
        super();
        this.nbNodes = g.getNbNodes();
        this.nbEdges = g.getNbEdges();
        this.nodes = new ArrayList<UndirectedNode>();
        this.edges = new ArrayList<Edge>();
        
        
        for (UndirectedNode n : g.getNodes()) {
            this.nodes.add(new UndirectedNode(n.getLabel()));
        }
        
        for (Edge e : g.getEdges()) {
        	this.edges.add(e);
        	UndirectedNode new_n   = this.getNodes().get(e.getFirstNode().getLabel());
        	UndirectedNode other_n = this.getNodes().get(e.getSecondNode().getLabel());
        	new_n.addEdge(new Edge(e.getFirstNode(),e.getSecondNode(),e.getWeight()));
        	other_n.addEdge(new Edge(e.getSecondNode(),e.getFirstNode(),e.getWeight()));
        }        
    }
    

    // ------------------------------------------
    // 				Accessors
    // ------------------------------------------
    
    /**
     * Returns the list of nodes in the graph
     */
    public List<UndirectedNode> getNodes() {
        return this.nodes;
    }
    
    /**
     * Returns the list of edges in the graph
     */
    public List<Edge> getEdges() {
        return this.edges;
    }

    /**
     * Returns the number of nodes in the graph
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
     * @return true if there is an edge between x and y
     */
    public boolean isEdge(UndirectedNode x, UndirectedNode y) {      	
         return this.getEdges().contains(new Edge(x,y));
    }

    /**
     * Removes edge (x,y) if there exists one. And remove this edge and the inverse in the list of edges from the two extremities (nodes)
     */
    public void removeEdge(UndirectedNode x, UndirectedNode y) {
    	if(isEdge(x,y)){
            this.edges.remove(new Edge(x,y));
            this.nbEdges--;
    	}
    }

    /**
     * Adds edge (x,y) if it is not already present in the graph, requires that nodes x and y already exist. 
     * And adds this edge to the incident list of both extremities (nodes) and into the global list "edges" of the graph.
     * In non-valued graph, every edge has a cost equal to 0.
     */
    public void addEdge(UndirectedNode x, UndirectedNode y) {
    	if(!isEdge(x,y)){
            this.edges.add(new Edge(x,y));
    		this.nbEdges++;
    	}
    }

    //--------------------------------------------------
    // 					Methods
    //--------------------------------------------------
    
    

    /**
     * @return the corresponding nodes in the list this.nodes
     */
    public UndirectedNode getNodeOfList(UndirectedNode v) {
        return this.getNodes().get(v.getLabel());
    }
    
    /**
     * @return a matrix representation of the graph 
     */
    public int[][] toAdjacencyMatrix() {
        int[][] matrix = new int[nbNodes][nbNodes];
        for (Edge e : this.edges) {
            matrix[e.getFirstNode().getLabel()][e.getSecondNode().getLabel()] = 1;
            matrix[e.getSecondNode().getLabel()][e.getFirstNode().getLabel()] = 1;
        }
        return matrix;
    }

    public DijkstraResult dijkstra(UndirectedNode startNode) {
        boolean[] mark = new boolean[this.nbNodes];
        int[] val = new int[this.nbNodes];
        UndirectedNode[] pred = new UndirectedNode[this.nbNodes];

        for (UndirectedNode node : this.getNodes()) {
            int label = node.getLabel();
            mark[label] = false;
            val[label] = Integer.MAX_VALUE / 2;
            pred[label] = null;
        }

        val[startNode.getLabel()] = 0;
        pred[startNode.getLabel()] = startNode;

        AdjacencyMatrixUndirectedGraph matrix = new AdjacencyMatrixUndirectedGraph(this); // à instancier UNE fois

        while (true) {
            // Trouver le sommet non marqué avec la plus petite distance
            int x = -1;
            int min = Integer.MAX_VALUE / 2;
            for (int i = 0; i < this.nbNodes; i++) {
                if (!mark[i] && val[i] < min) {
                    x = i;
                    min = val[i];
                }
            }

            // si aucun sommet n’est accessible ou tous sont marqués -> fin
            if (x == -1) {
                break;
            }

            mark[x] = true;
            UndirectedNode current = this.getNodes().get(x);

            for (Integer neighbor : matrix.getNeighbours(x)) {
                if (!mark[neighbor]) {
                    int weight = matrix.getMatrix()[x][neighbor];
                    if (val[x] + weight < val[neighbor]) {
                        val[neighbor] = val[x] + weight;
                        pred[neighbor] = current;
                    }
                }
            }
        }

        return new DijkstraResult(val, pred);
    }

    
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("List of nodes and their neighbours :\n");
        for (UndirectedNode n : this.nodes) {
            s.append("Node ").append(n).append(" : ");
            s.append("\nList of incident edges : ");
            for (Edge e : n.getIncidentEdges()) {
                s.append(e).append("  ");
            }
            s.append("\n");            
        }
        s.append("\nList of edges :\n");
        for (Edge e : this.edges) {
        	s.append(e).append("  ");
        }
        s.append("\n");
        return s.toString();
    }

    void printEdge(int firstNode, int secondNode) {
        System.out.println("isEdge(" + firstNode + "," + secondNode + ") = " + this.isEdge(this.getNodes().get(firstNode), this.getNodes().get(secondNode)) + " (nb d'edges: " + this.getNbEdges() + ")");
    }

    public static void main(String[] args) {
        int[][] mat = GraphTools.generateGraphData(10, 20, false, true, false, 100001);
        GraphTools.afficherMatrix(mat);
        AdjacencyListUndirectedGraph al = new AdjacencyListUndirectedGraph(mat);
        System.out.println(al);        

        int firstNode = 2;
        int secondNode = 5;

        al.printEdge(firstNode, secondNode);
        // il n'existe pas donc ajoutons le
        System.out.println("addEdge(" + firstNode + "," + secondNode + ")");
        al.addEdge(al.getNodes().get(firstNode), al.getNodes().get(secondNode));
        al.printEdge(firstNode, secondNode);
        System.out.println("removeEdge(" + firstNode + "," + secondNode + ")");
        al.removeEdge(al.getNodes().get(firstNode), al.getNodes().get(secondNode));
        al.printEdge(firstNode, secondNode);

        System.out.println();

        int[][] mat2 = al.toAdjacencyMatrix();
        GraphTools.afficherMatrix(mat2);

        System.out.println("addEdge(" + firstNode + "," + secondNode + ")");
        al.addEdge(al.getNodes().get(firstNode), al.getNodes().get(secondNode));

        System.out.println("al.toAdjacencyMatrix() : ");
        int[][] mat3 = al.toAdjacencyMatrix();
        GraphTools.afficherMatrix(mat3);


        System.out.println("== Test Dijkstra ==");
        DijkstraResult result = al.dijkstra(al.getNodes().get(0));
        System.out.println(result);

//        new UndirectedGraphVisualizer(mat3).display("Graph non dirigé");
    }

}
