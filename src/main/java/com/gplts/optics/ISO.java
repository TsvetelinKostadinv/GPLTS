/*
 * 06/08/2020 18:21:19
 * ISO.java created by Tsvetelin
 */
package com.gplts.optics;


import java.util.Objects;
import java.util.function.Function;


/**
 * An ISO function which transforms between 2 given types
 * 
 * @author Tsvetelin
 */
public class ISO < A , B >
{
    
    private final Function< A , B > transform;
    private final Function< B , A > reverse;
    
    public ISO ( Function< A , B > transform , Function< B , A > reverse )
    {
        super();
        Objects.requireNonNull( transform );
        Objects.requireNonNull( reverse );
        this.transform = transform;
        this.reverse = reverse;
    }
    
    /**
     * Transforms the given source to the target of the ISO
     * 
     * @param source
     * @return the transformed value
     */
    public B get ( final A source )
    {
        return this.transform.apply( source );
    }
    
    /**
     * Transforms the given source to the other side of the ISO
     * 
     * @param source
     * @return the transformed value
     */
    public A reverseGet ( final B source )
    {
        return this.reverse.apply( source );
    }
    
    /**
     * @return the whole ISO but reversed
     */
    public ISO< B , A > reverse ()
    {
        return new ISO<>( reverse , transform );
    }
}
