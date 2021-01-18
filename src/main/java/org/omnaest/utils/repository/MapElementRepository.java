package org.omnaest.utils.repository;

import java.util.Map;
import java.util.function.Supplier;

/**
 * {@link Map} like {@link ElementRepository}
 * 
 * @see ElementRepository
 * @author omnaest
 * @param <I>
 * @param <D>
 */
public interface MapElementRepository<I, D> extends MutableElementRepository<I, D>, AutoCloseable
{
    /**
     * Closes the underlying repository. This is an optional method.
     */
    @Override
    public default void close()
    {
        //do nothing
    }

    @Override
    public MapElementRepository<I, D> clear();

    /**
     * Returns an {@link ElementRepository} with the given id {@link Supplier}
     * 
     * @param idSupplier
     * @return
     */
    public default ElementRepository<I, D> toElementRepository(Supplier<I> idSupplier)
    {
        return ElementRepository.from(this, idSupplier);
    }

}
