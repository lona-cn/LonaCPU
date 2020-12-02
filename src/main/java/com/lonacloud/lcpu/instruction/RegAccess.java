package com.lonacloud.lcpu.instruction;

import com.lonacloud.lcpu.instruction.util.RegType;
import com.lonacloud.lcpu.register.CPURegGroup;

public class RegAccess {
    protected CPURegGroup regGroup;


    public RegAccess(CPURegGroup regGroup) {
        this.regGroup = regGroup;
    }

    public void mov(RegType to, RegType from) {
        setRegData(to, getRegData(from));
    }

    public void reset(RegType reg) {
        setRegData(reg, 0x00000000_00000000L);
    }

    public void inc(RegType reg) {
        setRegData(reg, getRegData(reg) + 1L);
    }

    public void loadImm8(RegType reg, byte data) {
        setRegData(reg, 0x00000000_000000ffL & data);
    }

    public void loadImm16(RegType reg, short data) {
        setRegData(reg, 0x00000000_0000ffffL & data);
    }

    public void loadImm32(RegType reg, int data) {
        setRegData(reg, 0x00000000_ffffffffL & data);
    }

    public void loadImm64(RegType reg, long data) {
        setRegData(reg, data);
    }

    private long getRegData(RegType regType) {
        return regGroup.getReg(regType).data;
    }

    private void setRegData(RegType regType, long data) {
        regGroup.getReg(regType).data = data;
    }
}
