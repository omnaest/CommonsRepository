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

import java.util.Map;
import java.util.WeakHashMap;

import org.omnaest.utils.repository.ElementRepository;

public class WeakHashMapDecoratingElementRepository<I, D> extends ElementRepositoryDecorator<I, D>
{
    private Map<I, D> cache = new WeakHashMap<>();

    public WeakHashMapDecoratingElementRepository(ElementRepository<I, D> elementRepository)
    {
        super(elementRepository);
    }

    @Override
    public I add(D element)
    {
        I id = super.add(element);
        this.cache.put(id, element);
        return id;
    }

    @Override
    public void put(I id, D element)
    {
        this.cache.put(id, element);
        super.put(id, element);
    }

    @Override
    public void putAll(Map<I, D> map)
    {
        if (map != null)
        {
            map.forEach(this::put);
        }
    }

    @Override
    public void remove(I id)
    {
        this.cache.remove(id);
        super.remove(id);
    }

    @Override
    public D getValue(I id)
    {
        D retval = this.cache.get(id);

        if (retval == null)
        {
            retval = super.getValue(id);
            this.cache.put(id, retval);
        }

        return retval;
    }

    @Override
    public ElementRepository<I, D> clear()
    {
        this.cache.clear();
        return super.clear();
    }

    @Override
    public String toString()
    {
        return "WeakHashMapDecoratingElementRepository [cache=" + this.cache + "]";
    }

}
