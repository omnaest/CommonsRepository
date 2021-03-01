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

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Supplier;

import org.junit.Test;
import org.omnaest.utils.repository.ElementRepository;

public class MapAndSupplierElementRepositoryTest
{

    @Test
        public void testGetValue() throws Exception
        {
            AtomicLong counter = new AtomicLong();
            Supplier<Long> idSupplier = () -> counter.getAndIncrement();
            ElementRepository<Long, String> repository = ElementRepository.of(new HashMap<Long, String>(), idSupplier)
                                                                          .asWeakCached()
                                                                          .asSynchronized();
    
            assertEquals("a", repository.getValue(repository.add("a")));
            assertEquals("a", repository.getValue(0l));
            assertEquals("b", repository.getValue(repository.add("b")));
    
            repository.put(0l, "c");
            assertEquals("c", repository.getValue(0l));
        }

}
