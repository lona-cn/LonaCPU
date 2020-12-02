package com.lonacloud.lcpu.instruction;

import com.lonacloud.lcpu.instruction.util.RegType;
import com.lonacloud.lcpu.register.CPURegGroup;

public class StackOperation {
    protected CPURegGroup regGroup;
    protected MemAccess memAccess;

    public StackOperation(CPURegGroup regGroup, MemAccess memAccess) {
        this.regGroup = regGroup;
        this.memAccess = memAccess;
    }

    private long getRegData(RegType regType) {
        return regGroup.getReg(regType).data;
    }

    private void setRegData(RegType regType, long data) {
        regGroup.getReg(regType).data = data;
    }

    public void push(RegType reg) {
        memAccess.store64(reg, RegType.SP);
        setRegData(RegType.SP, getRegData(RegType.SP) + 8);
    }

    public void pop(RegType reg) {
        memAccess.load64(reg, RegType.SP);
        setRegData(RegType.SP, getRegData(RegType.SP) - 8);
    }
}
