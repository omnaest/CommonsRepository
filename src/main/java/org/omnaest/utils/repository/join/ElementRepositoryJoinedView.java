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
package org.omnaest.utils.repository.join;

import java.util.function.Predicate;
import java.util.stream.Stream;

import org.omnaest.utils.EnumUtils;
import org.omnaest.utils.StreamUtils;
import org.omnaest.utils.element.bi.BiElement;
import org.omnaest.utils.element.lar.LeftAndRight;
import org.omnaest.utils.optional.NullOptional;
import org.omnaest.utils.repository.ElementRepository;
import org.omnaest.utils.repository.ImmutableElementRepository;

public class ElementRepositoryJoinedView<I, D1, D2> implements ImmutableElementRepository<I, BiElement<D1, D2>>
{
    private ElementRepository<I, D1>      elementRepositoryLeft;
    private ElementRepository<I, D2>      elementRepositoryRight;
    private Predicate<LeftAndRight<I, I>> mergedIdFilter;

    public ElementRepositoryJoinedView(ElementRepository<I, D1> elementRepositoryLeft, ElementRepository<I, D2> elementRepositoryRight,
                                       Predicate<LeftAndRight<I, I>> mergedIdFilter)
    {
        super();
        this.elementRepositoryLeft = elementRepositoryLeft;
        this.elementRepositoryRight = elementRepositoryRight;
        this.mergedIdFilter = mergedIdFilter;
    }

    @Override
    public NullOptional<BiElement<D1, D2>> get(I id)
    {
        NullOptional<D1> element1 = this.elementRepositoryLeft.get(id);
        NullOptional<D2> element2 = this.elementRepositoryRight.get(id);
        return NullOptional.ofPresenceAndNullable(element1.isPresent() || element2.isPresent(),
                                                  () -> BiElement.of(element1.orElse(null), element2.orElse(null)));
    }

    @Override
    public Stream<I> ids(IdOrder idOrder)
    {
        return EnumUtils.decideOn(idOrder)
                        .ifEqualTo(IdOrder.ARBITRARY,
                                   () -> StreamUtils.concat(this.elementRepositoryLeft.ids(idOrder), this.elementRepositoryRight.ids(idOrder))
                                                    .distinct()
                                                    .map(id ->
                                                    {
                                                        I left = this.elementRepositoryLeft.containsId(id) ? id : null;
                                                        I right = this.elementRepositoryRight.containsId(id) ? id : null;
                                                        return new LeftAndRight<>(left, right);
                                                    })
                                                    .filter(this.mergedIdFilter)
                                                    .map(lar -> lar.hasLeft() ? lar.getLeft() : lar.getRight()))
                        .orElseThrow(() -> new IllegalArgumentException("Unsupported IdOrder value: " + idOrder));
    }

    @Override
    public long size()
    {
        return this.ids()
                   .count();
    }

    @Override
    public String toString()
    {
        return "ElementRepositoryJoiner [elementRepositoryLeft=" + this.elementRepositoryLeft + ", elementRepositoryRight=" + this.elementRepositoryRight + "]";
    }

    @Override
    public Class<I> getKeyType()
    {
        return this.elementRepositoryLeft.getKeyType();
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public Class<BiElement<D1, D2>> getDataType()
    {
        return (Class) BiElement.class;
    }

}
