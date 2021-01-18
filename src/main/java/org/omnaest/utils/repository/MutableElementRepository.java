package org.omnaest.utils.repository;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

/**
 * Allows to modify the element repository similar to a {@link Collection} but does not allow add operations similar to the {@link AppendableElementRepository}.
 * 
 * @author omnaest
 * @param <I>
 * @param <D>
 */
public interface MutableElementRepository<I, D> extends ImmutableElementRepository<I, D>
{
    /**
     * Updates a data element where the id is already known
     * 
     * @param id
     * @param element
     * @return
     */
    public void put(I id, D element);

    /**
     * Puts all elements from the given {@link Map} into the {@link ElementRepository}
     * 
     * @param map
     */
    public default void putAll(Map<I, D> map)
    {
        if (map != null)
        {
            map.forEach(this::put);
        }
    }

    /**
     * Returns the element for the given id, but if the element is not available it calls the given {@link Supplier}, returns that retrieved element and adds it
     * to the {@link ElementRepository}
     * 
     * @param id
     * @param supplier
     * @return
     */
    public default D computeIfAbsent(I id, Supplier<D> supplier)
    {
        return this.computeIfAbsent(id, i -> supplier.get());
    }

    /**
     * Similar to {@link #computeIfAbsent(Object, Supplier)}
     * 
     * @param id
     * @param factory
     * @return
     */
    public default D computeIfAbsent(I id, Function<I, D> factory)
    {
        D element = this.getValue(id);
        if (element == null)
        {
            element = factory.apply(id);
            this.put(id, element);
        }
        return element;
    }

    /**
     * Gets an element by the given id and allows to update it by a {@link UnaryOperator}. After the {@link UnaryOperator} finishes the element will be written
     * back to the {@link ElementRepository}.
     * 
     * @param id
     * @param updateFunction
     * @return
     */
    public default MutableElementRepository<I, D> update(I id, UnaryOperator<D> updateFunction)
    {
        D element = this.getValue(id);
        element = updateFunction.apply(element);
        this.put(id, element);
        return this;
    }

    /**
     * Gets or creates an element by the given id and supplier and allows to update it by a {@link UnaryOperator}. After the {@link UnaryOperator} finished the
     * element will be written to the store.
     * 
     * @param id
     * @param supplier
     * @param updateFunction
     * @return
     */
    public default MutableElementRepository<I, D> computeIfAbsentAndUpdate(I id, Supplier<D> supplier, UnaryOperator<D> updateFunction)
    {
        D element = this.getValue(id);
        if (element == null)
        {
            element = supplier.get();
        }
        element = updateFunction.apply(element);
        this.put(id, element);
        return this;
    }

    /**
     * Deletes a data element by its id
     * 
     * @param id
     * @return
     */
    public void remove(I id);

    /**
     * Clears the {@link ElementRepository}
     * 
     * @return this
     */
    public MutableElementRepository<I, D> clear();
}
