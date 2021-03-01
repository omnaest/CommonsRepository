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
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface AppendableElementRepository<I, D>
{
    /**
     * Adds a new data element to the {@link ElementRepository} and returns its reference identifier
     * 
     * @param element
     * @return
     */
    public I add(D element);

    /**
     * Adds multiple elements
     * 
     * @param elements
     * @return
     */
    public default Stream<I> addAll(Stream<D> elements)
    {
        return Optional.ofNullable(elements)
                       .orElse(Stream.empty())
                       .map(this::add);
    }

    /**
     * Adds multiple elements
     * 
     * @param elements
     * @return
     */
    public default List<I> addAll(Collection<D> elements)
    {
        return this.addAll(Optional.ofNullable(elements)
                                   .orElse(Collections.emptyList())
                                   .stream())
                   .collect(Collectors.toList());
    }

    /**
     * @see #addAll(Stream)
     * @param elements
     * @return
     */
    @SuppressWarnings("unchecked")
    public default Stream<I> addAll(D... elements)
    {
        return this.addAll(Arrays.asList(elements)
                                 .stream());
    }
}
