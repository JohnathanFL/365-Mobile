package edu.mnstate.jz1652ve.majorproject2;

import java.math.BigDecimal;
import java.util.Currency;

/**
 * Represents a single product. Has no information about when it will be bought, etc
 */
public class Product {
    String name, desc, category;
    // Stored using the last 2 decimal places as cents
    // I do this instead of BigDecimal because BD is a pain to work with.
    int price;
}
