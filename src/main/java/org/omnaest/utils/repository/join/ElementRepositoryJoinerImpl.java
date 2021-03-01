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

import org.omnaest.utils.element.bi.BiElement;
import org.omnaest.utils.element.lar.LeftAndRight;
import org.omnaest.utils.repository.ElementRepository;
import org.omnaest.utils.repository.ImmutableElementRepository;

public class ElementRepositoryJoinerImpl<I, D1, D2> implements ElementRepositoryJoiner<I, D1, D2>
{
    private ElementRepository<I, D1> elementRepositoryLeft;
    private ElementRepository<I, D2> elementRepositoryRight;

    public ElementRepositoryJoinerImpl(ElementRepository<I, D1> elementRepositoryLeft, ElementRepository<I, D2> elementRepositoryRight)
    {
        super();
        this.elementRepositoryLeft = elementRepositoryLeft;
        this.elementRepositoryRight = elementRepositoryRight;
    }

    @Override
    public ImmutableElementRepository<I, BiElement<D1, D2>> inner()
    {
        Predicate<LeftAndRight<I, I>> mergedIdFilter = lar -> lar.hasBoth();
        return new ElementRepositoryJoinedView<>(this.elementRepositoryLeft, this.elementRepositoryRight, mergedIdFilter);
    }

    @Override
    public ImmutableElementRepository<I, BiElement<D1, D2>> leftOuter()
    {
        Predicate<LeftAndRight<I, I>> mergedIdFilter = lar -> lar.hasLeft();
        return new ElementRepositoryJoinedView<>(this.elementRepositoryLeft, this.elementRepositoryRight, mergedIdFilter);
    }

    @Override
    public ImmutableElementRepository<I, BiElement<D1, D2>> rightOuter()
    {
        Predicate<LeftAndRight<I, I>> mergedIdFilter = lar -> lar.hasRight();
        return new ElementRepositoryJoinedView<>(this.elementRepositoryLeft, this.elementRepositoryRight, mergedIdFilter);
    }

    @Override
    public ImmutableElementRepository<I, BiElement<D1, D2>> outer()
    {
        Predicate<LeftAndRight<I, I>> mergedIdFilter = lar -> lar.hasAny();
        return new ElementRepositoryJoinedView<>(this.elementRepositoryLeft, this.elementRepositoryRight, mergedIdFilter);
    }

}
