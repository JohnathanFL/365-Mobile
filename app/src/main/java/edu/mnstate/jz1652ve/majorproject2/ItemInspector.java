/**
 * A simple grocery list app.
 *
 * Johnathan Lee
 * MSUM Mobile App Dev
 * Due 04/03/20
 */

package edu.mnstate.jz1652ve.majorproject2;

import android.os.Bundle;

public interface ItemInspector {
    /**
     * Inspect the item with that name in that list
     * @param name
     * @param list
     */
    void inspect(Bundle clicked);
}
