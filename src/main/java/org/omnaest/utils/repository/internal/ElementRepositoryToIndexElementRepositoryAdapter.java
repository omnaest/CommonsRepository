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
package org.omnaest.utils.repository.internal;

import java.util.stream.Stream;

import org.omnaest.utils.optional.NullOptional;
import org.omnaest.utils.repository.ElementRepository;
import org.omnaest.utils.repository.IndexElementRepository;

public class ElementRepositoryToIndexElementRepositoryAdapter<D> implements IndexElementRepository<D>
{
    private ElementRepository<Long, D> elementRepository;

    public ElementRepositoryToIndexElementRepositoryAdapter(ElementRepository<Long, D> elementRepository)
    {
        super();
        this.elementRepository = elementRepository;
    }

    @Override
    public Long add(D element)
    {
        return this.elementRepository.add(element);
    }

    @Override
    public void put(Long id, D element)
    {
        this.elementRepository.put(id, element);
    }

    @Override
    public void remove(Long id)
    {
        this.elementRepository.remove(id);
    }

    @Override
    public NullOptional<D> get(Long id)
    {
        return this.elementRepository.get(id);
    }

    @Override
    public IndexElementRepository<D> clear()
    {
        this.elementRepository.clear();
        return this;
    }

    @Override
    public IndexElementRepository<D> asWeakCached()
    {
        return IndexElementRepository.of(this.elementRepository.asWeakCached());
    }

    @Override
    public IndexElementRepository<D> asSynchronized()
    {
        return IndexElementRepository.of(this.elementRepository.asSynchronized());
    }

    @Override
    public long size()
    {
        return this.elementRepository.size();
    }

    @Override
    public Stream<Long> ids(IdOrder idOrder)
    {
        return this.elementRepository.ids(idOrder);
    }

    @Override
    public void close()
    {
        this.elementRepository.close();
    }

}
