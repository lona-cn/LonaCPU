package com.lonacloud.lcpu.instruction;

import com.lonacloud.lcpu.instruction.util.RegType;

public class SubRoutineOperation {
    protected StackOperation stackOperation;
    protected ConditionalJump conditionalJump;

    public SubRoutineOperation(StackOperation stackOperation, ConditionalJump conditionalJump) {
        this.stackOperation = stackOperation;
        this.conditionalJump = conditionalJump;
    }

    public void call(RegType reg) {
        stackOperation.push(RegType.IP);
        conditionalJump.jmp(reg);
    }

    public void ret() {
        stackOperation.pop(RegType.IP);
    }
}
