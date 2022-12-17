package day16;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import helper.Puzzle;
import helper.djikstra.DijkstraAlgorithm;
import helper.djikstra.Edge;
import helper.djikstra.Graph;
import helper.djikstra.Vertex;

public class ProboscideaVolcanium extends Puzzle {

    List<Valve> valves = new ArrayList<>();
    Graph graph;
    Map<String, Vertex> nodesMap = new HashMap<>();

    protected ProboscideaVolcanium(final String input) throws IOException {
        super(input);
        Pattern valvePattern = Pattern.compile("Valve (\\w+) has flow rate=(\\d+); tunnels? leads? to valves? (.+)");
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(getInputFile()), StandardCharsets.UTF_8));) {
            String line;

            while ((line = br.readLine()) != null) {
                Matcher valveMatcher = valvePattern.matcher(line);
                while (valveMatcher.find()) {

                    List<String> tunnelTo = new ArrayList<>();
                    String[] splits = valveMatcher.group(3).split(",");
                    for (String split : splits) {
                        tunnelTo.add(split.trim());
                    }
                    Valve v = new Valve(valveMatcher.group(1), Integer.parseInt(valveMatcher.group(2)), tunnelTo);
                    valves.add(v);
                }
            }
        }
        graph = generateGraph(valves);
    }

    private Graph generateGraph(List<Valve> valves) {

        ArrayList<Vertex> nodes = new ArrayList<>();

        ArrayList<Edge> edges = new ArrayList<>();
        for (Valve valve : valves) {
            Vertex v = new Vertex(valve.id, valve.id);
            nodes.add(v);
            nodesMap.put(valve.id, v);
        }

        for (Valve valve : valves) {
            for (String tunnelTo : valve.tunnelsTo) {
                edges.add(new Edge("id", nodesMap.get(valve.id), nodesMap.get(tunnelTo), 1));
            }
        }

        return new Graph(nodes, edges);
    }

    @Override
    public Object getAnswer1() {
        Map<String, DijkstraAlgorithm> djikstraMap = new HashMap<>();
        for (Valve valve : valves) {
            DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph);
            dijkstra.execute(nodesMap.get(valve.id));
            djikstraMap.put(valve.id, dijkstra);
        }

        int time = 0;
        int currentFlow = 0;
        int totalFlow = 0;
        Valve start = null;
        for (Valve valve : valves) {
            if (valve.id.equals("AA")) {
                start = valve;
            }
        }
        Set<Valve> opens = new HashSet<>();


        int res = searchBest(start, time, currentFlow, totalFlow, djikstraMap, opens);


        return res;
    }

    private int searchBest(Valve start, int time, int currentFlow, int totalFlow,
            Map<String, DijkstraAlgorithm> djikstraMap, Set<Valve> opens) {
        // System.out.println(start + " " + time + " " + currentFlow + " " + totalFlow);

        Set<Valve> tempOpens = new HashSet<>(opens);
        if (time >= 30) {
            return totalFlow;
        }
        if (start.flow > 0) {
            totalFlow += currentFlow;
            currentFlow += start.flow;
            time++;
            tempOpens.add(start);
            // System.out.println(start + " " + time + " " + currentFlow + " " + totalFlow);
        }
        if (time >= 30) {
            return totalFlow;
        }

        DijkstraAlgorithm dji = djikstraMap.get(start.id);
        int max = totalFlow;
        int found = 0;
        for (Valve valve : valves) {

            if (valve != start && !tempOpens.contains(valve) && valve.flow > 0) {
                found++;
                LinkedList<Vertex> path = dji.getPath(nodesMap.get(valve.id));
                int travelTime = path.size() - 1;

                int addFlow;
                int addTime;
                int result;
                if (30 - time < travelTime) {
                    addFlow = (30 - time) * currentFlow;
                    result = totalFlow + addFlow;
                } else {
                    addFlow = travelTime * currentFlow;
                    addTime = travelTime;
                    result = searchBest(valve, time + addTime, currentFlow, totalFlow + addFlow, djikstraMap,
                            tempOpens);
                }

                if (result > max) {
                    max = result;
                }


            }
        }
        if (found > 0) {
            return max;
        } else {
            return totalFlow + (30 - time) * currentFlow;
        }
    }

    @Override
    public Object getAnswer2() {
        // TODO Auto-generated method stub
        return null;
    }

    public static void main(final String[] args) throws IOException {
        ProboscideaVolcanium proboscideaVolcanium = new ProboscideaVolcanium("day16/input");
        System.out.println("Answer1: " + proboscideaVolcanium.getAnswer1());
        System.out.println("Answer2: " + proboscideaVolcanium.getAnswer2());
    }

}
