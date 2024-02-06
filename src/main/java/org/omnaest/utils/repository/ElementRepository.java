/*******************************************************************************
 * Copyright 2021 Danny Kunz
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
/*

	Copyright 2017 Danny Kunz

	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at

		http://www.apache.org/licenses/LICENSE-2.0

	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.


*/
package org.omnaest.utils.repository;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

import org.omnaest.utils.ReflectionUtils;
import org.omnaest.utils.SupplierUtils;
import org.omnaest.utils.functional.BidirectionalFunction;
import org.omnaest.utils.repository.internal.AppendableSupportedCoreElementRepositoryDecorator;
import org.omnaest.utils.repository.internal.MapAndSupplierElementRepository;
import org.omnaest.utils.repository.internal.MappedElementRepository;
import org.omnaest.utils.repository.internal.SynchronizedElementRepository;
import org.omnaest.utils.repository.internal.WeakHashMapDecoratingElementRepository;
import org.omnaest.utils.repository.join.ElementRepositoryJoiner;

/**
 * {@link ElementRepository} does define accessors for a data element with a reference identifier
 * 
 * @see #of(Map, Supplier)
 * @see #of(File, Class)
 * @see #asWeakCached()
 * @see #asSynchronized()
 * @see IndexElementRepository
 * @see ImmutableElementRepository
 * @see ElementAggregationRepository
 * @see ElementRepositoryUtils
 * @author omnaest
 * @param <I>
 *            reference identifier
 * @param <D>
 *            data element
 */
public interface ElementRepository<I, D> extends MapElementRepository<I, D>, AppendableElementRepository<I, D>
{
    @Override
    public ElementRepository<I, D> clear();

    @Override
    public default ElementRepository<I, D> update(I id, UnaryOperator<D> updateFunction)
    {
        MapElementRepository.super.update(id, updateFunction);
        return this;
    }

    @Override
    public default ElementRepository<I, D> computeIfAbsentAndUpdate(I id, Supplier<D> supplier, UnaryOperator<D> updateFunction)
    {
        MapElementRepository.super.computeIfAbsentAndUpdate(id, supplier, updateFunction);
        return this;
    }

    /**
     * Returns a new {@link ElementRepository} wrapping the current one into a {@link WeakReference} cached structure
     * 
     * @return
     */
    public default ElementRepository<I, D> asWeakCached()
    {
        return new WeakHashMapDecoratingElementRepository<>(this);
    }

    /**
     * Returns a synchronized {@link ElementRepository} wrapping the current one
     * 
     * @return
     */
    public default ElementRepository<I, D> asSynchronized()
    {
        return new SynchronizedElementRepository<>(this);
    }

    /**
     * Returns a new {@link ElementRepository} based on the given {@link Map} and {@link Supplier} of reference ids
     * 
     * @param map
     * @param idSupplier
     * @return
     */
    public static <I, D> ElementRepository<I, D> of(Map<I, D> map, Supplier<I> idSupplier)
    {
        return new MapAndSupplierElementRepository<>(map, idSupplier);
    }

    /**
     * Returns a {@link MapElementRepository} based on a {@link Map}, which does not allow to append elements without specifying an id.
     * 
     * @param map
     * @return
     */
    public static <I, D> MapElementRepository<I, D> ofNonSupplied(Map<I, D> map)
    {
        return of(map, SupplierUtils.toExceptionThrowingSupplier(() -> new UnsupportedOperationException())).asMapElementRepository();
    }

    /**
     * Returns the current instance as {@link MapElementRepository}
     * 
     * @return
     */
    public default MapElementRepository<I, D> asMapElementRepository()
    {
        return this;
    }

    /**
     * Wraps a given {@link MapElementRepository} with support for the {@link AppendableElementRepository} methods
     * 
     * @param coreElementRepository
     * @param idSupplier
     * @return
     */
    public static <I, D> ElementRepository<I, D> from(MapElementRepository<I, D> coreElementRepository, Supplier<I> idSupplier)
    {
        return new AppendableSupportedCoreElementRepositoryDecorator<>(coreElementRepository, idSupplier);
    }

    /**
     * Returns an {@link IndexElementRepository} based on the given {@link Map} using a {@link Long} id {@link Supplier}
     * 
     * @param map
     * @return
     */
    public static <D> IndexElementRepository<D> of(Map<Long, D> map)
    {
        return IndexElementRepository.of(map);
    }

    /**
     * Returns a new {@link ElementRepository} based on the folder structure of the given {@link File} directory
     * 
     * @param directory
     * @param type
     * @return
     */
    public static <D> IndexElementRepository<D> of(File directory, Class<D> type)
    {
        return IndexElementRepository.of(directory, type);

    }

    /**
     * Returns an {@link ElementRepositoryJoiner} of this and an additional {@link ElementRepository} with the same keys.
     * 
     * @param elementRepository
     * @return
     */
    public default <DR> ElementRepositoryJoiner<I, D, DR> join(ElementRepository<I, DR> elementRepository)
    {
        return ElementRepositoryJoiner.of(this, elementRepository);
    }

    /**
     * Returns this as {@link ImmutableElementRepository}
     * 
     * @return
     */
    public default ImmutableElementRepository<I, D> asImmutable()
    {
        return this;
    }

    public static interface Factory
    {
        public <I, D> ElementRepository<I, D> newInstance(String name, Class<I> idType, Class<D> dataType);

        public static Factory of(Map<?, ?> map)
        {
            return new ElementRepository.Factory()
            {
                @Override
                public <I, D> ElementRepository<I, D> newInstance(String name, Class<I> idType, Class<D> dataType)
                {
                    AtomicLong counter = new AtomicLong();
                    return ElementRepository.of(new HashMap<>(), () -> ReflectionUtils.newInstance(idType, counter.getAndIncrement()));
                }
            };
        }
    }

    public default <ND> ElementRepository<I, ND> asDataMapped(BidirectionalFunction<D, ND> mapper)
    {
        return asMapped(BidirectionalFunction.identity(), mapper);
    }

    public default <NI> ElementRepository<NI, D> asKeyMapped(BidirectionalFunction<I, NI> mapper)
    {
        return asMapped(mapper, BidirectionalFunction.identity());
    }

    public default <NI, ND> ElementRepository<NI, ND> asMapped(BidirectionalFunction<I, NI> keyMapper, BidirectionalFunction<D, ND> dataMapper)
    {
        return new MappedElementRepository<>(this, keyMapper, dataMapper);
    }

    public default IndexElementRepository<D> asIndexElementRepository(BidirectionalFunction<I, Long> mapper)
    {
        return IndexElementRepository.of(this.asKeyMapped(mapper));
    }

    public default SetElementRepository<I> asSetElementRepository()
    {
        return SetElementRepository.of(this);
    }

    public default SetElementRepository<I> asSetElementRepository(D nullValue)
    {
        return SetElementRepository.of(this, nullValue);
    }

}
