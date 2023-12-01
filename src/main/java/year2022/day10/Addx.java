package year2022.day10;

public class Addx implements IInstruction {

    int value;


    public Addx(final int value) {
        super();
        this.value = value;
    }


    @Override
    public void modifyReigisterX(final CPU cpu) {
        cpu.increaseCycle();
        cpu.X += value;
        cpu.increaseCycle();
    }

}
