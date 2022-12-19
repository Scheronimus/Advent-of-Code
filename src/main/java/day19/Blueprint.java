package day19;

import java.util.ArrayList;
import java.util.List;

public class Blueprint {
    Reciepe oreRobot;
    Reciepe clayRobot;
    Reciepe obsidianRobot;
    Reciepe geodeRobot;

    public Blueprint(final Reciepe oreRobot, final Reciepe clayRobot, final Reciepe obsidianRobot,
            final Reciepe geodeRobot) {
        super();
        this.oreRobot = oreRobot;
        this.clayRobot = clayRobot;
        this.obsidianRobot = obsidianRobot;
        this.geodeRobot = geodeRobot;
    }

    @Override
    public String toString() {
        return "Blueprint [oreRobot=" + oreRobot + ", clayRobot=" + clayRobot + ", obsidianRobot=" + obsidianRobot
                + ", geodeRobot=" + geodeRobot + "]";
    }

    public Material getMaximals() {
        Material max = new Material(0, 0, 0, 0);

        List<Reciepe> reciepes = new ArrayList<>();

        reciepes.add(oreRobot);
        reciepes.add(clayRobot);
        reciepes.add(obsidianRobot);
        reciepes.add(geodeRobot);

        for (Reciepe reciepe : reciepes) {
            max.clay = Math.max(max.clay, reciepe.cost.clay);
            max.ore = Math.max(max.ore, reciepe.cost.ore);
            max.obsidian = Math.max(max.obsidian, reciepe.cost.obsidian);
            max.geode = Math.max(max.geode, reciepe.cost.geode);
        }
        return max;
    }


}
