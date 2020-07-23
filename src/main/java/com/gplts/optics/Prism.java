/*
 * 23/07/2020 15:02:23
 * Prism.java created by Tsvetelin
 */
package com.gplts.optics;


import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;


/**
 * @author Tsvetelin
 *
 */
public class Prism < Type , Field > extends Lens< Type , Optional< Field > >
{
    
    /**
     * @param getter
     * @param setter
     */
    public Prism (
        Function< Type , Optional< Field > > getter ,
        BiFunction< Type , Optional< Field > , Type > setter
    )
    {
        super( getter , setter );
    }
}
