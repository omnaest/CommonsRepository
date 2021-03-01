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
package org.omnaest.utils.repository.map;

import java.util.Map;

import org.omnaest.utils.repository.ElementRepository;

/**
 * A {@link ElementRepositoryMap} is a {@link Map} where the key and values are stored in a {@link ElementRepository} to save memory. Only the information
 * necessary to
 * build up the internal hash or tree structure remains in memory.
 * 
 * @see ConcurrentRepositoryHashMap
 * @author omnaest
 * @param <K>
 * @param <V>
 */
public interface ElementRepositoryMap<K, V> extends Map<K, V>
{

    /**
     * Returns an {@link ElementRepositoryMap} based on a {@link ElementRepository} for the keys and an {@link ElementRepository} for the values
     * 
     * @param keyRepository
     * @param valueRepository
     * @return
     */
    public static <K, V, I1, I2> ElementRepositoryMap<K, V> of(ElementRepository<I1, K> keyRepository, ElementRepository<I2, V> valueRepository)
    {
        return new BiElementRepositoryMap<>(keyRepository, valueRepository);
    }

    public static <K, V, I> ElementRepositoryMap<K, V> ofKeyRepository(ElementRepository<I, K> keyRepository)
    {
        return new KeyElementRepositoryMap<>(keyRepository);
    }

    public static <K, V, I> ElementRepositoryMap<K, V> ofValueRepository(ElementRepository<I, V> valueRepository)
    {
        return new ValueElementRepositoryMap<>(valueRepository);
    }

    /**
     * Calls {@link ElementRepository#close()} on both underlying {@link ElementRepository}s
     */
    public void close();

    /**
     * Copies the current {@link ElementRepositoryMap} entries into another given {@link Map}
     * 
     * @param map
     * @return the given map
     */
    public default <M extends Map<K, V>> M copyInto(M map)
    {
        if (map != null)
        {
            map.putAll(this);
        }
        return map;
    }
}
