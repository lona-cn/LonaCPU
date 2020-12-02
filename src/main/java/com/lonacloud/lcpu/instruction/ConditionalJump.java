package com.lonacloud.lcpu.instruction;

import com.lonacloud.lcpu.instruction.util.RegType;
import com.lonacloud.lcpu.register.CPURegGroup;

public class ConditionalJump {
    protected CPURegGroup regGroup;

    public ConditionalJump(CPURegGroup regGroup) {
        this.regGroup = regGroup;
    }

    public void jmp(RegType reg) {
        setRegData(RegType.IP, getRegData(reg));
    }

    public void ja(RegType regA, RegType regB) {
        long a = getRegData(regA);
        long b = getRegData(regB);
        if (a > b) {
            jmp(RegType.AR0);
        }
    }

    public void je(RegType regA, RegType regB) {
        long a = getRegData(regA);
        long b = getRegData(regB);
        if (a == b) {
            jmp(RegType.AR0);
        }

    }

    private long getRegData(RegType regType) {
        return regGroup.getReg(regType).data;
    }

    private void setRegData(RegType regType, long data) {
        regGroup.getReg(regType).data = data;
    }
}
