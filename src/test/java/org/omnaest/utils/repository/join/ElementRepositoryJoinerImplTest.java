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

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.junit.Test;
import org.omnaest.utils.element.bi.BiElement;
import org.omnaest.utils.repository.ElementRepositoryUtils;
import org.omnaest.utils.repository.ImmutableElementRepository;
import org.omnaest.utils.repository.IndexElementRepository;

public class ElementRepositoryJoinerImplTest
{

    @Test
    public void testInner() throws Exception
    {
        IndexElementRepository<String> repositoryLeft = ElementRepositoryUtils.newConcurrentHashMapIndexElementRepository();
        IndexElementRepository<String> repositoryRight = ElementRepositoryUtils.newConcurrentHashMapIndexElementRepository();

        ImmutableElementRepository<Long, BiElement<String, String>> result = repositoryLeft.join(repositoryRight)
                                                                                           .inner();

        repositoryLeft.put(0l, "l0");
        repositoryLeft.put(1l, "l1");

        repositoryRight.put(1l, "r1");
        repositoryRight.put(2l, "r2");

        assertEquals(1, result.size());
        assertEquals(BiElement.of("l1", "r1"), result.getValue(1l));

        assertEquals(Arrays.asList(1l), result.ids()
                                              .collect(Collectors.toList()));
    }

    @Test
    public void testOuter() throws Exception
    {
        IndexElementRepository<String> repositoryLeft = ElementRepositoryUtils.newConcurrentHashMapIndexElementRepository();
        IndexElementRepository<String> repositoryRight = ElementRepositoryUtils.newConcurrentHashMapIndexElementRepository();

        ImmutableElementRepository<Long, BiElement<String, String>> result = repositoryLeft.join(repositoryRight)
                                                                                           .outer();

        repositoryLeft.put(0l, "l0");
        repositoryLeft.put(1l, "l1");

        repositoryRight.put(1l, "r1");
        repositoryRight.put(2l, "r2");

        assertEquals(3, result.size());
        assertEquals(BiElement.of("l0", null), result.getValue(0l));
        assertEquals(BiElement.of("l1", "r1"), result.getValue(1l));
        assertEquals(BiElement.of(null, "r2"), result.getValue(2l));

        assertEquals(Arrays.asList(0l, 1l, 2l), result.ids()
                                                      .collect(Collectors.toList()));
    }

    @Test
    public void testLeftOuter() throws Exception
    {
        IndexElementRepository<String> repositoryLeft = ElementRepositoryUtils.newConcurrentHashMapIndexElementRepository();
        IndexElementRepository<String> repositoryRight = ElementRepositoryUtils.newConcurrentHashMapIndexElementRepository();

        ImmutableElementRepository<Long, BiElement<String, String>> result = repositoryLeft.join(repositoryRight)
                                                                                           .leftOuter();
        repositoryLeft.put(0l, "l0");
        repositoryLeft.put(1l, "l1");

        repositoryRight.put(1l, "r1");
        repositoryRight.put(2l, "r2");

        assertEquals(2, result.size());
        assertEquals(BiElement.of("l0", null), result.getValue(0l));
        assertEquals(BiElement.of("l1", "r1"), result.getValue(1l));

        assertEquals(Arrays.asList(0l, 1l), result.ids()
                                                  .collect(Collectors.toList()));
    }

    @Test
    public void testRightOuter() throws Exception
    {
        IndexElementRepository<String> repositoryLeft = ElementRepositoryUtils.newConcurrentHashMapIndexElementRepository();
        IndexElementRepository<String> repositoryRight = ElementRepositoryUtils.newConcurrentHashMapIndexElementRepository();

        ImmutableElementRepository<Long, BiElement<String, String>> result = repositoryLeft.join(repositoryRight)
                                                                                           .rightOuter();

        repositoryLeft.put(0l, "l0");
        repositoryLeft.put(1l, "l1");

        repositoryRight.put(1l, "r1");
        repositoryRight.put(2l, "r2");

        assertEquals(2, result.size());
        assertEquals(BiElement.of("l1", "r1"), result.getValue(1l));
        assertEquals(BiElement.of(null, "r2"), result.getValue(2l));

        assertEquals(Arrays.asList(1l, 2l), result.ids()
                                                  .collect(Collectors.toList()));
    }

}
