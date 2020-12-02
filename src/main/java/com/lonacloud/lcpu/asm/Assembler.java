package com.lonacloud.lcpu.asm;

import com.lonacloud.lcpu.instruction.util.RegType;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class Assembler {

    private static byte[] asmLine(String line) {
        line = line.toUpperCase().replaceAll("\\s+", " ");
        int commentIndex = line.indexOf("//");
        String cmd = commentIndex == -1 ? line : line.substring(0, commentIndex);
        String[] cmds = cmd.split(" ");
        if (cmd.equals("") || cmds.length == 0) return null;
        String opName = cmds[0];
        byte op;
        byte lReg = 0;
        byte rReg = 0;
        switch (opName) {
            case "LOAD8":
                op = 0x00;
                lReg = RegType.valueOf(cmds[1]).id;
                rReg = RegType.valueOf(cmds[2]).id;
                break;
            case "LOAD16":
                op = 0x01;
                lReg = RegType.valueOf(cmds[1]).id;
                rReg = RegType.valueOf(cmds[2]).id;
                break;
            case "LOAD32":
                op = 0x02;
                lReg = RegType.valueOf(cmds[1]).id;
                rReg = RegType.valueOf(cmds[2]).id;
                break;
            case "LOAD64":
                op = 0x03;
                lReg = RegType.valueOf(cmds[1]).id;
                rReg = RegType.valueOf(cmds[2]).id;
                break;
            case "STORE8":
                op = 0x10;
                lReg = RegType.valueOf(cmds[1]).id;
                rReg = RegType.valueOf(cmds[2]).id;
                break;
            case "STORE16":
                op = 0x11;
                lReg = RegType.valueOf(cmds[1]).id;
                rReg = RegType.valueOf(cmds[2]).id;
                break;
            case "STORE32":
                op = 0x12;
                lReg = RegType.valueOf(cmds[1]).id;
                rReg = RegType.valueOf(cmds[2]).id;
                break;
            case "STORE64":
                op = 0x13;
                lReg = RegType.valueOf(cmds[1]).id;
                rReg = RegType.valueOf(cmds[2]).id;
                break;
            case "MOV":
                op = 0x20;
                lReg = RegType.valueOf(cmds[1]).id;
                rReg = RegType.valueOf(cmds[2]).id;
                break;
            case "RESET":
                op = 0x21;
                lReg = RegType.valueOf(cmds[1]).id;
                break;
            case "INC":
                op = 0x22;
                lReg = RegType.valueOf(cmds[1]).id;
                break;
            case "LIMM8":
                op = 0x23;
                lReg = RegType.valueOf(cmds[1]).id;
                rReg = parseByte(cmds[2]);
                break;
            case "LIMM16":
                try {
                    return limm16(cmds[1], parseShort(cmds[2]));
                } catch (IOException ignored) {
                }
            case "LIMM32":
                try {
                    return limm32(cmds[1], parseInt(cmds[2]));
                } catch (IOException ignored) {
                }
            case "LIMM64":
                try {
                    return limm64(cmds[1], parseLong(cmds[2]));
                } catch (IOException ignored) {
                }
            case "AND":
                op = 0x30;
                lReg = RegType.valueOf(cmds[1]).id;
                rReg = RegType.valueOf(cmds[2]).id;
                break;
            case "OR":
                op = 0x31;
                lReg = RegType.valueOf(cmds[1]).id;
                rReg = RegType.valueOf(cmds[2]).id;
                break;
            case "XOR":
                op = 0x32;
                lReg = RegType.valueOf(cmds[1]).id;
                rReg = RegType.valueOf(cmds[2]).id;
                break;
            case "NOT":
                op = 0x33;
                lReg = RegType.valueOf(cmds[1]).id;
                break;
            case "ADD8":
                op = 0x40;
                lReg = RegType.valueOf(cmds[1]).id;
                rReg = RegType.valueOf(cmds[2]).id;
                break;
            case "ADD16":
                op = 0x41;
                lReg = RegType.valueOf(cmds[1]).id;
                rReg = RegType.valueOf(cmds[2]).id;
                break;
            case "ADD32":
                op = 0x42;
                lReg = RegType.valueOf(cmds[1]).id;
                rReg = RegType.valueOf(cmds[2]).id;
                break;
            case "ADD64":
                op = 0x43;
                lReg = RegType.valueOf(cmds[1]).id;
                rReg = RegType.valueOf(cmds[2]).id;
                break;
            case "SUB8":
                op = 0x44;
                lReg = RegType.valueOf(cmds[1]).id;
                rReg = RegType.valueOf(cmds[2]).id;
                break;
            case "SUB16":
                op = 0x45;
                lReg = RegType.valueOf(cmds[1]).id;
                rReg = RegType.valueOf(cmds[2]).id;
                break;
            case "SUB32":
                op = 0x46;
                lReg = RegType.valueOf(cmds[1]).id;
                rReg = RegType.valueOf(cmds[2]).id;
                break;
            case "SUB64":
                op = 0x47;
                lReg = RegType.valueOf(cmds[1]).id;
                rReg = RegType.valueOf(cmds[2]).id;
                break;
            case "JMP":
                op = 0x50;
                lReg = RegType.valueOf(cmds[1]).id;
                break;
            case "JA":
                op = 0x51;
                lReg = RegType.valueOf(cmds[1]).id;
                rReg = RegType.valueOf(cmds[2]).id;
                break;
            case "JE":
                op = 0x52;
                lReg = RegType.valueOf(cmds[1]).id;
                rReg = RegType.valueOf(cmds[2]).id;
                break;
            case "SAL":
                op = 0x53;
                lReg = RegType.valueOf(cmds[1]).id;
                rReg = parseByte(cmds[2]);
                break;
            case "SAR":
                op = 0x54;
                lReg = RegType.valueOf(cmds[1]).id;
                rReg = parseByte(cmds[2]);
                break;
            case "SHL":
                op = 0x55;
                lReg = RegType.valueOf(cmds[1]).id;
                rReg = parseByte(cmds[2]);
                break;
            case "SHR":
                op = 0x56;
                lReg = RegType.valueOf(cmds[1]).id;
                rReg = parseByte(cmds[2]);
                break;
            case "PUSH":
                op = 0x60;
                lReg = RegType.valueOf(cmds[1]).id;
                break;
            case "POP":
                op = 0x61;
                lReg = RegType.valueOf(cmds[1]).id;
                break;
            case "CALL":
                op = 0x70;
                lReg = RegType.valueOf(cmds[1]).id;
                break;
            case "RET":
                op = 0x71;
                break;
            case "INT":
                op = (byte) 0x80;
                lReg = parseByte(cmds[1]);
                break;
            case "IRET":
                op = (byte) 0x81;
                break;
            case "NOP":
                op = (byte) 0xff;
                break;
            default:
                System.out.println("unsupported command:" + line);
                return null;
        }
        return new byte[]{op, lReg, rReg};
    }

    public static byte[] asm(String[] lines) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];
            try {
                byte[] bytes = asmLine(line);
                if (bytes != null) {
                    byteArrayOutputStream.write(bytes);
                }
            } catch (Exception e) {
                throw new RuntimeException(String.format("can not convert line %d :%s", i, line), e);
            }
        }
        return byteArrayOutputStream.toByteArray();
    }

    private static byte parseByte(String s) {
        s = s.toLowerCase();
        if (s.startsWith("0x")) {
            return parseHexByte(s);
        } else {
            return (byte) Integer.parseUnsignedInt(s);
        }
    }

    private static byte parseHexByte(String s) {
        return (byte) Integer.parseUnsignedInt(s.replaceAll("^0[x|X]", ""), 16);
    }

    private static short parseShort(String s) {
        s = s.toLowerCase();
        if (s.startsWith("0x")) {
            return parseHexShort(s);
        } else {
            return (short) Integer.parseInt(s);
        }
    }

    protected static short parseHexShort(String s) {
        s = s.replaceAll("^0[x|X]", "");
        return (short) Integer.parseUnsignedInt(s, 16);
    }

    protected static int parseInt(String s) {
        s = s.toLowerCase();
        if (s.startsWith("0x")) {
            return parseHexInteger(s);
        } else {
            return (short) Integer.parseInt(s);
        }

    }

    protected static int parseHexInteger(String s) {
        s = s.replaceAll("^0[x|X]", "");
        return Integer.parseUnsignedInt(s, 16);
    }

    protected static long parseLong(String s) {
        s = s.toLowerCase();
        if (s.startsWith("0x")) {
            return parseHexLong(s);
        } else {
            return (byte) Long.parseLong(s);
        }
    }

    protected static long parseHexLong(String s) {
        s = s.replaceAll("^0[x|X]", "");
        return Long.parseUnsignedLong(s, 16);
    }

    public static byte[] asmFile(File file) throws IOException {
        Stream<String> lines = Files.lines(Paths.get(file.toURI()));
        String[] lineArray = lines.collect(Collectors.toList()).toArray(new String[0]);
        return asm(lineArray);
    }

    //伪指令
    private static byte[] limm16(String reg, short imm) throws IOException {
        final String internalReg = RegType.ITNR.name();
        ByteBuffer immByteBuffer = ByteBuffer.allocate(Short.SIZE / Byte.SIZE).putShort(imm);
        byte[] immBytes = immByteBuffer.array();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byteArrayOutputStream.write(Objects.requireNonNull(asmLine(String.format("%s %s 0x%x", "LIMM8", internalReg, immBytes[0]))));
        byteArrayOutputStream.write(Objects.requireNonNull(asmLine(String.format("%s %s 0x%x", "SAL", internalReg, (byte) 8))));
        byteArrayOutputStream.write(Objects.requireNonNull(asmLine(String.format("%s %s %s", "MOV", reg, internalReg))));
        byteArrayOutputStream.write(Objects.requireNonNull(asmLine(String.format("%s %s 0x%x", "LIMM8", internalReg, immBytes[1]))));
        byteArrayOutputStream.write(Objects.requireNonNull(asmLine(String.format("%s %s 0x%x", "SAL", internalReg, (byte) 0))));
        byteArrayOutputStream.write(Objects.requireNonNull(asmLine(String.format("%s %s %s", "ADD64", reg, internalReg))));
        return byteArrayOutputStream.toByteArray();
    }

    private static byte[] limm32(String reg, int imm) throws IOException {
        final String internalReg = RegType.ITNR.name();
        ByteBuffer immByteBuffer = ByteBuffer.allocate(Integer.SIZE / Byte.SIZE).putInt(imm);
        byte[] immBytes = immByteBuffer.array();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byteArrayOutputStream.write(Objects.requireNonNull(asmLine(String.format("%s %s 0x%x", "LIMM8", internalReg, immBytes[0]))));
        byteArrayOutputStream.write(Objects.requireNonNull(asmLine(String.format("%s %s 0x%x", "SAL", internalReg, (byte) 3 * 8))));
        byteArrayOutputStream.write(Objects.requireNonNull(asmLine(String.format("%s %s %s", "MOV", reg, internalReg))));
        for (int i = 2; i >= 0; i--) {
            byteArrayOutputStream.write(Objects.requireNonNull(asmLine(String.format("%s %s 0x%x", "LIMM8", internalReg, immBytes[3 - i]))));
            byteArrayOutputStream.write(Objects.requireNonNull(asmLine(String.format("%s %s 0x%x", "SAL", internalReg, (byte) i * 8))));
            byteArrayOutputStream.write(Objects.requireNonNull(asmLine(String.format("%s %s %s", "ADD64", reg, internalReg))));
        }
        return byteArrayOutputStream.toByteArray();
    }

    private static byte[] limm64(String reg, long imm) throws IOException {
        final String internalReg = RegType.ITNR.name();
        ByteBuffer immByteBuffer = ByteBuffer.allocate(Long.SIZE / Byte.SIZE).putLong(imm);
        byte[] immBytes = immByteBuffer.array();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byteArrayOutputStream.write(Objects.requireNonNull(asmLine(String.format("%s %s 0x%x", "LIMM8", internalReg, immBytes[0]))));
        byteArrayOutputStream.write(Objects.requireNonNull(asmLine(String.format("%s %s 0x%x", "SAL", internalReg, (byte) 7 * 8))));
        byteArrayOutputStream.write(Objects.requireNonNull(asmLine(String.format("%s %s %s", "MOV", reg, internalReg))));
        for (int i = 6; i >= 0; i--) {
            byteArrayOutputStream.write(Objects.requireNonNull(asmLine(String.format("%s %s 0x%x", "LIMM8", internalReg, immBytes[7 - i]))));
            byteArrayOutputStream.write(Objects.requireNonNull(asmLine(String.format("%s %s 0x%x", "SAL", internalReg, (byte) i * 8))));
            byteArrayOutputStream.write(Objects.requireNonNull(asmLine(String.format("%s %s %s", "ADD64", reg, internalReg))));
        }
        return byteArrayOutputStream.toByteArray();
    }
}
