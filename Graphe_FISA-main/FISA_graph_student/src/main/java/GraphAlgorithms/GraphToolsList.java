package GraphAlgorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import AdjacencyList.AdjacencyListDirectedGraph;
import Nodes_Edges.Arc;
import Nodes_Edges.DirectedNode;

public class GraphToolsList  extends GraphTools {

	private static int _DEBBUG =0;

	private static int[] visite;
	private static int[] debut;
	private static int[] fin;
	private static List<Integer> order_CC;
	private static int cpt=0;

	// Liste pour stocker l'ordre des sommets complètement explorés (Question 11)
	private static List<DirectedNode> finList;

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

	// DFS avec classificaiton dynamique
	public void explorerSommet(DirectedNode sommet, List<DirectedNode> atteints) {
		int label = sommet.getLabel();

		// Marquer le sommet comme en cours de visite et dater le début
		visite[label] = 1;
		debut[label] = cpt++;
		atteints.add(sommet);

		// Explorer tous les successeurs
		for (Arc arc : sommet.getArcSucc()) {
			DirectedNode voisin = arc.getSecondNode();
			int labelVoisin = voisin.getLabel();

			// Ne visiter que les sommets non visités (visite[voisin] == 0)
			if (visite[labelVoisin] == 0) {
				explorerSommet(voisin, atteints);
			}
		}

		// Marquer le sommet comme totalement visité et dater la fin
		visite[label] = 2;
		fin[label] = cpt++;

		// Ajouter le sommet à la liste des sommets complètement explorés (Question 11)
		finList.add(sommet);
	}

	public List<DirectedNode> explorerGraphe(AdjacencyListDirectedGraph graph) {
		// Initialiser les tableaux pour le nombre de noeuds du graphe
		int nbNodes = graph.getNbNodes();
		visite = new int[nbNodes];
		debut = new int[nbNodes];
		fin = new int[nbNodes];
		order_CC = new ArrayList<>();
		finList = new ArrayList<>(); // Initialiser la liste de fin (Question 11)
		cpt = 0;

		// Initialiser tous les sommets comme non visités
		Arrays.fill(visite, 0);

		List<DirectedNode> atteints = new ArrayList<DirectedNode>();

		for (DirectedNode sommet : graph.getNodes()) {
			if (visite[sommet.getLabel()] == 0) {
				explorerSommet(sommet, atteints);
			}
		}

		return atteints;
	}


	// superbe logs (à rendre plus propre un jour)
	public void afficherInformationsParcours(AdjacencyListDirectedGraph graph) {
		System.out.println("\nInformations du parcours DFS :");
		System.out.println("Sommet\t| État\t| Début\t| Fin");
		System.out.println("--------|-------|-------|-------");

		for (DirectedNode node : graph.getNodes()) {
			int label = node.getLabel();
			String etat = "";
			switch (visite[label]) {
				case 0: etat = "Non visité"; break;
				case 1: etat = "En cours"; break;
				case 2: etat = "Terminé"; break;
			}
			System.out.println(String.format("%s\t| %s\t| %d\t| %d",
				node.toString(), etat, debut[label], fin[label]));
		}
	}

	public boolean estDescendant(int x, int y) {
		//System.out.println("debut[x] = " + debut[x] + " debut[y] = " + debut[y] + " fin[y] = " + fin[y] + " fin[x] = " + fin[x]);
		return debut[x] < debut[y] && fin[y] < fin[x];
	}

	// pour affichage dans les test
	public List<DirectedNode> getFinList() {
		return finList;
	}

	public void explorerSommetBis(DirectedNode sommet, List<DirectedNode> composante, AdjacencyListDirectedGraph grapheInverse) {
		int label = sommet.getLabel();

		// Marquer le sommet comme visité dans cette phase
		visite[label] = 1;
		composante.add(sommet);

		System.out.println("  - Visite du sommet : " + sommet);

		for (Arc arc : sommet.getArcSucc()) {
			DirectedNode voisin = arc.getSecondNode();
			int labelVoisin = voisin.getLabel();

			if (visite[labelVoisin] == 0) {
				explorerSommetBis(voisin, composante, grapheInverse);
			}
		}
	}


	public List<List<DirectedNode>> explorerGrapheBis(AdjacencyListDirectedGraph graphe) {
		int nbNodes = graphe.getNbNodes();
		visite = new int[nbNodes];
		Arrays.fill(visite, 0);

		List<List<DirectedNode>> composantesFortementConnexes = new ArrayList<>();
		int numeroComposante = 1;

		for (int i = finList.size() - 1; i >= 0; i--) {
			DirectedNode sommet = finList.get(i);

			DirectedNode sommetInverse = graphe.getNodes().get(sommet.getLabel());

			if (visite[sommet.getLabel()] == 0) {
				List<DirectedNode> composante = new ArrayList<>();

				System.out.println("\nComposante fortement connexe numéro" + numeroComposante +
				                   " (à partir du sommet " + sommet + ") :");

				explorerSommetBis(sommetInverse, composante, graphe);

				// Convertir les labels pour l'affichage (utiliser les sommets du graphe original)
				List<DirectedNode> composanteOriginale = new ArrayList<>();
				for (DirectedNode n : composante) {
					composanteOriginale.add(graphe.getNodes().get(n.getLabel()));
				}

				composantesFortementConnexes.add(composanteOriginale);

				System.out.print("  Sommets atteints : ");
				for (DirectedNode n : composanteOriginale) {
					System.out.print(n + " ");
				}
				System.out.println();

				numeroComposante++;
			}
		}

		return composantesFortementConnexes;
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

		System.out.println("\n=== Test du DFS (avec classification dynamique) ===");
		System.out.println("DFS on the graph: ");
		for (DirectedNode n : gtl.explorerGraphe(al)) {
			System.out.print(n+" ");
		}
		System.out.println("\nExpected: \nn_0 n_4 n_2 n_6 n_9 n_5 n_3 n_8 n_1");



		gtl.afficherInformationsParcours(al);

		System.out.print("Ordre de fin : ");
		for (DirectedNode node : gtl.getFinList()) {
			System.out.print(node + " ");
		}

		AdjacencyListDirectedGraph grapheInverse = al.computeInverse(); // je sais pas d'où vient inverserGraphe, dans le sujet..?
		List<List<DirectedNode>> composantes = gtl.explorerGrapheBis(grapheInverse);

		System.out.println("\n=== Composantes fortement connextes ===");
		System.out.println("Nombre total de composantes : " + composantes.size());

		for (int i = 0; i < composantes.size(); i++) {
			System.out.print("Composante " + (i + 1) + " : {");
			for (int j = 0; j < composantes.get(i).size(); j++) {
				System.out.print(composantes.get(i).get(j));
				if (j < composantes.get(i).size() - 1) {
					System.out.print(", ");
				}
			}
			System.out.println("}");
		}

		// test filiation entre sommets
		System.out.println("\n=== Test de filiation ===");
		System.out.println("Le sommet 4 est-il descendant du sommet 0 ? " + gtl.estDescendant(0, 4));
		System.out.println("Le sommet 0 est-il descendant du sommet 4 ? " + gtl.estDescendant(4, 0));
		System.out.println("Le sommet 2 est-il descendant du sommet 4 ? " + gtl.estDescendant(4, 2));
		System.out.println("Le sommet 2 est-il descendant du sommet 1 ? " + gtl.estDescendant(4, 1));
		System.out.println("Le sommet 4 est-il descendant du sommet 2 ? " + gtl.estDescendant(2, 4));

		// visualisation avec le superbe GraphVisualizer
		 //new DirectedGraphVisualizer(Matrix).display("Graph dirigé avec BFS");
	}
}
