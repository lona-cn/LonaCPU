package com.lonacloud.lcpu.main;

import com.lonacloud.lcpu.asm.Assembler;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public abstract class ASMMain {

    public static void main(String[] args) throws IOException {
        System.out.println(Arrays.toString(Assembler.asmFile(new File(args[0]))));
    }
}
