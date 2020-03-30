package edu.mnstate.jz1652ve.majorproject2;



/**
 * An interface for anything which can available actions based on context
 */
public interface Contextable {
    /**
     * The action to be performed
     */
    interface ContextAction {
        void doAction();
    }

    /**
     * Add a new action to the menu
     * @param name The name to be displayed in the menu
     * @param action The action to be run when clicked
     * @return The ID of the action, for use in changeAction
     */
    int addAction(String name, ContextAction action);

    /**
     * Change what an action does
     * @param id The id to change, as returned by addAction
     * @param action The new action. Replaces the old entirely.
     */
    void changeAction(int id, ContextAction action);

    /**
     * Removes all actions from the context menu
     */
    void clearActions();
}
