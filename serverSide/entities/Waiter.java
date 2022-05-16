package serverSide.entities;
/**
 *   Waiter thread.
 *
 *   Used to simulate the Waiter life cycle.
 *   Static solution.
 */
public interface Waiter {
    /**
     *   Set waiter state.
     *
     *     @param state waiter state
     */
    public void setWaiterState(int state);

    /**
     *   Get waiter state.
     *
     *     @return waiter state
     */
    public int getWaiterState();

}
