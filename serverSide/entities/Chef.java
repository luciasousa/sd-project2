package serverSide.entities;
/**
 *   Chef thread.
 *
 *   Used to simulate the Chef life cycle.
 *   Static solution.
 */
public interface Chef {
    /**
     *   Set chef state.
     *
     *     @param state chef state
     */
    public void setChefState(int state);

    /**
     *   Get chef state.
     *
     *   @return chef state
     */
    public int getChefState();
}
