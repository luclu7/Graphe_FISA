package Visualizer;

import GraphAlgorithms.GraphTools;
import com.brunomnsilva.smartgraph.graph.Graph;
import com.brunomnsilva.smartgraph.graph.GraphEdgeList;

public class UndirectedGraphVisualizer extends VisualizerBase {

    public UndirectedGraphVisualizer(int[][] matrix) {
        super();
        this.graph = convertGraph(matrix);
    }

    @Override
    protected Graph<Integer, Weight> convertGraph(int[][] matrix) {
        Graph<Integer, Weight> g = new GraphEdgeList<>();

        for (int i = 0; i < matrix.length; i++) {
            g.insertVertex(i);
        }

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] != 0) {
                    if (i != j) {
                        g.insertEdge(i, j, new Weight(matrix[i][j]));
                    }
                }
            }
        }
        return g;
    }

    static public void main(String[] args) throws InterruptedException {
        int[][] matrixValued = GraphTools.generateValuedGraphData(10, false, true, false, false, 100001);
        GraphTools.afficherMatrix(matrixValued);
        UndirectedGraphVisualizer am = new UndirectedGraphVisualizer(matrixValued);
        am.display();

        Thread.sleep(5000);

        System.out.println("<< Remplacement du graph ! >>");
        int[][] matrixValued2 = GraphTools.generateValuedGraphData(10, false, true, false, false, 100001);
        am.setGraph(matrixValued2);
    }
}
