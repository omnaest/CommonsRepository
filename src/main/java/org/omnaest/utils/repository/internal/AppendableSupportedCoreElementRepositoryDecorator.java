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

import java.util.function.Supplier;

import org.omnaest.utils.StreamUtils;
import org.omnaest.utils.repository.MapElementRepository;
import org.omnaest.utils.repository.ElementRepository;

public class AppendableSupportedCoreElementRepositoryDecorator<I, D> extends CoreElementRepositoryDecorator<I, D> implements ElementRepository<I, D>
{
    private Supplier<I> idSupplier;

    public AppendableSupportedCoreElementRepositoryDecorator(MapElementRepository<I, D> elementRepository, Supplier<I> idSupplier)
    {
        super(elementRepository);
        this.idSupplier = idSupplier;
    }

    @Override
    public I add(D element)
    {
        I id = StreamUtils.fromSupplier(this.idSupplier)
                          .filter(i -> !this.containsId(i))
                          .findFirst()
                          .get();
        this.put(id, element);
        return id;
    }

    @Override
    public ElementRepository<I, D> clear()
    {
        super.clear();
        return this;
    }

    @Override
    public String toString()
    {
        return "AppendableSupportedCoreElementRepositoryDecorator [idSupplier=" + this.idSupplier + "]";
    }

}
