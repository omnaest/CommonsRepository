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

import org.omnaest.utils.element.bi.BiElement;
import org.omnaest.utils.repository.ElementRepository;
import org.omnaest.utils.repository.ImmutableElementRepository;

public interface ElementRepositoryJoiner<I, DL, DR>
{
    public ImmutableElementRepository<I, BiElement<DL, DR>> inner();

    public ImmutableElementRepository<I, BiElement<DL, DR>> leftOuter();

    public ImmutableElementRepository<I, BiElement<DL, DR>> rightOuter();

    public ImmutableElementRepository<I, BiElement<DL, DR>> outer();

    public static <I, DL, DR> ElementRepositoryJoiner<I, DL, DR> of(ElementRepository<I, DL> elementRepositoryLeft,
                                                                    ElementRepository<I, DR> elementRepositoryRight)
    {
        return new ElementRepositoryJoinerImpl<>(elementRepositoryLeft, elementRepositoryRight);
    }

}
