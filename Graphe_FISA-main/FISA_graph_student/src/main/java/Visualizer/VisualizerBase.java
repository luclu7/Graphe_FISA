package Visualizer;

import com.brunomnsilva.smartgraph.containers.SmartGraphDemoContainer;
import com.brunomnsilva.smartgraph.graph.Graph;
import com.brunomnsilva.smartgraph.graphview.SmartGraphPanel;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

abstract class VisualizerBase {
    protected Stage stage;
    protected SmartGraphPanel<Integer, Weight> graphView;
    protected Graph<Integer, Weight> graph;

    protected static class Weight {
        private final int weight;

        public Weight(int weight) {
            this.weight = weight;
        }

        public int getWeight() {
            return weight;
        }

        @Override
        public String toString() {
            return String.valueOf(weight);
        }
    }

    public void display(String title) {
        Platform.startup(() -> {
            this.graphView = new SmartGraphPanel<>(this.graph);
            SmartGraphDemoContainer container = new SmartGraphDemoContainer(this.graphView);
            Scene scene = new Scene(container, 1024, 768);

            this.stage = new Stage(StageStyle.DECORATED);
            stage.setTitle(title);
            stage.setScene(scene);
            stage.show();
            graphView.init();
        });
    }
    
    public void setGraph(int[][] matrix) {
        Platform.runLater(() -> {
            this.graph = convertGraph(matrix);
            this.graphView = new SmartGraphPanel<>(this.graph);
            SmartGraphDemoContainer container = new SmartGraphDemoContainer(this.graphView);
            Scene scene = new Scene(container, 1024, 768);
            this.stage.setScene(scene);
            graphView.init();
        });
    }
    
    protected abstract Graph<Integer, Weight> convertGraph(int[][] matrix);
}
