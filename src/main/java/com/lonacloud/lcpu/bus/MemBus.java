package com.lonacloud.lcpu.bus;

import java.util.Arrays;

public class MemBus {

    protected long address;
    protected final byte[] mem;

    public MemBus() {
        this.address = 0;
        mem = new byte[512];
    }

    public MemBus(byte[] mem) {
        this.address = 0;
        this.mem = mem;
    }

    public void setAddress(long address) {
        this.address = address;
    }

    public byte[] load(int size) {
        if (address == 0xffffffff_ffffff00L) {
            return new byte[size];
        }
        byte[] ret = new byte[size];
        for (int i = 0; i < size; i++) {
            ret[i] = mem[(int) (address + i)];
        }
        return ret;
    }

    public void store(byte[] bytes) {
        if (address == 0xffffffff_ffffff00L) {
            System.out.printf("%s%n", Arrays.toString(bytes));
        } else {
            for (int i = 0; i < bytes.length; i++) {
                mem[(int) (address + i)] = bytes[i];
            }
        }
    }

}
