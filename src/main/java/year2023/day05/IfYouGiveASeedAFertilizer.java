package year2023.day05;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import helper.ListUtils;
import helper.Puzzle;


public class IfYouGiveASeedAFertilizer extends Puzzle {


    Almanac almanac = new Almanac();

    public IfYouGiveASeedAFertilizer(final String input) throws IOException {
        super(input);
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(getInputFile()), StandardCharsets.UTF_8));) {
            String line;
            List<Long> seeds;
            AlmanacMap currentMap = null;
            while ((line = br.readLine()) != null) {
                if (line.isEmpty()) {
                    continue;
                }
                if (line.contains("seeds: ")) {
                    seeds = ListUtils.stringToLongList(line.replace("seeds: ", "").trim());
                    almanac.setSeeds(seeds);
                } else if (line.contains("seed-to-soil map")) {
                    currentMap = almanac.getSeedToSoil();
                } else if (line.contains("soil-to-fertilizer map")) {
                    currentMap = almanac.getSoilToFertilizer();
                } else if (line.contains("fertilizer-to-water map")) {
                    currentMap = almanac.getFertilizerToWater();
                } else if (line.contains("water-to-light map")) {
                    currentMap = almanac.getWaterToLight();
                } else if (line.contains("light-to-temperature map")) {
                    currentMap = almanac.getLightToTemperature();
                } else if (line.contains("temperature-to-humidity map")) {
                    currentMap = almanac.getTemperatureToHumidity();
                } else if (line.contains("humidity-to-location map")) {
                    currentMap = almanac.getHumidityToLocation();
                } else {
                    List<Long> entryList = ListUtils.stringToLongList(line.trim());
                    currentMap.add(new AlmanacMapEntry(entryList.get(0), entryList.get(1), entryList.get(2)));
                }


            }

            // System.out.println("done");
        }
    }

    @Override
    public Object getAnswer1() {
        long res = Long.MAX_VALUE;
        for (Long seed : almanac.seeds) {
            long location = almanac.calculateLocationForSeed(seed);
            if (location < res) {
                res = location;
            }
        }
        return res;
    }


    @Override
    public Object getAnswer2() {
        List<Range> ranges = new ArrayList<>();
        for (int i = 0; i < almanac.seeds.size(); i = i + 2) {
            ranges.add(new Range(almanac.seeds.get(i), almanac.seeds.get(i + 1)));
        }

        System.out.println(ranges);

        // TODO Auto-generated method stub
        return null;
    }

    public static void main(final String[] args) throws IOException {
        IfYouGiveASeedAFertilizer ifYouGiveASeedAFertilizer = new IfYouGiveASeedAFertilizer("year2023/day05/input");
        System.out.println("Answer 1: " + ifYouGiveASeedAFertilizer.getAnswer1());
        System.out.println("Answer 2: " + ifYouGiveASeedAFertilizer.getAnswer2());
    }


}
