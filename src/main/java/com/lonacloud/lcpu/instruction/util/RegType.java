package com.lonacloud.lcpu.instruction.util;

public enum RegType {
    //common
    R0((byte) 0),
    R1((byte) 1),
    R2((byte) 2),
    R3((byte) 3),
    R4((byte) 4),
    R5((byte) 5),
    R6((byte) 6),
    R7((byte) 7),
    //IP
    IP((byte) 0x20),
    //stack
    SP((byte) 0x30),
    BP((byte) 0x31),
    //address
    AR0((byte) 0x40),
    //interrupt
    IR((byte) 0x50),
    //internal
    ITNR((byte)0xf0),
    ;
    public final byte id;

    RegType(byte id) {
        this.id = id;
    }

    public static RegType from(final byte id) {
        for (RegType reg : RegType.values()) {
            if (reg.id == id) {
                return reg;
            }
        }
        return null;
    }
}
