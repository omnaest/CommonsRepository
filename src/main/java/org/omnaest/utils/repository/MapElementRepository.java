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
package org.omnaest.utils.repository;

import java.util.Map;
import java.util.function.Supplier;

import org.omnaest.utils.repository.adapter.MapElementRepositoryToMapAdapter;

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

    /**
     * Returns a {@link Map} view of this {@link MapElementRepository}
     * 
     * @return
     */
    public default Map<I, D> asMap()
    {
        return new MapElementRepositoryToMapAdapter<>(this);
    }
}
