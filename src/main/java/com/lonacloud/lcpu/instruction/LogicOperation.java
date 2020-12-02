package com.lonacloud.lcpu.instruction;

import com.lonacloud.lcpu.instruction.util.RegType;
import com.lonacloud.lcpu.register.CPURegGroup;

public class LogicOperation {
    protected CPURegGroup regGroup;

    public LogicOperation(CPURegGroup regGroup) {
        this.regGroup = regGroup;
    }

    public void and(RegType regA, RegType regB) {
        setRegData(regA, getRegData(regA) & getRegData(regB));
    }

    public void or(RegType regA, RegType regB) {
        setRegData(regA, getRegData(regA) | getRegData(regB));
    }

    public void xor(RegType regA, RegType regB) {
        setRegData(regA, getRegData(regA) ^ getRegData(regB));
    }

    public void not(RegType regA) {
        setRegData(regA, ~getRegData(regA));
    }

    private long getRegData(RegType regType) {
        return regGroup.getReg(regType).data;
    }

    private void setRegData(RegType regType, long data) {
        regGroup.getReg(regType).data = data;
    }
}
