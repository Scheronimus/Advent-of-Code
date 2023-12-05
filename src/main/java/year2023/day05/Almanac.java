package year2023.day05;

import java.util.ArrayList;
import java.util.List;

public class Almanac {
    List<Long> seeds;
    AlmanacMap seedToSoil = new AlmanacMap();
    AlmanacMap soilToFertilizer = new AlmanacMap();
    AlmanacMap fertilizerToWater = new AlmanacMap();
    AlmanacMap waterToLight = new AlmanacMap();
    AlmanacMap lightToTemperature = new AlmanacMap();
    AlmanacMap temperatureToHumidity = new AlmanacMap();
    AlmanacMap humidityToLocation = new AlmanacMap();

    public long calculateLocationForSeed(final long seed) {
        return humidityToLocation.getDestination(temperatureToHumidity
                .getDestination(lightToTemperature.getDestination(waterToLight.getDestination(fertilizerToWater
                        .getDestination(soilToFertilizer.getDestination(seedToSoil.getDestination(seed)))))));
    }

    public List<Range> calculateLocationForSeed(List<Range> seedRanges) {
        return humidityToLocation.getDestination(temperatureToHumidity
                .getDestination(lightToTemperature.getDestination(waterToLight.getDestination(fertilizerToWater
                        .getDestination(soilToFertilizer.getDestination(seedToSoil.getDestination(seedRanges)))))));
    }

    public List<Range> getSeedsRange() {
        List<Range> ranges = new ArrayList<>();
        for (int i = 0; i < seeds.size(); i = i + 2) {
            ranges.add(new Range(seeds.get(i), seeds.get(i + 1)));
        }
        return ranges;
    }

    public List<Long> getSeeds() {
        return seeds;
    }

    public void setSeeds(final List<Long> seeds) {
        this.seeds = seeds;
    }

    public AlmanacMap getSeedToSoil() {
        return seedToSoil;
    }

    public AlmanacMap getSoilToFertilizer() {
        return soilToFertilizer;
    }

    public AlmanacMap getFertilizerToWater() {
        return fertilizerToWater;
    }

    public AlmanacMap getWaterToLight() {
        return waterToLight;
    }

    public AlmanacMap getLightToTemperature() {
        return lightToTemperature;
    }

    public AlmanacMap getTemperatureToHumidity() {
        return temperatureToHumidity;
    }

    public AlmanacMap getHumidityToLocation() {
        return humidityToLocation;
    }
}
