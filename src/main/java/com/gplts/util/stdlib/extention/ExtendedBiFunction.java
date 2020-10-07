/*
 * 11/07/2020 16:25:40
 * ExtendedBiFunction.java created by Tsvetelin
 */
package com.gplts.util.stdlib.extention;


import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;


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
     * @throws NullPointerException
     *             if the supplied argument is null
     */
    public static < T , U , R > ExtendedBiFunction< T , U , R > from (
        final BiFunction< T , U , R > f
    )
        throws NullPointerException
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
     * @throws NullPointerException
     *             if the supplied argument is null
     */
    public static < U , T , R > ExtendedBiFunction< U , T , R > swapArgs (
        final ExtendedBiFunction< T , U , R > f
    )
        throws NullPointerException
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
     * @throws NullPointerException
     *             if the supplied function is null
     */
    public static < T , R > boolean isAssociative (
        final ExtendedBiFunction< T , T , R > f ,
        final T arg1 ,
        final T arg2
    )
        throws NullPointerException
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
     * @throws NullPointerException
     *             if the supplied argument is null
     */
    public static < T , U , R > ExtendedFunction< T , ExtendedFunction< U , R > > curryForm (
        final BiFunction< T , U , R > f
    )
        throws NullPointerException
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
     * @throws NullPointerException
     *             if either of the arguments is null
     */
    public static < T , U , R > ExtendedFunction< U , R > curry (
        final ExtendedSupplier< T > supplier ,
        final BiFunction< T , U , R > f
    )
        throws NullPointerException
    {
        Objects.requireNonNull( supplier );
        Objects.requireNonNull( f );
        return curryForm( f ).curry( supplier ).get();
    }
    
    /**
     * Maps the output through the given function
     * 
     * @param <T>
     * @param <U>
     * @param <R>
     * @param <newR>
     * @param biF
     *            - the bifunction to map
     * @param f
     *            - the function to pass through
     * @return a new bifunction that has the mapped output
     * @throws NullPointerException
     *             - if any of the arguments are null
     */
    public static < T , U , R , newR > ExtendedBiFunction< T , U , newR > map (
        final ExtendedBiFunction< T , U , R > biF ,
        Function< R , newR > f
    )
        throws NullPointerException
    {
        Objects.requireNonNull( f );
        return ( t , u ) -> f
            .apply(
                biF.apply( t , u )
            );
    }
    
    /**
     * Maps the output through the given function
     * 
     * @param <T>
     * @param <U>
     * @param <R>
     * @param <newR>
     * @param biF
     *            - the bifunction to map
     * @param f
     *            - the function to pass through
     * @return a new bifunction that has the mapped output
     * @throws NullPointerException
     *             - if any of the arguments are null
     */
    public static < T , U , R , newR > ExtendedBiFunction< T , U , newR > map (
        final ExtendedBiFunction< T , U , R > biF ,
        ExtendedFunction< R , newR > f
    )
        throws NullPointerException
    {
        Objects.requireNonNull( f );
        return ( t , u ) -> f
            .apply(
                biF.apply( t , u )
            );
    }
    
    /**
     * Lefts the output of the bifunction to an optional
     * 
     * @param <T>
     * @param <U>
     * @param <R>
     * @param f
     *            - the function to lift
     * @return the extended bifunction with the output lifted to an optional
     * @throws NullPointerException
     *             - if the function is null
     */
    public static < T , U , R > ExtendedBiFunction< T , U , Optional< R > > liftToOptional (
        final ExtendedBiFunction< T , U , R > f
    )
        throws NullPointerException
    {
        return f.map( Optional::ofNullable );
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
     *            - value to curry with
     * @return
     */
    public default ExtendedFunction< U , R > curry (
        final ExtendedSupplier< T > supplier
    )
        throws NullPointerException
    {
        return curry( supplier , this );
    }
    
    /**
     * Mapps the output of the bifunction with the given function
     * 
     * @param <newR>
     * @param f
     *            - the function to map
     * @return an extended bifunction with the output mapped
     * @throws NullPointerException
     *             - if the function is null
     */
    public default < newR > ExtendedBiFunction< T , U , newR > map (
        Function< R , newR > f
    )
        throws NullPointerException
    {
        return map( this , f );
    }
    
    /**
     * Mapps the output of the bifunction with the given function
     * 
     * @param <newR>
     * @param f
     *            - the function to map
     * @return an extended bifunction with the output mapped
     * @throws NullPointerException
     *             - if the function is null
     */
    public default < newR > ExtendedBiFunction< T , U , newR > map (
        ExtendedFunction< R , newR > f
    )
        throws NullPointerException
    {
        return map( this , f );
    }
    
    /**
     * Lefts the output to an optional
     * 
     * @return the extended bifunction with the output mapped to an optional
     */
    public default ExtendedBiFunction< T , U , Optional< R > > liftToOptional ()
    {
        return liftToOptional( this );
    }
}
