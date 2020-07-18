/*
 * 11/07/2020 16:25:40
 * ExtendedBiFunction.java created by Tsvetelin
 */
package com.gplts.util.stdlib.extention;


import java.util.Objects;
import java.util.function.BiFunction;


/**
 * 
 * Extends the basic functionality of the BiFunction interface with useful
 * functionality
 * 
 * @author Tsvetelin
 *
 */
@FunctionalInterface
public interface ExtendedBiFunction < T , U , R >
                                    extends
                                    BiFunction< T , U , R >
{
    
    /**
     * 
     * @param <T>
     * @param <U>
     * @param <R>
     * @param f
     *            - the bifunction to lift
     * @return
     * @throws NullPointerException if the supplied argument is null
     */
    public static < T , U , R > ExtendedBiFunction< T , U , R > from (
        final BiFunction< T , U , R > f
    ) throws NullPointerException
    {
        Objects.requireNonNull( f );
        return f::apply;
    }
    
    /**
     * Swaps the arguments
     * 
     * @param <U>
     * @param <T>
     * @param <R>
     * @param f
     *            - the function to swap args of , it is null safe
     * @return
     * @throws NullPointerException if the supplied argument is null
     */
    public static < U , T , R > ExtendedBiFunction< U , T , R > swapArgs (
        final ExtendedBiFunction< T , U , R > f
    ) throws NullPointerException
    {
        Objects.requireNonNull( f );
        return ( U u , T t ) -> f.apply( t , u );
    }
    
    /**
     * Checks whether the biFunction is associative
     * 
     * @param <T>
     * @param <R>
     * @param f
     *            - the subject to check, it is null safe
     * @param arg1
     * @param arg2
     * @return true if is associative, false otherwise
     * @throws NullPointerException if the supplied function is null
     */
    public static < T , R > boolean isAssociative (
        final ExtendedBiFunction< T , T , R > f ,
        final T arg1 ,
        final T arg2
    )throws NullPointerException
    {
        Objects.requireNonNull( f );
        return f.apply( arg1 , arg2 ) == f.apply( arg2 , arg1 )
            || f.apply( arg1 , arg2 ).equals( f.apply( arg2 , arg1 ) );
    }
    
    /**
     * 
     * Transforms the function into it's curry form
     * 
     * @param <T>
     * @param <U>
     * @param <R>
     * @param f
     * @return
     * @throws NullPointerException if the supplied argument is null
     */
    public static < T , U , R > ExtendedFunction< T , ExtendedFunction< U , R > > curryForm (
        final BiFunction< T , U , R > f
    ) throws NullPointerException
    {
        Objects.requireNonNull( f );
        return ( T t ) -> ( U u ) -> f.apply( t , u );
    }
    
    /**
     * 
     * Curries the function with the given value
     * 
     * @param val-
     *            value to curry with
     * @param f-
     *            the function to curry, it is null safe
     * @return
     */
    public static < T , U , R > ExtendedFunction< U , R > curry (
        final T val ,
        final BiFunction< T , U , R > f
    )
    {
        return curryForm( f ).apply( val );
    }
    
    /**
     * 
     * Curries the function with the given supplier
     * 
     * @param supplier
     *            -
     *            value to curry with
     * @param f
     *            -
     *            the function to curry, it is null safe
     * @return
     * @throws NullPointerException if either of the arguments is null
     */
    public static < T , U , R > ExtendedFunction< U , R > curry (
        final ExtendedSupplier< T > supplier ,
        final BiFunction< T , U , R > f
    ) throws NullPointerException
    {
        Objects.requireNonNull( supplier );
        Objects.requireNonNull( f );
        return curryForm( f ).curry( supplier ).get();
    }
    
    /**
     * Swaps the arguments
     * 
     * @return
     */
    public default ExtendedBiFunction< U , T , R > swapArgs ()
    {
        return swapArgs( this );
    }
    
    /**
     * 
     * Curries the function with the given value
     * 
     * @param val-
     *            value to curry with
     * @return
     */
    public default ExtendedFunction< U , R > curry (
        final T val
    )
    {
        return curry( val , this );
    }
    
    /**
     * 
     * Curries the function with the given supplier
     * 
     * @param supplier
     *            -
     *            value to curry with
     * @return
     */
    public default ExtendedFunction< U , R > curry (
        final ExtendedSupplier< T > supplier
    )
    {
        return curry( supplier , this );
    }
    
}
