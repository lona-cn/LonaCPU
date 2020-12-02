package com.lonacloud.lcpu.instruction;

import com.lonacloud.lcpu.instruction.util.RegType;
import com.lonacloud.lcpu.register.CPURegGroup;

public class InterruptOperation {
    protected CPURegGroup regGroup;
    protected SubRoutineOperation subRoutineOperation;

    public InterruptOperation(CPURegGroup regGroup, SubRoutineOperation subRoutineOperation) {
        this.regGroup = regGroup;
        this.subRoutineOperation = subRoutineOperation;
    }

    private long getRegData(RegType regType) {
        return regGroup.getReg(regType).data;
    }

    private void setRegData(RegType regType, long data) {
        regGroup.getReg(regType).data = data;
    }

    public void interrupt(byte number) {
        long address = getRegData(RegType.IR) + number * 8;
        setRegData(RegType.ITNR, address);
        subRoutineOperation.call(RegType.ITNR);
    }

    public void iret() {
        subRoutineOperation.ret();
    }
}
