package edu.mnstate.jz1652ve.majorproject2;


import androidx.fragment.app.Fragment;

/**
 * Represents the activity that holds the fragments
 */
public interface Overlord {
    /**
     * Change out the caller with a new fragment
     * @param frag The fragment to exchange the caller for.
     */
    void swapFrag(Fragment frag);
}
