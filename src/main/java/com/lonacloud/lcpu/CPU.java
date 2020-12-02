package com.lonacloud.lcpu;

import com.lonacloud.lcpu.bus.MemBus;
import com.lonacloud.lcpu.imc.IMC;
import com.lonacloud.lcpu.instruction.*;
import com.lonacloud.lcpu.instruction.util.RegType;
import com.lonacloud.lcpu.register.CPURegGroup;

public class CPU implements Runnable {
    protected CPURegGroup regGroup = new CPURegGroup();
    protected MemBus memBus;
    protected IMC imc;
    protected MemAccess memAccess;
    protected RegAccess regAccess;
    protected LogicOperation logicOperation;
    protected IntegerOperation integerOperation;
    protected ConditionalJump conditionalJump;
    protected ShiftOperation shiftOperation;
    protected StackOperation stackOperation;
    protected SubRoutineOperation subRoutineOperation;
    protected InterruptOperation interruptOperation;
    //statistic

    public CPU(MemBus memBus) {
        this.memBus = memBus;
        imc = new IMC(memBus);
        memAccess = new MemAccess(regGroup, imc);
        regAccess = new RegAccess(regGroup);
        logicOperation = new LogicOperation(regGroup);
        integerOperation = new IntegerOperation(regGroup);
        conditionalJump = new ConditionalJump(regGroup);
        shiftOperation = new ShiftOperation(regGroup);
        stackOperation = new StackOperation(regGroup, memAccess);
        subRoutineOperation = new SubRoutineOperation(stackOperation, conditionalJump);
        interruptOperation = new InterruptOperation(regGroup, subRoutineOperation);
    }

    @Override
    public void run() {
        for (; ; ) {
            memBus.setAddress(regGroup.ip.data);
            regGroup.ip.data = regGroup.ip.data + 3;
            byte[] bytes = memBus.load(3);
            byte op = bytes[0];
            byte lReg = bytes[1];
            byte rReg = bytes[2];
            execute(op, lReg, rReg);
        }
    }

    private void execute(byte op, byte lReg, byte rReg) {
        switch (op) {
            case 0x00:
                memAccess.load8(RegType.from(lReg), RegType.from(rReg));
                break;
            case 0x01:
                memAccess.load16(RegType.from(lReg), RegType.from(rReg));
                break;
            case 0x02:
                memAccess.load32(RegType.from(lReg), RegType.from(rReg));
                break;
            case 0x03:
                memAccess.load64(RegType.from(lReg), RegType.from(rReg));
                break;
            case 0x10:
                memAccess.store8(RegType.from(lReg), RegType.from(rReg));
                break;
            case 0x11:
                memAccess.store16(RegType.from(lReg), RegType.from(rReg));
                break;
            case 0x12:
                memAccess.store32(RegType.from(lReg), RegType.from(rReg));
                break;
            case 0x13:
                memAccess.store64(RegType.from(lReg), RegType.from(rReg));
                break;
            case 0x20:
                regAccess.mov(RegType.from(lReg), RegType.from(rReg));
                break;
            case 0x22:
                regAccess.inc(RegType.from(lReg));
                break;
            case 0x23:
                regAccess.loadImm8(RegType.from(lReg), rReg);
                break;
            case 0x30:
                logicOperation.and(RegType.from(lReg), RegType.from(rReg));
                break;
            case 0x31:
                logicOperation.or(RegType.from(lReg), RegType.from(rReg));
                break;
            case 0x32:
                logicOperation.xor(RegType.from(lReg), RegType.from(rReg));
                break;
            case 0x33:
                logicOperation.not(RegType.from(lReg));
                break;
            case 0x40:
                integerOperation.add8(RegType.from(lReg), RegType.from(rReg));
                break;
            case 0x41:
                integerOperation.add16(RegType.from(lReg), RegType.from(rReg));
                break;
            case 0x42:
                integerOperation.add32(RegType.from(lReg), RegType.from(rReg));
                break;
            case 0x43:
                integerOperation.add64(RegType.from(lReg), RegType.from(rReg));
                break;
            case 0x44:
                integerOperation.sub8(RegType.from(lReg), RegType.from(rReg));
                break;
            case 0x45:
                integerOperation.sub16(RegType.from(lReg), RegType.from(rReg));
                break;
            case 0x46:
                integerOperation.sub32(RegType.from(lReg), RegType.from(rReg));
                break;
            case 0x47:
                integerOperation.sub64(RegType.from(lReg), RegType.from(rReg));
                break;
            case 0x50:
                conditionalJump.jmp(RegType.from(lReg));
                break;
            case 0x51:
                conditionalJump.ja(RegType.from(lReg), RegType.from(rReg));
                break;
            case 0x52:
                conditionalJump.je(RegType.from(lReg), RegType.from(rReg));
                break;
            case 0x53:
                shiftOperation.sal(RegType.from(lReg), rReg);
                break;
            case 0x54:
                shiftOperation.sar(RegType.from(lReg), rReg);
                break;
            case 0x55:
                shiftOperation.shl(RegType.from(lReg), rReg);
                break;
            case 0x56:
                shiftOperation.shr(RegType.from(lReg), rReg);
                break;
            case 0x60:
                stackOperation.push(RegType.from(lReg));
                break;
            case 0x61:
                stackOperation.pop(RegType.from(lReg));
                break;
            case 0x70:
                subRoutineOperation.call(RegType.from(lReg));
                break;
            case 0x71:
                subRoutineOperation.ret();
                break;
            case (byte) 0x80:
                interruptOperation.interrupt(lReg);
                break;
            case (byte) 0x81:
                interruptOperation.iret();
                break;
            default:
                break;
        }
    }

    public CPURegGroup getRegGroup() {
        return regGroup;
    }
}
