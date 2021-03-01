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
package org.omnaest.utils.repository.aggregation.v1;

import java.util.List;
import java.util.stream.Stream;

import org.omnaest.utils.element.bi.BiElement;

public interface ElementAggregationRepositoryV1<I, D> extends AutoCloseable
{
    public Stream<D> get(I id);

    public void add(I id, D element);

    public void addAll(List<BiElement<I, D>> list);

    public Stream<I> ids();

    public void remove(I id);

    public ElementAggregationRepositoryV1<I, D> clear();

    public long size();

    /**
     * Returns an {@link ElementAggregationRepositoryV1} based on a {@link List}
     * 
     * @param list
     * @return
     */
    public static <I, D> ElementAggregationRepositoryV1<I, D> of(List<BiElement<I, D>> list)
    {
        return new ListElementAggregationRepositoryV1<>(list);
    }

    @Override
    public default void close()
    {
        //do nothing
    }
}
