package Visualizer;

import GraphAlgorithms.GraphTools;
import com.brunomnsilva.smartgraph.graph.Digraph;
import com.brunomnsilva.smartgraph.graph.DigraphEdgeList;
import com.brunomnsilva.smartgraph.graph.Graph;

public class DirectedGraphVisualizer extends VisualizerBase {

    public DirectedGraphVisualizer(int[][] matrix) {
        super();
        this.graph = convertGraph(matrix);
    }

    @Override
    protected Graph<Integer, Weight> convertGraph(int[][] matrix) {
        Digraph<Integer, Weight> g = new DigraphEdgeList<>();

        for (int i = 0; i < matrix.length; i++) {
            g.insertVertex(i);
        }

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] != 0) {
                    g.insertEdge(i, j, new Weight(matrix[i][j]));
                }
            }
        }
        return g;
    }

    static public void main(String[] args) throws InterruptedException {
        int[][] matrixValued = GraphTools.generateValuedGraphData(10, false, false, true, false, 100001);
        GraphTools.afficherMatrix(matrixValued);
        DirectedGraphVisualizer am = new DirectedGraphVisualizer(matrixValued);
        am.display("Directed Graph");

        // on attend un peu avant de remplacer le graph
        Thread.sleep(5000);

        System.out.println("<< Remplacement du graph ! >>");
        int[][] matrixValued2 = GraphTools.generateValuedGraphData(10, true, false, false, false, 100001);
        am.setGraph(matrixValued2);
    }
}
