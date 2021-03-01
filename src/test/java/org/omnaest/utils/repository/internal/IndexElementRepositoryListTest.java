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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;
import org.omnaest.utils.repository.IndexElementRepository;

public class IndexElementRepositoryListTest
{
    private IndexElementRepository<String> elementRepository = IndexElementRepository.of(new HashMap<>());

    @Test
    public void test() throws Exception
    {
        List<String> list = this.elementRepository.asList();

        assertTrue(list.isEmpty());

        list.add("a");
        assertEquals(1, list.size());
        assertEquals("a", list.get(0));

        list.add("b");
        assertEquals(2, list.size());
        assertEquals("a", list.get(0));
        assertEquals("b", list.get(1));

        list.add("b");
        assertEquals(3, list.size());
        assertEquals("b", list.get(2));

        list.remove(0);
        assertEquals("b", list.get(0));
        assertEquals("b", list.get(1));
        assertEquals(Arrays.asList("b", "b"), list);
    }

}
