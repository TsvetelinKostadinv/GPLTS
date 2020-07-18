/*
 * 29/03/2020 18:12:46
 * ExtendedFunction.java created by Tsvetelin
 */
package com.gplts.util.stdlib.extention;


import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;


/**
 * 
 * Extends the basic functionality of the Function interface with useful
 * functionality
 * 
 * @author Tsvetelin
 *
 */
@FunctionalInterface
public interface ExtendedFunction < T , R > extends Function< T , R >
{
    
    /**
     * 
     * Lifts the java function to extended one
     * 
     * @param <T>
     * @param <R>
     * @param f
     *            - the function to lift, it is null safe
     * @return
     * @throws NullPointerException
     *             if the supplied argument is null
     */
    public static < T , R > ExtendedFunction< T , R > from (
        final Function< T , R > f
    )
        throws NullPointerException
    {
        Objects.requireNonNull( f );
        return f::apply;
    }
    
    /**
     * 
     * Composes the output of the current function to the input of the after
     * function
     * 
     * @param <U>
     * @param after
     *            - the next function, it is null safe
     * @return
     *            
     * @throws NullPointerException
     *             if the supplied argument is null
     */
    public default < U > ExtendedFunction< T , U > map (
        final Function< R , U > after
    )
        throws NullPointerException
    {
        Objects.requireNonNull( after );
        return t -> after.apply( this.apply( t ) );
    }
    
    /**
     * 
     * Lifts the function to an optional one
     * 
     * @return
     */
    public default ExtendedFunction< T , Optional< R > > liftToOptional ()
    {
        return this.map( Optional::ofNullable );
    }
    
    /**
     * 
     * Curries the function with the given value
     * 
     * @param val
     * @return
     */
    public default ExtendedSupplier< R > curry ( final T val )
    {
        return () -> this.apply( val );
    }
    
    /**
     * 
     * Curries the function with the given supplier
     * 
     * @param supplier
     *            - the supplier to curry with, it is null safe
     * @return
     * @throws NullPointerException
     *             if the supplied argument is null
     */
    public default ExtendedSupplier< R > curry (
        final ExtendedSupplier< T > supplier
    )
        throws NullPointerException
    {
        Objects.requireNonNull( supplier );
        return supplier.map( this );
    }
}
