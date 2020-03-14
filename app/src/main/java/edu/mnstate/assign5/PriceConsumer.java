package edu.mnstate.assign5;

public interface PriceConsumer {
    enum GPU {
        AMD, Intel, NVIDIA
    }

    // The granular ones are preferred. This is here for complete compliance with requirements
    void setAll(GPU gpu, int ram, int numBats, int month, int year);

    void setGPU(GPU gpu);
    void setRAM(int amt);
    void setNumBatteries(int num);
    void setDate(int month, int year);
}
