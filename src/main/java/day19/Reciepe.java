package day19;

public class Reciepe {

    MaterialEnum type;
    Material cost;

    public Reciepe(final MaterialEnum type, final Material cost) {
        super();
        this.type = type;
        this.cost = cost;
    }

    static Reciepe newOreRobotReciepe(final int ore) {
        return new Reciepe(MaterialEnum.ORE, new Material(ore, 0, 0, 0));
    }

    static Reciepe newClayRobotReciepe(final int ore) {
        return new Reciepe(MaterialEnum.CLAY, new Material(ore, 0, 0, 0));
    }

    static Reciepe newObsidianRobotReciepe(final int ore, final int clay) {
        return new Reciepe(MaterialEnum.OBSIDIAN, new Material(ore, clay, 0, 0));
    }

    static Reciepe newGeodeRobotReciepe(final int ore, final int obsidian) {
        return new Reciepe(MaterialEnum.GEODE, new Material(ore, 0, obsidian, 0));
    }

    @Override
    public String toString() {
        return "Reciepe [type=" + type + ", cost=" + cost + "]";
    }

    public boolean isPossible(final Material material) {
        return material.clay - this.cost.clay >= 0 && material.ore - this.cost.ore >= 0
                && material.obsidian - this.cost.obsidian >= 0;
    }

    public boolean isWaiting(final Material material, final Material robotCount) {
        switch (type) {
            case ORE: {
                return material.ore - robotCount.ore >= this.cost.ore + 1;
            }
            case CLAY: {
                return material.ore - robotCount.ore >= this.cost.ore;

            }
            case OBSIDIAN: {
                return false;
            }
            case GEODE: {
                return false;
            }
            default:
                throw new IllegalArgumentException("Unexpected value: " + type);
        }
    }

    public Material created() {
        switch (type) {
            case ORE: {
                return new Material(1, 0, 0, 0);
            }
            case CLAY: {
                return new Material(0, 1, 0, 0);
            }
            case OBSIDIAN: {
                return new Material(0, 0, 1, 0);
            }
            case GEODE: {
                return new Material(0, 0, 0, 1);
            }
            default:
                throw new IllegalArgumentException("Unexpected value: " + type);
        }
    }


}
