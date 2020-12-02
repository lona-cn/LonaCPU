package com.lonacloud.lcpu.register;

import com.lonacloud.lcpu.instruction.util.RegType;

public class CPURegGroup implements ICPURegGroup {
    public final Reg64[] common = new Reg64[8];
    public final Reg64 s0 = new Reg64();
    public final Reg64 ip = new Reg64();
    public final Reg64 sp = new Reg64(), bp = new Reg64();
    public final Reg64[] ar = new Reg64[3];
    public final Reg64 ir = new Reg64();
    public final Reg64 itnr = new Reg64();

    public CPURegGroup() {
        allocateReg(common);
        allocateReg(ar);
    }

    private static void allocateReg(Reg64[] regArray) {
        for (int i = 0; i < regArray.length; i++) {
            regArray[i] = new Reg64();
        }
    }

    @Override
    public Reg64 getCommonReg(short number) {
        return common[number];
    }

    @Override
    public Reg64 getIP() {
        return ip;
    }

    @Override
    public Reg64 getState() {
        return this.s0;
    }

    @Override
    public Reg64 getSP() {
        return this.sp;
    }

    @Override
    public Reg64 getBP() {
        return bp;
    }

    @Override
    public Reg64 getAR(short number) {
        return ar[number];
    }

    @Override
    public Reg64 getReg(RegType regType) {
        switch (regType) {
            case R0:
                return common[0];
            case R1:
                return common[1];
            case R2:
                return common[2];
            case R3:
                return common[3];
            case R4:
                return common[4];
            case R5:
                return common[5];
            case R6:
                return common[6];
            case R7:
                return common[7];
            case IP:
                return ip;
            case SP:
                return sp;
            case BP:
                return bp;
            case AR0:
                return ar[0];
            case IR:
                return ir;
            case ITNR:
                return itnr;
            default:
                return null;
        }
    }
}
