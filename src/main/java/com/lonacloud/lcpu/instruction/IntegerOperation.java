package com.lonacloud.lcpu.instruction;

import com.lonacloud.lcpu.instruction.util.RegType;
import com.lonacloud.lcpu.register.CPURegGroup;

public class IntegerOperation {
    protected CPURegGroup regGroup;

    public IntegerOperation(CPURegGroup regGroup) {
        this.regGroup = regGroup;
    }

    public void add8(RegType regA, RegType regB) {
        long data = getRegData(regA) & 0x00000000_000000ffL + getRegData(regB) & 0x00000000_000000ffL;
        setRegData(regA, data);
    }

    public void add16(RegType regA, RegType regB) {
        long data = getRegData(regA) & 0x00000000_0000ffffL + getRegData(regB) & 0x00000000_0000ffffL;
        setRegData(regA, data);
    }

    public void add32(RegType regA, RegType regB) {
        long data = getRegData(regA) & 0x00000000_ffffffffL + getRegData(regB) & 0x00000000_ffffffffL;
        setRegData(regA, data);
    }

    public void add64(RegType regA, RegType regB) {
        long data = getRegData(regA) + getRegData(regB);
        setRegData(regA, data);
    }

    public void sub8(RegType regA, RegType regB) {
        long data = getRegData(regA) & 0x00000000_000000ffL - getRegData(regB) & 0x00000000_000000ffL;
        setRegData(regA, data);
    }

    public void sub16(RegType regA, RegType regB) {
        long data = getRegData(regA) & 0x00000000_0000ffffL - getRegData(regB) & 0x00000000_0000ffffL;
        setRegData(regA, data);
    }

    public void sub32(RegType regA, RegType regB) {
        long data = getRegData(regA) & 0x00000000_ffffffffL - getRegData(regB) & 0x00000000_ffffffffL;
        setRegData(regA, data);
    }

    public void sub64(RegType regA, RegType regB) {
        long data = getRegData(regA) - getRegData(regB);
        setRegData(regA, data);
    }

    private long getRegData(RegType regType) {
        return regGroup.getReg(regType).data;
    }

    private void setRegData(RegType regType, long data) {
        regGroup.getReg(regType).data = data;
    }
}
