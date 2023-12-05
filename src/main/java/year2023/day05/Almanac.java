package year2023.day05;

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

    public List<Long> getSeeds() {
        return seeds;
    }


    public void setSeeds(final List<Long> seeds) {
        this.seeds = seeds;
    }


    public AlmanacMap getSeedToSoil() {
        return seedToSoil;
    }


    public void setSeedToSoil(final AlmanacMap seedToSoil) {
        this.seedToSoil = seedToSoil;
    }


    public AlmanacMap getSoilToFertilizer() {
        return soilToFertilizer;
    }


    public void setSoilToFertilizer(final AlmanacMap soilToFertilizer) {
        this.soilToFertilizer = soilToFertilizer;
    }


    public AlmanacMap getFertilizerToWater() {
        return fertilizerToWater;
    }


    public void setFertilizerToWater(final AlmanacMap fertilizerToWater) {
        this.fertilizerToWater = fertilizerToWater;
    }


    public AlmanacMap getWaterToLight() {
        return waterToLight;
    }


    public void setWaterToLight(final AlmanacMap waterToLight) {
        this.waterToLight = waterToLight;
    }


    public AlmanacMap getLightToTemperature() {
        return lightToTemperature;
    }


    public void setLightToTemperature(final AlmanacMap lightToTemperature) {
        this.lightToTemperature = lightToTemperature;
    }


    public AlmanacMap getTemperatureToHumidity() {
        return temperatureToHumidity;
    }


    public void setTemperatureToHumidity(final AlmanacMap temperatureToHumidity) {
        this.temperatureToHumidity = temperatureToHumidity;
    }


    public AlmanacMap getHumidityToLocation() {
        return humidityToLocation;
    }


    public void setHumidityToLocation(final AlmanacMap humidityToLocation) {
        this.humidityToLocation = humidityToLocation;
    }


}
