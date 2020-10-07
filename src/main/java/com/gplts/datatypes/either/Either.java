/*
 * 18/07/2020 19:06:08
 * Either.java created by Tsvetelin
 */
package com.gplts.datatypes.either;


import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;

import com.gplts.datatypes.lazy.Lazy;
import com.gplts.util.stdlib.extention.ExtendedSupplier;


/**
 * 
 * Type-safe abstraction over dual types. Or in the language of the pure
 * functional folks disjunction union type
 * 
 * @author Tsvetelin
 *
 */
public class Either < L , R >
{
    
    private final Optional< Lazy< L > > left;
    private final Optional< Lazy< R > > right;
    
    protected Either ( L left , R right )
    {
        this.left =
            left != null ? Optional.of( Lazy.of( left ) ) : Optional.empty();
        this.right =
            right != null ? Optional.of( Lazy.of( right ) ) : Optional.empty();
    }
    
    protected Either ( Supplier< L > left , Supplier< R > right )
    {
        this.left =
            left != null ? Optional.of( Lazy.of( left ) ) : Optional.empty();
        this.right =
            right != null ? Optional.of( Lazy.of( right ) ) : Optional.empty();
    }
    
    protected Either (
        ExtendedSupplier< L > left ,
        ExtendedSupplier< R > right
    )
    {
        this.left =
            left != null ? Optional.of( Lazy.of( left ) ) : Optional.empty();
        this.right =
            right != null ? Optional.of( Lazy.of( right ) ) : Optional.empty();
    }
    
    protected Either ( Lazy< L > left , Lazy< R > right )
    {
        this.left =
            left != null ? Optional.of( left ) : Optional.empty();
        this.right =
            right != null ? Optional.of( right ) : Optional.empty();
    }
    
    /**
     * Factory method
     * 
     * @param <L>
     * @param <R>
     * @param left
     * @return
     */
    public static < L , R > Either< L , R > left ( final L left )
    {
        return new Either<>( left , null );
    }
    
    /**
     * Factory method
     * 
     * @param <L>
     * @param <R>
     * @param left
     * @return
     */
    public static < L , R > Either< L , R > left ( final Supplier< L > left )
    {
        return new Either<>( left , null );
    }
    
    /**
     * Factory method
     * 
     * @param <L>
     * @param <R>
     * @param left
     * @return
     */
    public static < L , R > Either< L , R > left (
        final ExtendedSupplier< L > left
    )
    {
        return new Either<>( left , null );
    }
    
    /**
     * Factory method
     * 
     * @param <L>
     * @param <R>
     * @param left
     * @return
     */
    public static < L , R > Either< L , R > left (
        final Lazy< L > left
    )
    {
        return new Either<>( left , null );
    }
    
    /**
     * Factory method
     * 
     * @param <L>
     * @param <R>
     * @param right
     * @return
     */
    public static < L , R > Either< L , R > right ( final R right )
    {
        return new Either<>( null , right );
    }
    
    /**
     * Factory method
     * 
     * @param <L>
     * @param <R>
     * @param right
     * @return
     */
    public static < L , R > Either< L , R > right ( final Supplier< R > right )
    {
        return new Either<>( null , right );
    }
    
    /**
     * Factory method
     * 
     * @param <L>
     * @param <R>
     * @param right
     * @return
     */
    public static < L , R > Either< L , R > right (
        final ExtendedSupplier< R > right
    )
    {
        return new Either<>( null , right );
    }
    
    /**
     * Factory method
     * 
     * @param <L>
     * @param <R>
     * @param right
     * @return
     */
    public static < L , R > Either< L , R > right (
        final Lazy< R > right
    )
    {
        return new Either<>( null , right );
    }
    
    /**
     * 
     * Maps the left value if it is present, otherwise leaves it unchanged
     * 
     * @param <_L>
     * @param f
     * @return
     * @throws NullPointerException
     *             - if the supplied function is null
     */
    public < _L > Either< _L , R > mapLeft ( final Function< L , _L > f )
        throws NullPointerException
    {
        return this.left
            .map( lazy -> lazy.map( f ) )
            .map( newLeft -> Either.< _L , R >left( newLeft ) )
            .orElseGet( () -> Either.right( this.right.get() ) );
    }
    
    /**
     * Maps the right value if it is present, otherwise leaves it unchanged
     * 
     * @param <_R>
     * @param f
     * @return
     * @throws NullPointerException
     *             - if the supplied function is null
     */
    public < _R > Either< L , _R > mapRight ( final Function< R , _R > f )
        throws NullPointerException
    {
        return this.right
            .map( lazy -> lazy.map( f ) )
            .map( newRight -> Either.< L , _R >right( newRight ) )
            .orElseGet( () -> Either.left( this.left.get() ) );
    }
    
    /**
     * 
     * Maps both the values. The present value is mapped, the other one is left
     * unpresent and unmapped
     * 
     * @param <_L>
     * @param <_R>
     * @param lf
     *            - the mapper for the left value
     * @param rf
     *            - the mapper for the right value
     * @return
     * @throws NullPointerException
     *             - if any of the functions are not present
     */
    public < _L , _R > Either< _L , _R > map (
        final Function< L , _L > lf ,
        final Function< R , _R > rf
    )
        throws NullPointerException
    {
        return this.mapLeft( lf ).mapRight( rf );
    }
    
    /**
     * 
     * @return true if the left value is present, false otherwise
     */
    public boolean isLeft ()
    {
        return this.left.isPresent();
    }
    
    /**
     * 
     * @return true if the right value is present, false otherwise
     */
    public boolean isRight ()
    {
        return this.right.isPresent();
    }

    /**
     * Runs the action if this is a left value
     * 
     * @param action
     * @return the same either( ***referentially*** )
     */
    public Either< L , R > ifLeft ( final Runnable action )
    {
        if ( this.isLeft() ) action.run();
        return this;
    }
    
    /**
     * Runs the action if this is a right value
     * 
     * @param action
     * @return the same either( ***referentially*** )
     */
    public Either< L , R > ifRight ( final Runnable action )
    {
        if ( this.isRight() ) action.run();
        return this;
    }
    
    /**
     * Gets the value if this is a left value
     * 
     * @param supplier
     * @return a optional of the result if the either is left, empty one otherwise
     */
    public <T> Optional<T> ifLeft ( Supplier<T> supplier )
    {
        if ( this.isLeft() ) return Optional.ofNullable( supplier.get() );
        return Optional.empty();
    }
    
    /**
     * Gets the value if this is a left value
     * 
     * @param supplier
     * @return a optional of the result if the either is left, empty one otherwise
     */
    public <T> Optional<T> ifRight ( Supplier<T> supplier  )
    {
        if ( this.isRight() ) return Optional.ofNullable( supplier.get() );
        return Optional.empty();
    }
    
    /**
     * 
     * @return full optional of the lazy value if present, empty one otherwise
     */
    public Optional< Lazy< L > > getLeft_l ()
    {
        return this.left;
    }
    
    /**
     * 
     * @return full optional of the lazy value if present, empty one otherwise
     */
    public Optional< Lazy< R > > getRight_l () throws NoSuchElementException
    {
        return this.right;
    }
    
    /**
     * 
     * @return full optional of the value if present, empty one otherwise
     */
    public Optional< L > getLeft ()
    {
        return this.left.map( lazy -> lazy.get() );
    }
    
    /**
     * 
     * @return full optional of the value if present, empty one otherwise
     */
    public Optional< R > getRight ()
    {
        return this.right.map( lazy -> lazy.get() );
    }
    
    /**
     * 
     * @param <T>
     * @param ifLeft
     * @param ifRight
     * @return
     * @throws NullPointerException
     *             - if any of the functions is null
     */
    public < T > T match (
        final Function< Lazy< L > , T > ifLeft ,
        final Function< Lazy< R > , T > ifRight
    )
        throws NullPointerException
    {
        Objects.requireNonNull( ifLeft );
        Objects.requireNonNull( ifRight );
        
        return this.left
            .map( ifLeft )
            .orElseGet( () -> this.right.map( ifRight ).get() );
    }
    
    /**
     * Folds the either to a single value.
     * NOTE: The functions may or may not produce a value.
     * 
     * @param <T>
     * @param lf
     * @param rf
     * @param combiner
     * @return
     * @throws NullPointerException
     *             - if any of the functions is null
     */
    public < T > T fold (
        final Function< L , T > lf ,
        final Function< R , T > rf ,
        final BinaryOperator< T > combiner
    )
        throws NullPointerException
    {
        Objects.requireNonNull( lf );
        Objects.requireNonNull( rf );
        Objects.requireNonNull( combiner );
        
        final T leftVal =
            this.left.map( lazy -> lazy.get() ).map( lf ).orElse( null );
        final T rightVal =
            this.right.map( lazy -> lazy.get() ).map( rf ).orElse( null );
        
        return combiner.apply( leftVal , rightVal );
    }
    
}
