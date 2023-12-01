package year2022.day02;

public class Round {
    Move moveA;
    Move moveB;


    public Round(final Move moveA, final Move moveB) {
        super();
        this.moveA = moveA;
        this.moveB = moveB;
    }

    public static Round setRoundWithFirstRule(final String move1, final String move2) {
        Move moveA;
        Move moveB;

        switch (move1) {
            case "A": {
                moveA = Move.ROCK;
                break;
            }
            case "B": {
                moveA = Move.PAPER;
                break;
            }
            case "C": {
                moveA = Move.SCISSORS;
                break;
            }
            default:
                throw new IllegalArgumentException("Unexpected value: " + move2);
        }

        switch (move2) {
            case "X": {
                moveB = Move.ROCK;
                break;
            }
            case "Y": {
                moveB = Move.PAPER;
                break;
            }
            case "Z": {
                moveB = Move.SCISSORS;
                break;
            }
            default:
                throw new IllegalArgumentException("Unexpected value: " + move2);
        }

        return new Round(moveA, moveB);

    }

    public int calculatePoint() {
        int point = 0;
        switch (moveB) {
            case ROCK: {
                point += 1;
                switch (moveA) {
                    case ROCK: {
                        point += 3;
                        break;
                    }
                    case PAPER: {
                        point += 0;
                        break;
                    }
                    case SCISSORS: {
                        point += 6;
                        break;
                    }
                    default:
                        throw new IllegalArgumentException("Unexpected value: " + moveA);
                }
                break;
            }
            case PAPER: {
                point += 2;
                switch (moveA) {
                    case ROCK: {
                        point += 6;
                        break;
                    }
                    case PAPER: {
                        point += 3;
                        break;
                    }
                    case SCISSORS: {
                        point += 0;
                        break;
                    }
                    default:
                        throw new IllegalArgumentException("Unexpected value: " + moveA);
                }
                break;
            }
            case SCISSORS: {
                point += 3;
                switch (moveA) {
                    case ROCK: {
                        point += 0;
                        break;
                    }
                    case PAPER: {
                        point += 6;
                        break;
                    }
                    case SCISSORS: {
                        point += 3;
                        break;
                    }
                    default:
                        throw new IllegalArgumentException("Unexpected value: " + moveA);
                }
                break;
            }
            default:
                throw new IllegalArgumentException("Unexpected value: " + moveB);
        }


        return point;
    }


    public static Round setRoundWithSecondRule(final String move1, final String move2) {
        Move moveA;
        Move moveB;

        switch (move1) {
            case "A": {
                moveA = Move.ROCK;
                switch (move2) {
                    case "X": {
                        moveB = Move.SCISSORS;
                        break;
                    }
                    case "Y": {
                        moveB = Move.ROCK;
                        break;
                    }
                    case "Z": {
                        moveB = Move.PAPER;
                        break;
                    }
                    default:
                        throw new IllegalArgumentException("Unexpected value: " + move2);
                }
                break;
            }
            case "B": {
                moveA = Move.PAPER;
                switch (move2) {
                    case "X": {
                        moveB = Move.ROCK;
                        break;
                    }
                    case "Y": {
                        moveB = Move.PAPER;
                        break;
                    }
                    case "Z": {
                        moveB = Move.SCISSORS;
                        break;
                    }
                    default:
                        throw new IllegalArgumentException("Unexpected value: " + move2);
                }
                break;
            }
            case "C": {
                moveA = Move.SCISSORS;
                switch (move2) {
                    case "X": {
                        moveB = Move.PAPER;
                        break;
                    }
                    case "Y": {
                        moveB = Move.SCISSORS;
                        break;
                    }
                    case "Z": {
                        moveB = Move.ROCK;
                        break;
                    }
                    default:
                        throw new IllegalArgumentException("Unexpected value: " + move2);
                }
                break;
            }
            default:
                throw new IllegalArgumentException("Unexpected value: " + move2);
        }


        return new Round(moveA, moveB);

    }


}
