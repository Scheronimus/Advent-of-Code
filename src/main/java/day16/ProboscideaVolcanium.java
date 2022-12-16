package day16;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import helper.Puzzle;
import helper.djikstra.Edge;
import helper.djikstra.Graph;
import helper.djikstra.Vertex;

public class ProboscideaVolcanium extends Puzzle {

    List<Valve> valves = new ArrayList<>();

    protected ProboscideaVolcanium(final String input) throws IOException {
        super(input);
        Pattern valvePattern = Pattern.compile("Valve (\\w+) has flow rate=(\\d+); tunnels lead to valves (.+)");
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

        System.out.println(valves);
    }

    private Graph generateGraph(List<Valve> valves) {

        ArrayList<Vertex> nodes = new ArrayList<>();
        Map<String, Vertex> nodesMap = new HashMap<>();
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
        // TODO Auto-generated method stub
        return null;
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
