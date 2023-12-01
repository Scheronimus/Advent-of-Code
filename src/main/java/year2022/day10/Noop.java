package year2022.day10;

public class Noop implements IInstruction {

    @Override
    public void modifyReigisterX(final CPU cpu) {
        cpu.increaseCycle();
    }

}
