package day12;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedList;

import helper.Puzzle;
import helper.djikstra.DijkstraAlgorithm;
import helper.djikstra.Edge;
import helper.djikstra.Graph;
import helper.djikstra.Vertex;

public class HillClimbingAlgorithm extends Puzzle {

    HeightMap heightMap = new HeightMap();
    Graph graph;

    protected HillClimbingAlgorithm(final String input) throws IOException {
        super(input);
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(getInputFile()), StandardCharsets.UTF_8));) {
            String line;

            while ((line = br.readLine()) != null) {
                heightMap.addLine(line);
            }
        }

        graph = generateGraph(heightMap);

    }

    private Graph generateGraph(final HeightMap heightMap) {
        int maxX = heightMap.getmaxX();
        int maxY = heightMap.getmaxY();

        ArrayList<Vertex> nodes = new ArrayList<>();
        ArrayList<Edge> edges = new ArrayList<>();
        for (int i = 0; i < maxX * maxY; i++) {
            Vertex location = new Vertex("Node_" + i, "Node_" + i);
            nodes.add(location);
        }

        for (int j = 0; j < maxY; j++) {
            for (int i = 0; i < maxX; i++) {
                if (i - 1 >= 0 && canBeClimb(heightMap.get(i, j), heightMap.get(i - 1, j))) {
                    edges.add(new Edge("id", nodes.get(toNodeId(i, j)), nodes.get(toNodeId(i - 1, j)), 1));
                }

                if (i + 1 < maxX && canBeClimb(heightMap.get(i, j), heightMap.get(i + 1, j))) {
                    edges.add(new Edge("id", nodes.get(toNodeId(i, j)), nodes.get(toNodeId(i + 1, j)), 1));
                }
                if (j - 1 >= 0 && canBeClimb(heightMap.get(i, j), heightMap.get(i, j - 1))) {
                    edges.add(new Edge("id", nodes.get(toNodeId(i, j)), nodes.get(toNodeId(i, j - 1)), 1));
                }
                if (j + 1 < maxY && canBeClimb(heightMap.get(i, j), heightMap.get(i, j + 1))) {
                    edges.add(new Edge("id", nodes.get(toNodeId(i, j)), nodes.get(toNodeId(i, j + 1)), 1));
                }
            }
        }
        return new Graph(nodes, edges);
    }

    private int toNodeId(final int x, final int y) {
        return y * heightMap.getmaxX() + x;
    }

    private boolean hasNeighbours(final int i, final int j, final char c) {

        if (i - 1 >= 0 && heightMap.get(i - 1, j) == c) {
            return true;
        }

        if (i + 1 < heightMap.getmaxX() && heightMap.get(i + 1, j) == c) {
            return true;
        }
        if (j - 1 >= 0 && heightMap.get(i, j - 1) == c) {
            return true;
        }
        if (j + 1 < heightMap.getmaxY() && heightMap.get(i, j + 1) == c) {
            return true;
        }
        return false;
    }

    private boolean canBeClimb(final Character char1, final Character char2) {

        return char1 + 1 >= char2;
    }

    private String visualizePath(final LinkedList<Vertex> path) {
        StringBuilder result = new StringBuilder();
        for (int j = 0; j < heightMap.getmaxY(); j++) {
            for (int i = 0; i < heightMap.getmaxX(); i++) {
                if (path.contains(new Vertex("Node_" + toNodeId(i, j), "Node_" + toNodeId(i, j)))) {

                    int id = path.indexOf(new Vertex("Node_" + toNodeId(i, j), "Node_" + toNodeId(i, j)));
                    if (id == path.size() - 1) {
                        result.append("E");
                    } else {
                        int next = Integer.parseInt(path.get(id + 1).getId().substring(5));
                        if (next - toNodeId(i, j) == 1) {
                            result.append(">");
                        } else if (next - toNodeId(i, j) == -1) {
                            result.append("<");
                        } else if (next - toNodeId(i, j) < 0) {
                            result.append("^");
                        } else if (next - toNodeId(i, j) > 0) {
                            result.append("v");
                        }
                    }
                } else {
                    result.append(".");
                }
            }
            result.append("\n");
        }

        return result.toString();
    }

    @Override
    public Object getAnswer1() {
        DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph);
        dijkstra.execute(graph.getVertexes().get(toNodeId(heightMap.Sx, heightMap.Sy)));
        LinkedList<Vertex> path = dijkstra.getPath(graph.getVertexes().get(toNodeId(heightMap.Ex, heightMap.Ey)));
        // System.out.println(visualizePath(path));
        return path.size() - 1;
    }

    @Override
    public Object getAnswer2() {
        int min = Integer.MAX_VALUE;
        LinkedList<Vertex> bestPath = null;

        for (int j = 0; j < heightMap.getmaxY(); j++) {
            for (int i = 0; i < heightMap.getmaxX(); i++) {
                if (heightMap.get(i, j) == 'a' && hasNeighbours(i, j, 'b')) {
                    DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph);
                    dijkstra.execute(graph.getVertexes().get(toNodeId(i, j)));
                    LinkedList<Vertex> path =
                            dijkstra.getPath(graph.getVertexes().get(toNodeId(heightMap.Ex, heightMap.Ey)));
                    if (path != null && path.size() - 1 < min) {
                        min = path.size() - 1;
                        bestPath = path;
                    }
                }
            }
        }
        // System.out.println(visualizePath(bestPath));
        return min;
    }

    public static void main(final String[] args) throws IOException {
        HillClimbingAlgorithm hillClimbingAlgorithm = new HillClimbingAlgorithm("day12/input");
        System.out.println("Answer1: " + hillClimbingAlgorithm.getAnswer1());
        System.out.println("Answer2: " + hillClimbingAlgorithm.getAnswer2());
    }

}
