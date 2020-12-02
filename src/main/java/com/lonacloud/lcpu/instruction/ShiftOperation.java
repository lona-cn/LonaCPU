package com.lonacloud.lcpu.instruction;

import com.lonacloud.lcpu.instruction.util.RegType;
import com.lonacloud.lcpu.register.CPURegGroup;

public class ShiftOperation {
    protected CPURegGroup regGroup;

    public ShiftOperation(CPURegGroup regGroup) {
        this.regGroup = regGroup;
    }

    private long getRegData(RegType regType) {
        return regGroup.getReg(regType).data;
    }

    private void setRegData(RegType regType, long data) {
        regGroup.getReg(regType).data = data;
    }

    public void sal(RegType reg, byte size) {
        setRegData(reg, getRegData(reg) << size);
    }

    public void sar(RegType reg, byte size) {
        setRegData(reg, getRegData(reg) >> size);
    }

    public void shl(RegType reg, byte size) {
        sal(reg, size);
    }

    public void shr(RegType reg, byte size) {
        setRegData(reg, getRegData(reg) >>> size);
    }
}
