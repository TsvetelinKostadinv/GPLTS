/*
 * 08/04/2019 22:15:32
 * UnimplementedException.java created by Tsvetelin
 */
package com.gplts.util.todo;


/**
 * @author Tsvetelin
 *
 */
public class UnimplementedException extends RuntimeException
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public UnimplementedException ()
    {
        super();
    }

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public UnimplementedException (
            String message ,
            Throwable cause ,
            boolean enableSuppression ,
            boolean writableStackTrace
    )
    {
        super( message , cause , enableSuppression , writableStackTrace );
    }

    /**
     * @param message
     * @param cause
     */
    public UnimplementedException ( String message , Throwable cause )
    {
        super( message , cause );
    }

    /**
     * @param message
     */
    public UnimplementedException ( String message )
    {
        super( message );
    }

    /**
     * @param cause
     */
    public UnimplementedException ( Throwable cause )
    {
        super( cause );
    }
}
