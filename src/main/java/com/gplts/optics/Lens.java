/*
 * 23/03/2020 17:05:53
 * Lens.java created by Tsvetelin
 */
package com.gplts.optics;


import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;


/**
 * 
 * A general purpouse lens used to look into immutable data structures
 * 
 * @author Tsvetelin
 *
 */
public class Lens < Type , Field >
{
    
    private final Function< Type , Field >          getter;
    
    private final BiFunction< Type , Field , Type > setter;
    
    /**
     * 
     * Constructs a new Lens for the type and the field
     * 
     * @param getter
     *            -how to get the field - cannot be null
     * @param setter
     *            - how to set the field - cannot be null
     */
    public Lens (
        final Function< Type , Field > getter ,
        final BiFunction< Type , Field , Type > setter
    )
    {
        super();
        Objects.requireNonNull( getter );
        Objects.requireNonNull( setter );
        this.getter = getter;
        this.setter = setter;
    }
    
    /**
     * 
     * Constructs a new Lens for the type and the field
     * 
     * @param getter
     *            -how to get the field - cannot be null
     * @param setter
     *            - how to set the field - cannot be null
     */
    public static < Type , Field > Lens< Type , Field > of (
        final Function< Type , Field > getter ,
        final BiFunction< Type , Field , Type > setter
    )
    {
        return new Lens<>( getter , setter );
    }
    
    /**
     * 
     * Gets the value out of the type
     * 
     * @param target
     * @return
     */
    public Field get ( final Type target )
    {
        return this.getter.apply( target );
    }
    
    /**
     * 
     * Sets the value of the field in the type
     * 
     * @param target
     * @param newValue
     * @return a type with the field changed
     */
    public Type set ( final Type target , final Field newValue )
    {
        return modify( __ -> newValue ).apply( target );
    }
    
    /**
     * 
     * Given a transformation on the field, maps it internally and constructs a
     * function that changes the type
     * 
     * @param mapper
     * @return a function to transform a type into the same type, but with a
     *         changed field
     */
    public Function< Type , Type > modify (
        final Function< Field , Field > mapper
    )
    {
        Objects.requireNonNull( mapper );
        return ( oldValue ) -> {
            final Field extracted = getter.apply( oldValue );
            final Field transformed = mapper.apply( extracted );
            return setter.apply( oldValue , transformed );
        };
    }
    
    /**
     * 
     * Given the old type, returns a function which given a mapper produces a
     * type
     * 
     * @param oldValue
     * @return
     */
    public Function< Function< Field , Field > , Type > modify (
        final Type oldValue
    )
    {
        return ( mapper ) -> {
            final Field extracted = getter.apply( oldValue );
            final Field transformed = mapper.apply( extracted );
            return setter.apply( oldValue , transformed );
        };
    }
    
    /**
     * Composes 2 lens so that they can look deeper into the data structure
     * 
     * @param <InnerField>
     * @param other
     * @return
     */
    public < InnerField > Lens< Type , InnerField > compose (
        final Lens< Field , InnerField > other
    )
    {
        Objects.requireNonNull( other );
        return new Lens< Type , InnerField >(
            ( Type type ) -> other.getter.apply( this.getter.apply( type ) ) ,
            ( Type type , InnerField newInner ) -> {
                final Field intermediate = this.get( type );
                final Field result =
                    other.modify( __ -> newInner ).apply( intermediate );
                final Type resType = this.set( type , result );
                return resType;
            }
        );
    }
}
