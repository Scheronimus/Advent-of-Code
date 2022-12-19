package day19;

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


}
