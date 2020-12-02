package com.lonacloud.lcpu.instruction;

import com.lonacloud.lcpu.imc.IMC;
import com.lonacloud.lcpu.register.ICPURegGroup;
import com.lonacloud.lcpu.instruction.util.RegType;

public class MemAccess {
    protected ICPURegGroup regGroup;
    protected IMC imc;

    public MemAccess(ICPURegGroup regGroup, IMC imc) {
        this.regGroup = regGroup;
        this.imc = imc;
    }

    public void load8(RegType register, RegType address) {
        long data = imcLoad(address) & 0x00_00_00_00__00_00_00_ffL;
        setRegData(register, (regGroup.getReg(register).data & 0xff_ff_ff_ff__ff_ff_ff_00L) + data);
    }

    public void load16(RegType register, RegType address) {
        long data = imcLoad(address) & 0x00_00_00_00__00_00_ff_ffL;
        setRegData(register, (regGroup.getReg(register).data & 0xff_ff_ff_ff__ff_ff_00_00L) + data);
    }

    public void load32(RegType register, RegType address) {
        long data = imcLoad(address) & 0x00_00_00_00__ff_f_ff_ffL;
        setRegData(register, (regGroup.getReg(register).data & 0xff_ff_ff_ff__00_00_00_00L) & data);
    }

    public void load64(RegType register, RegType address) {
        regGroup.getReg(register).data = imcLoad(address);
    }

    public void store8(RegType address, RegType register) {
        long memData = imcLoad(address);
        long regData = getRegData(register);
        imcStore(address, (memData & 0xffffffff_ffffff00L) + (regData & 0x00000000_000000ffL));
    }

    public void store16(RegType address, RegType register) {
        long memData = imcLoad(address);
        long regData = getRegData(register);
        imcStore(address, (memData & 0xffffffff_ffff0000L) + (regData & 0x00000000_0000ffffL));
    }

    public void store32(RegType address, RegType register) {
        long memData = imcLoad(address);
        long regData = getRegData(register);
        imcStore(address, (memData & 0xffffffff_00000000L) + (regData & 0x00000000_ffffffffL));
    }

    public void store64(RegType address, RegType register) {
        long regData = getRegData(register);
        imcStore(address, regData);
    }

    private long imcLoad(RegType address) {
        return imc.load(regGroup.getReg(address).data);
    }

    private void imcStore(RegType address, long data) {
        imc.store(getRegData(address), data);
    }

    private long getRegData(RegType regType) {
        return regGroup.getReg(regType).data;
    }

    private void setRegData(RegType regType, long data) {
        regGroup.getReg(regType).data = data;
    }
}
