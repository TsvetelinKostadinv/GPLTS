/*
 * 07/10/2020 18:35:25
 * OptionalUtils.java created by Tsvetelin
 */
package com.gplts.util.stdlib.extention.optional;


import java.util.Objects;
import java.util.Optional;

import com.gplts.util.stdlib.extention.ExtendedBiFunction;


/**
 * Utility class for working with optionals
 * 
 * @author Tsvetelin
 */
public class OptionalUtils
{
    
    /**
     * If both the optionals are present combines them through the bifunction
     * 
     * @param <T>
     * @param <U>
     * @param <R>
     * @param left
     *            - the optional which will stand on the left of the combiner
     * @param right
     *            - the optional which will stand on the right of the combiner
     * @param combiner
     *            - the bifunction which will combine the values
     * @return an optional of the result
     * @throws NullPointerException
     *             - if any of the arguments are null
     */
    public static < T , U , R > Optional< R > ifBothPresentCombine (
        Optional< T > left ,
        Optional< U > right ,
        ExtendedBiFunction< T , U , R > combiner
    )
        throws NullPointerException
    {
        Objects.requireNonNull( left );
        Objects.requireNonNull( right );
        Objects.requireNonNull( combiner );
        if ( left.isPresent() && right.isPresent() )
        {
            return combiner.liftToOptional().apply( left.get() , right.get() );
        } else
        {
            return Optional.empty();
        }
    }
    
}
