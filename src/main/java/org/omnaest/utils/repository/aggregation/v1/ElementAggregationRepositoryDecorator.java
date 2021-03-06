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

public class ElementAggregationRepositoryDecorator<I, D> implements ElementAggregationRepositoryV1<I, D>
{
    protected ElementAggregationRepositoryV1<I, D> repository;

    public ElementAggregationRepositoryDecorator(ElementAggregationRepositoryV1<I, D> repository)
    {
        super();
        this.repository = repository;
    }

    @Override
    public Stream<D> get(I id)
    {
        return this.repository.get(id);
    }

    @Override
    public void add(I id, D element)
    {
        this.repository.add(id, element);
    }

    @Override
    public void addAll(List<BiElement<I, D>> list)
    {
        this.repository.addAll(list);
    }

    @Override
    public Stream<I> ids()
    {
        return this.repository.ids();
    }

    @Override
    public void remove(I id)
    {
        this.repository.remove(id);
    }

    @Override
    public ElementAggregationRepositoryV1<I, D> clear()
    {
        return this.repository.clear();
    }

    @Override
    public long size()
    {
        return this.repository.size();
    }

}
