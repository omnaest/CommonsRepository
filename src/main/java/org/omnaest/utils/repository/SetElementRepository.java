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

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.omnaest.utils.repository.adapter.SetElementRepositoryToSetAdapter;
import org.omnaest.utils.repository.internal.ElementRepositoryToSetElementRepositoryAdapter;
import org.omnaest.utils.stream.Streamable;

/**
 * {@link Set} like {@link ElementRepository}
 * 
 * @see ElementRepository
 * @author omnaest
 * @param <I>
 * @param <D>
 */
public interface SetElementRepository<I> extends Streamable<I>, AutoCloseable
{
    /**
     * Closes the underlying repository. This is an optional method.
     */
    @Override
    public default void close()
    {
        //do nothing
    }

    public SetElementRepository<I> clear();

    public default SetElementRepository<I> add(I element)
    {
        return this.addAll(Optional.ofNullable(element)
                                   .map(Arrays::asList)
                                   .orElse(Collections.emptyList()));
    }

    public SetElementRepository<I> addAll(Iterable<I> elements);

    public SetElementRepository<I> removeAll(Iterable<I> elements);

    public default SetElementRepository<I> remove(I element)
    {
        return this.removeAll(Optional.ofNullable(element)
                                      .map(Arrays::asList)
                                      .orElse(Collections.emptyList()));
    }

    public SetElementRepository<I> retainAll(Iterable<I> elements);

    public default boolean contains(I element)
    {
        return this.containsAll(Optional.ofNullable(element)
                                        .map(Arrays::asList)
                                        .orElse(Collections.emptyList()));
    }

    public boolean containsAll(Iterable<I> elements);

    public long size();

    /**
     * Returns a {@link Map} view of this {@link SetElementRepository}
     * 
     * @return
     */
    public default Set<I> asSet()
    {
        return new SetElementRepositoryToSetAdapter<>(this);
    }

    public Class<I> getElementType();

    public static <I, D> SetElementRepository<I> of(ElementRepository<I, D> elementRepository)
    {
        D nullValue = null;
        return of(elementRepository, nullValue);
    }

    public static <I, D> SetElementRepository<I> of(ElementRepository<I, D> elementRepository, D nullValue)
    {
        return new ElementRepositoryToSetElementRepositoryAdapter<>(elementRepository, nullValue);
    }

}
