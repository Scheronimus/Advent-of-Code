package day19;

public class Material {
    int ore;
    int clay;
    int obsidian;
    int geode;

    public Material(final int ore, final int clay, final int obsidian, final int geode) {
        super();
        this.ore = ore;
        this.clay = clay;
        this.obsidian = obsidian;
        this.geode = geode;
    }

    public Material(final Material m) {
        super();
        this.ore = m.ore;
        this.clay = m.clay;
        this.obsidian = m.obsidian;
        this.geode = m.geode;
    }

    public Material multiplyBy(int i) {
        return new Material(ore * i, clay * i, obsidian * i, geode * i);
    }

    @Override
    public String toString() {
        return "Material [ore=" + ore + ", clay=" + clay + ", obsidian=" + obsidian + ", geode=" + geode + "]";
    }

    public void add(final Material material) {
        ore += material.ore;
        clay += material.clay;
        obsidian += material.obsidian;
        geode += material.geode;
    }

    public void addxtime(final Material material, int x) {
        ore += material.ore * x;
        clay += material.clay * x;
        obsidian += material.obsidian * x;
        geode += material.geode * x;
    }

    public void remove(final Material material) {
        ore -= material.ore;
        clay -= material.clay;
        obsidian -= material.obsidian;
        geode -= material.geode;
    }


    public int get(final MaterialEnum material) {
        switch (material) {
            case ORE: {
                return ore;
            }
            case CLAY: {
                return clay;
            }
            case OBSIDIAN: {
                return obsidian;
            }
            case GEODE: {
                return geode;
            }
            default:
                throw new IllegalArgumentException("Unexpected value: " + material);
        }
    }
}
