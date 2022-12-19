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

    List<PossibleSolution> possibleSolution;

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

    public record PossibleSolution(Set<Valve> opens, int totalFlow) {
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

    private Map<String, DijkstraAlgorithm> generateDjikstraForAllNode() {
        Map<String, DijkstraAlgorithm> djikstraMap = new HashMap<>();
        for (Valve valve : valves) {
            DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph);
            dijkstra.execute(nodesMap.get(valve.id));
            djikstraMap.put(valve.id, dijkstra);
        }
        return djikstraMap;
    }

    private int searchBest(Map<String, DijkstraAlgorithm> djikstraMap, Valve start, int timeMax) {
        int time = 0;
        int currentFlow = 0;
        int totalFlow = 0;
        Set<Valve> opens = new HashSet<>();

        return searchBest(start, time, currentFlow, totalFlow, djikstraMap, opens, timeMax);
    }

    private int searchBest(Valve start, int time, int currentFlow, int totalFlow,
            Map<String, DijkstraAlgorithm> djikstraMap, Set<Valve> opens, int timeMax) {
        Set<Valve> tempOpens = new HashSet<>(opens);
        if (time >= timeMax) {
            possibleSolution.add(new PossibleSolution(new HashSet<>(tempOpens), totalFlow));
            return totalFlow;
        }
        if (start.flow > 0) {
            totalFlow += currentFlow;
            currentFlow += start.flow;
            time++;
            tempOpens.add(start);
        }
        if (time >= timeMax) {
            possibleSolution.add(new PossibleSolution(new HashSet<>(tempOpens), totalFlow));
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
                if (timeMax - time < travelTime) {
                    addFlow = (timeMax - time) * currentFlow;
                    result = totalFlow + addFlow;
                    possibleSolution.add(new PossibleSolution(new HashSet<>(tempOpens), result));
                } else {
                    addFlow = travelTime * currentFlow;
                    addTime = travelTime;
                    result = searchBest(valve, time + addTime, currentFlow, totalFlow + addFlow, djikstraMap, tempOpens,
                            timeMax);
                }

                if (result > max) {
                    max = result;
                }
            }
        }
        possibleSolution.add(new PossibleSolution(new HashSet<>(tempOpens), totalFlow + (26 - time) * currentFlow));
        if (found > 0) {
            return max;
        } else {
            return totalFlow + (timeMax - time) * currentFlow;
        }
    }

    private Valve getValveById(String valveId) {
        Valve start = null;
        for (Valve valve : valves) {
            if (valve.id.equals(valveId)) {
                start = valve;
            }
        }
        return start;
    }

    private int findBestResultBasedOnPossibleSolution() {
        int res = 0;
        for (int i = 0; i <= possibleSolution.size(); i++) {
            for (int j = i + 1; j <= possibleSolution.size() - 1; j++) {
                Set<Valve> opens1 = possibleSolution.get(i).opens;
                Set<Valve> opens2 = possibleSolution.get(j).opens;

                if (areDistinct(opens1, opens2)) {
                    int total1 = possibleSolution.get(i).totalFlow;
                    int total2 = possibleSolution.get(j).totalFlow;
                    res = Math.max(res, total1 + total2);
                }
            }
            if (i % 100 == 0) {
                System.out.println(i + "/" + possibleSolution.size() + " current max:" + res);
            }
        }
        return res;
    }

    private boolean areDistinct(Set<Valve> set1, Set<Valve> set2) {
        for (Valve valve : set1) {
            if (set2.contains(valve)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Object getAnswer1() {
        possibleSolution = new ArrayList<>();
        Map<String, DijkstraAlgorithm> djikstraMap = generateDjikstraForAllNode();
        Valve start = getValveById("AA");
        return searchBest(djikstraMap, start, 30);
    }

    @Override
    public Object getAnswer2() {
        possibleSolution = new ArrayList<>();
        Map<String, DijkstraAlgorithm> djikstraMap = generateDjikstraForAllNode();
        Valve start = getValveById("AA");
        searchBest(djikstraMap, start, 26);
        return findBestResultBasedOnPossibleSolution();
    }

    public static void main(final String[] args) throws IOException {
        ProboscideaVolcanium proboscideaVolcanium = new ProboscideaVolcanium("day16/input");
        System.out.println("Answer1: " + proboscideaVolcanium.getAnswer1());
        System.out.println("Answer2: " + proboscideaVolcanium.getAnswer2());
    }
}
