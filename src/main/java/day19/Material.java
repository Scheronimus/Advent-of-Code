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

    public void remove(final Material material) {
        ore -= material.ore;
        clay -= material.clay;
        obsidian -= material.obsidian;
        geode -= material.geode;
    }


}