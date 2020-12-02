package com.lonacloud.lcpu.imc;

import com.lonacloud.lcpu.bus.MemBus;

import java.nio.ByteBuffer;

public class IMC {
    MemBus memBus;

    public IMC(MemBus memBus) {
        this.memBus = memBus;
    }

    public long load(long address) {
        memBus.setAddress(address);
        byte[] bytes = memBus.load(8);
        return ByteBuffer.wrap(bytes).getLong();
    }

    public void store(long address, long data) {
        memBus.setAddress(address);
        memBus.store(ByteBuffer.allocate(Long.SIZE / Byte.SIZE).putLong(data).array());
    }
}
