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

import java.util.concurrent.ConcurrentHashMap;

import org.omnaest.utils.repository.ElementRepository;

public class ValueElementRepositoryMap<K, V> extends BiElementRepositoryMap<K, V>
{

    public <I> ValueElementRepositoryMap(ElementRepository<I, V> valueRepository)
    {
        super(ElementRepository.of(new ConcurrentHashMap<>()), valueRepository);
    }

}
