package year2023.day10;

import java.util.Objects;

import helper.Direction;

public class Tile {
    int x;
    int y;
    char symbol;

    public Tile(int x, int y, char symbol) {
        super();
        this.x = x;
        this.y = y;
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return "Tile [x=" + x + ", y=" + y + ", symbol=" + symbol + "]";
    }

    public TileDirection getNextTile(Direction d) {
        switch (symbol) {
            case '|':
                switch (d) {
                    case DOWN:
                        return new TileDirection(x, y, Direction.DOWN);

                    case UP:
                        return new TileDirection(x, y, Direction.UP);
                    default:
                        break;
                }
                break;
            case '-':
                switch (d) {
                    case RIGHT:

                        return new TileDirection(x, y, Direction.RIGHT);
                    case LEFT:

                        return new TileDirection(x, y, Direction.LEFT);
                    default:
                        break;
                }
                break;

            case 'L':
                switch (d) {
                    case DOWN:

                        return new TileDirection(x, y, Direction.RIGHT);
                    case LEFT:

                        return new TileDirection(x, y, Direction.UP);
                    default:
                        break;
                }
                break;
            case 'J':
                switch (d) {
                    case DOWN:

                        return new TileDirection(x, y, Direction.LEFT);
                    case RIGHT:

                        return new TileDirection(x, y, Direction.UP);
                    default:
                        break;
                }
                break;
            case '7':
                switch (d) {
                    case UP:

                        return new TileDirection(x, y, Direction.LEFT);
                    case RIGHT:

                        return new TileDirection(x, y, Direction.DOWN);
                    default:
                        break;
                }
                break;
            case 'F':
                switch (d) {
                    case UP:

                        return new TileDirection(x, y, Direction.RIGHT);
                    case LEFT:

                        return new TileDirection(x, y, Direction.DOWN);
                    default:
                        break;
                }
                break;

            default:
                return null;
        }
        return null;

    }

    public void move(final Direction d) {
        switch (d) {
            case UP: {
                y++;
                break;
            }
            case DOWN: {
                y--;
                break;
            }
            case RIGHT: {
                x++;
                break;
            }
            case LEFT: {
                x--;
                break;
            }
            default:
                throw new IllegalArgumentException("Unexpected value: " + d);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Tile other = (Tile)obj;
        return x == other.x && y == other.y;
    }


}
