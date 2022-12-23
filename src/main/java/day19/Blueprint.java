package day19;

import java.util.ArrayList;
import java.util.List;

public class Blueprint {
    Reciepe oreRobot;
    Reciepe clayRobot;
    Reciepe obsidianRobot;
    Reciepe geodeRobot;
    Material maximals;

    public Blueprint(final Reciepe oreRobot, final Reciepe clayRobot, final Reciepe obsidianRobot,
            final Reciepe geodeRobot) {
        super();
        this.oreRobot = oreRobot;
        this.clayRobot = clayRobot;
        this.obsidianRobot = obsidianRobot;
        this.geodeRobot = geodeRobot;
        maximals = calculateMaximals();
    }

    @Override
    public String toString() {
        return "Blueprint [oreRobot=" + oreRobot + ", clayRobot=" + clayRobot + ", obsidianRobot=" + obsidianRobot
                + ", geodeRobot=" + geodeRobot + "]";
    }

    public Material calculateMaximals() {
        Material max = new Material(0, 0, 0, 0);

        List<Reciepe> reciepes = new ArrayList<>();
        reciepes.add(clayRobot);
        reciepes.add(obsidianRobot);
        reciepes.add(geodeRobot);

        for (Reciepe reciepe : reciepes) {
            max.clay = Math.max(max.clay, reciepe.cost.clay);
            max.ore = Math.max(max.ore, reciepe.cost.ore);
            max.obsidian = Math.max(max.obsidian, reciepe.cost.obsidian);
        }
        return max;
    }

    public Material getMaximals() {
        return maximals;
    }
}
