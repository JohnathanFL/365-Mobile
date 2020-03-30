package edu.mnstate.jz1652ve.majorproject2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.time.LocalDate;

/**
 * Represents the need to buy a certain product in a certain quantity
 */
public class Order {

    @NonNull
    Product prod;
    int quant;

    // If null, use default date
    @Nullable
    LocalDate by;
}
