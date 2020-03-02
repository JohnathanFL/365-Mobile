package edu.mnstate.assign5;

public interface PriceConsumer {
    enum GPU {
        AMD, Intel, NVIDIA
    }

    void setGPU(GPU gpu);
    void setRAM(int amt);
    void setNumBatteries(int num);
    void setDate(int month, int year);
}
