package clientSide.entities;

/**
 *   Student thread.
 *
 *   Used to simulate the student life cycle.
 *   Static solution.
 */
public interface StudentCloning
{
    /**
     *   Set student id.
     *
     *     @param id student id
     */
    public void setStudentID(int id);

    /**
     *   Get student id.
     *  
     *   @return stduent id
     */
    public int getStudentID();
    /**
     *   Set student state.
     *
     *     @param state student state
     */
    public void setStudentState(int state);

    /**
     *   Get student state.
     *
     *   @return student state
     */
    public int getStudentState();
}
