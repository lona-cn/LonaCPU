package com.lonacloud.lcpu.register;

import com.lonacloud.lcpu.instruction.util.RegType;

public interface ICPURegGroup {

    Reg64 getCommonReg(final short number);

    Reg64 getIP();

    Reg64 getState();

    Reg64 getSP();

    Reg64 getBP();

    Reg64 getAR(final short number);

    Reg64 getReg(RegType regType);
}
