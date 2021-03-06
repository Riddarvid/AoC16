package aoc.days.day25;

import java.util.*;

public class VM {
    private final Map<Character, Long> registers;
    private int pc;
    private boolean done = false;
    private boolean succeeded = true;
    private int lastOut;
    private int counter;

    public VM(Collection<Character> registerNames) {
        pc = 0;
        registers = new HashMap<>();
        lastOut = 1;
        counter = 0;
        for (char c : registerNames) {
            registers.put(c, 0L);
        }
    }

    public boolean execute(List<Instruction> instructions) {
        while (pc >= 0 && pc < instructions.size() && !done) {
            Instruction instruction = instructions.get(pc);
            List<String> arguments = instruction.getArguments();
            if (pc == 8) {
                System.out.print("");
            }
            if (pc == 20) {
                System.out.print("");
            }
            if (pc == 26) {
                System.out.print("");
            }
            switch (instruction.getOperation()) {
                case CPY:
                    copy(arguments.get(0), arguments.get(1));
                    break;
                case INC:
                    inc(arguments.get(0));
                    break;
                case DEC:
                    dec(arguments.get(0));
                    break;
                case JNZ:
                    jnz(arguments.get(0), arguments.get(1));
                    break;
                case TGL:
                    toggle(arguments.get(0), instructions);
                    break;
                case OUT:
                    out(arguments.get(0));
                    break;
                default:
                    throw new InputMismatchException();
            }
        }
        return succeeded;
    }

    private void out(String x) {
        long value;
        if (Character.isLetter(x.charAt(0))) {
            value = getRegister(x.charAt(0));
        } else {
            value = Long.parseLong(x);
        }
        if ((lastOut == 0 && value != 1) || (lastOut == 1 && value != 0)) {
            succeeded = false;
            done = true;
        } else {
            counter++;
            if (counter == 100) {
                done = true;
            }
            lastOut = (int)value;
        }
        pc++;
    }

    private void toggle(String x, List<Instruction> instructions) {
        long value;
        if (Character.isLetter(x.charAt(0))) {
            value = getRegister(x.charAt(0));
        } else {
            value = Long.parseLong(x);
        }
        if (pc + value >= 0 && pc + value < instructions.size()) {
            instructions.get(pc + (int)value).toggle();
        }
        pc++;
    }

    private void jnz(String x, String y) {
        long condition;
        if (Character.isLetter(x.charAt(0))) {
            condition = getRegister(x.charAt(0));
        } else {
            condition = Long.parseLong(x);
        }
        if (condition != 0) {
            int value;
            if (Character.isLetter(y.charAt(0))) {
                value = (int)getRegister(y.charAt(0));
            } else {
                value = Integer.parseInt(y);
            }
            pc += value;
        } else {
            pc++;
        }
    }

    private void inc(String x) {
        if (Character.isLetter(x.charAt(0))) {
            long value = getRegister(x.charAt(0));
            setRegister(x.charAt(0), value + 1);
        }
        pc++;
    }

    private void dec(String x) {
        if (Character.isLetter(x.charAt(0))) {
            long value = getRegister(x.charAt(0));
            setRegister(x.charAt(0), value - 1);
        }
        pc++;
    }

    private void copy(String x, String y) {
        if (!Character.isLetter(y.charAt(0))) {
            pc++;
            return;
        }
        long value;
        if (Character.isLetter(x.charAt(0))) {
            value = getRegister(x.charAt(0));

        } else {
            value = Long.parseLong(x);
        }
        setRegister(y.charAt(0), value);
        pc++;
    }

    public long getRegister(char register) {
        if (!registers.containsKey(register)) {
            throw new InputMismatchException("No such register");
        }
        return registers.get(register);
    }

    public void setRegister(char register, long value) {
        registers.put(register, value);
    }
}
