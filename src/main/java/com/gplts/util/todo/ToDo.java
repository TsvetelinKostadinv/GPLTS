/*
 * 15/03/2020 16:52:57
 * ToDo.java created by Tsvetelin
 */
package com.gplts.util.todo;


/**
 * 
 * Utility interface for postponing implementation details
 * Best used with <br>
 * <code>import static com.gplts.util.todo.ToDO.*</code>
 * 
 * @author Tsvetelin
 *
 */
public interface ToDo
{
    
    public static void TODO ( final String message )
    {
        throw new UnimplementedException( message );
    }
    
    public static void __ ()
    {
        throw new UnimplementedException();
    }
    
}
