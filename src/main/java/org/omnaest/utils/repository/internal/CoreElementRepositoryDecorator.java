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

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.Stream;

import org.omnaest.utils.element.bi.BiElement;
import org.omnaest.utils.optional.NullOptional;
import org.omnaest.utils.repository.MapElementRepository;

public class CoreElementRepositoryDecorator<I, D> implements MapElementRepository<I, D>
{
    protected MapElementRepository<I, D> elementRepository;

    public CoreElementRepositoryDecorator(MapElementRepository<I, D> elementRepository)
    {
        super();
        this.elementRepository = elementRepository;
    }

    @Override
    public void put(I id, D element)
    {
        this.elementRepository.put(id, element);
    }

    @Override
    public void putAll(Map<I, D> map)
    {
        this.elementRepository.putAll(map);
    }

    @Override
    public D getValue(I id)
    {
        return this.elementRepository.getValue(id);
    }

    @Override
    public void remove(I id)
    {
        this.elementRepository.remove(id);
    }

    @Override
    public boolean containsId(I id)
    {
        return this.elementRepository.containsId(id);
    }

    @Override
    public MapElementRepository<I, D> clear()
    {
        this.elementRepository.clear();
        return this;
    }

    @Override
    public NullOptional<D> get(I id)
    {
        return this.elementRepository.get(id);
    }

    @Override
    public void forEach(Consumer<? super BiElement<I, D>> action)
    {
        this.elementRepository.forEach(action);
    }

    @Override
    public Stream<I> ids(IdOrder idOrder)
    {
        return this.elementRepository.ids(idOrder);
    }

    @Override
    public Stream<BiElement<I, D>> entries()
    {
        return this.elementRepository.entries();
    }

    @Override
    public void close()
    {
        this.elementRepository.close();
    }

    @Override
    public long size()
    {
        return this.elementRepository.size();
    }

    @Override
    public boolean isEmpty()
    {
        return this.elementRepository.isEmpty();
    }

    @Override
    public Spliterator<BiElement<I, D>> spliterator()
    {
        return this.elementRepository.spliterator();
    }

    @Override
    public D getValueOrDefault(I id, D defaultElement)
    {
        return this.elementRepository.getValueOrDefault(id, defaultElement);
    }

    @Override
    public Map<I, D> getAll(Collection<I> ids)
    {
        return this.elementRepository.getAll(ids);
    }

    @Override
    public Iterator<BiElement<I, D>> iterator()
    {
        return this.elementRepository.iterator();
    }

    @Override
    public Stream<BiElement<I, D>> stream()
    {
        return this.elementRepository.stream();
    }

    @Override
    public Stream<D> values()
    {
        return this.elementRepository.values();
    }

    @Override
    public Class<I> getKeyType()
    {
        return this.elementRepository.getKeyType();
    }

    @Override
    public Class<D> getDataType()
    {
        return this.elementRepository.getDataType();
    }

}
