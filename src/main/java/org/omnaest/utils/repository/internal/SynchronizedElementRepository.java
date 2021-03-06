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

import org.omnaest.utils.repository.ElementRepository;

/**
 * Synchronized {@link ElementRepository} decorator
 * 
 * @author omnaest
 * @param <I>
 * @param <D>
 */
public class SynchronizedElementRepository<I, D> extends ElementRepositoryDecorator<I, D>
{

    public SynchronizedElementRepository(ElementRepository<I, D> elementRepository)
    {
        super(elementRepository);
    }

    @Override
    public synchronized I add(D element)
    {
        return super.add(element);
    }

    @Override
    public synchronized Stream<I> ids()
    {
        return super.ids();
    }

    @Override
    public synchronized void put(I id, D element)
    {
        super.put(id, element);
    }

    @Override
    public synchronized void remove(I id)
    {
        super.remove(id);
    }

    @Override
    public synchronized D getValue(I id)
    {
        return super.getValue(id);
    }

    @Override
    public synchronized ElementRepository<I, D> clear()
    {
        return super.clear();
    }

    @Override
    public synchronized long size()
    {
        return super.size();
    }

    @Override
    public synchronized boolean isEmpty()
    {
        return super.isEmpty();
    }

    @Override
    public synchronized void close()
    {
        super.close();
    }

    @Override
    public String toString()
    {
        return "SynchronizedElementRepository []";
    }

}
