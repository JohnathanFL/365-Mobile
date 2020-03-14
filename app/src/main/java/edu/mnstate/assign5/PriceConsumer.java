/**
 * Calculate the monthly payment amount for the purchase of a new computer with customization.
 * @author Johnathan Lee
 * @date 2020-03-16
 */

package edu.mnstate.assign5;

/**
 * Should be implemented by things that display the monthly payment amount to the user
 */
public interface PriceConsumer {
    enum GPU {
        AMD, Intel, NVIDIA
    }

    /**
     * A globbed setter for computer details.
     * Prefer using the granular options.
     * @param gpu The gpu of the new computer
     * @param ram The amount of ram in the new computer {8, 16, 32, 64}
     * @param numBats The number of batteris in the new computer [1, inf)
     * @param month The month we expect to pay it off in
     * @param year The year we expect to pay it off in
     */
    void setAll(GPU gpu, int ram, int numBats, int month, int year);

    // See the globbed setter for documentation on the below


    void setGPU(GPU gpu);
    void setRAM(int amt);
    void setNumBatteries(int num);
    void setDate(int month, int year);
}
