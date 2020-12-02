package com.lonacloud.lcpu.main;

import com.lonacloud.lcpu.CPU;
import com.lonacloud.lcpu.asm.Assembler;
import com.lonacloud.lcpu.bus.MemBus;

import java.io.File;
import java.io.IOException;

public class CPUMain {
    public static void main(String[] args) throws IOException {
        //从args[0]读入汇编文件来执行
        if (args.length == 0) {
            throw new IOException("必须指定用于执行的汇编文件");
        }
        byte[] mem = Assembler.asmFile(new File(args[0]));
        MemBus memBus = new MemBus(mem);
        new CPU(memBus).run();
    }
}
