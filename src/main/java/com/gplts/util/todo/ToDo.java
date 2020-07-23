/*
 * 15/03/2020 16:52:57
 * ToDo.java created by Tsvetelin
 */
package com.gplts.util.todo;


/**
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
