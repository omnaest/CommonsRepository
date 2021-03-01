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
package org.omnaest.utils.repository.map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.LinkedHashMap;
import java.util.SortedMap;
import java.util.TreeMap;

import org.junit.Test;
import org.omnaest.utils.repository.ElementRepository;

/**
 * @see BiElementRepositoryMap
 * @author omnaest
 */
public class BiElementRepositoryMapTest
{
    private ElementRepositoryMap<String, String> repositoryMap = new BiElementRepositoryMap<>(ElementRepository.of(new LinkedHashMap<>()),
                                                                                              ElementRepository.of(new LinkedHashMap<>()));

    @Test
    public void test()
    {
        //
        for (int ii = 0; ii < 10000; ii++)
        {
            this.repositoryMap.put("key" + ii, "value" + ii);
            assertEquals("value" + ii, this.repositoryMap.get("key" + ii));
        }

        assertEquals(10000, this.repositoryMap.size());
        assertEquals(10000, this.repositoryMap.keySet()
                                              .size());
        assertEquals(10000, this.repositoryMap.values()
                                              .size());
        assertFalse(this.repositoryMap.isEmpty());

        //
        assertNotNull(this.repositoryMap.remove("key0"));
        assertEquals(10000 - 1, this.repositoryMap.size());

        //
        this.repositoryMap.clear();
        assertTrue(this.repositoryMap.isEmpty());

    }

    @Test
    public void testCopyInto()
    {
        for (int ii = 0; ii < 100; ii++)
        {
            this.repositoryMap.put("key" + ii, "value" + ii);
        }

        SortedMap<String, String> sortedMap = this.repositoryMap.copyInto(new TreeMap<>());
        assertEquals(100, sortedMap.size());
    }
}
