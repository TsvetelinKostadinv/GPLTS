/*
 * 18/07/2020 18:26:36
 * Lazy.java created by Tsvetelin
 */
package com.gplts.datatypes.lazy;


import java.io.Serializable;
import java.util.function.Function;
import java.util.function.Supplier;

import com.gplts.util.stdlib.extention.ExtendedSupplier;


/**
 * 
 * A dataclass as an abstraction over lazy values
 * 
 * @author Tsvetelin
 *
 */
public class Lazy < T > implements Serializable
{
    
    private static final long             serialVersionUID = 1L;
    
    protected final ExtendedSupplier< T > lazyVal;
    
    protected Lazy ( final T val )
    {
        this.lazyVal = () -> val;
    }
    
    protected Lazy ( final Supplier< T > val )
    {
        this.lazyVal = ExtendedSupplier.from( val );
    }
    
    protected Lazy ( final ExtendedSupplier< T > val )
    {
        this.lazyVal = val;
    }
    
    public T get ()
    {
        return this.lazyVal.get();
    }
    
    public < R > Lazy< R > map ( Function< T , R > f )
    {
        return new Lazy<>( lazyVal.map( f ) );
    }
    
    /*
     * public < R > Lazy< R > flatMap ( Function< T , Lazy< R > > f )
     * {
     * TODO: At this point in time this method cannot be implemented, maybe
     * after the addition of HigherKinds
     * }
     */
    
    public static < T > T get ( final Lazy< T > val )
    {
        return val.get();
    }
    
    public static < T , R > Lazy< R > map (
        final Lazy< T > val ,
        Function< T , R > f
    )
    {
        return val.map( f );
    }
    
}
