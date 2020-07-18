/*
 * 27/03/2020 15:46:13
 * MappableSupplier.java created by Tsvetelin
 */
package com.gplts.util.stdlib.extention;


import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;


/**
 * 
 * Extends the basic functionality of the Supplier interface with useful
 * functionality
 * 
 * @author Tsvetelin
 *
 */
@FunctionalInterface
public interface ExtendedSupplier < T > extends Supplier< T >
{
    
    /**
     * 
     * Lifts the supplier to the extended one
     * 
     * @param <T>
     * @param val
     *            - the native supplier, it is null safe
     * @return an extended supplier
     * @throws NullPointerException
     *             if the supplied argument is null
     */
    public static < T > ExtendedSupplier< T > from ( final Supplier< T > val )
        throws NullPointerException
    {
        Objects.requireNonNull( val );
        return val::get;
    }
    
    /**
     * 
     * Constructs a constant supplier with the supplied value
     * 
     * @param <T>
     * @param value
     *            - the constant value
     * @return a supplier of constant value
     */
    public static < T > ExtendedSupplier< T > from ( final T value )
    {
        return () -> value;
    }
    
    /**
     * Checks if the supplier returns the same instance( using .equals or ==)
     * 
     * @param <T>
     * @param supplier
     *            - the subject to check, it is null safe
     * @return true if constant, false otherwise
     * @implNote this calls the supplier twice
     * @throws NullPointerException
     *             if the supplied argument is null
     */
    public static < T > boolean isConstant ( final Supplier< T > supplier )
        throws NullPointerException
    {
        Objects.requireNonNull( supplier );
        final T instance1 = supplier.get();
        final T instance2 = supplier.get();
        return instance1 == instance2
            || instance1.equals( instance2 );
    }
    
    /**
     * Checks if the supplier returns the same instance( referetially )
     * 
     * @param <T>
     * @param supplier
     *            - the subject to check, it is null safe
     * @return true if constant, false otherwise
     * @implNote this calls the supplier twice
     *            
     * @throws NullPointerException
     *             if the supplied argument is null
     */
    public static < T > boolean isRefConstant ( final Supplier< T > supplier )
        throws NullPointerException
    {
        Objects.requireNonNull( supplier );
        return supplier.get() == supplier.get();
    }
    
    /**
     * 
     * Adds the transformation after the generation of the value
     * 
     * @param <R>
     * @param f
     *            - the transformation, it is null safe
     * @return the mapped supplier
     *            
     * @throws NullPointerException
     *             if the supplied argument is null
     */
    public default < R > ExtendedSupplier< R > map ( final Function< T , R > f )
        throws NullPointerException
    {
        Objects.requireNonNull( f );
        return () -> f.apply( get() );
    }
    
    /**
     * 
     * Adds the transformation after the generation of the value
     * 
     * @param <R>
     * @param f
     *            - the transformation, it is null safe
     * @return the mapped supplier
     *            
     * @throws NullPointerException
     *             if the supplied argument is null
     */
    public default < R > ExtendedSupplier< R > flatMap (
        final Function< T , Supplier< R > > f
    )
        throws NullPointerException
    {
        Objects.requireNonNull( f );
        return from( f.apply( get() ) );
    }
    
    /**
     * 
     * Lift the value to an optional of the value
     * 
     * @param <R>
     * @return the mapped supplier
     */
    public default ExtendedSupplier< Optional< T > > liftToOptional ()
    {
        return this.map( Optional::ofNullable );
    }
    
    /**
     * 
     * Checks if this supplier is a constant one
     * 
     * @return true if constant, false otherwise
     */
    public default boolean isConstant ()
    {
        return isConstant( this );
    }
    
}
