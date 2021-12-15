package org.omnaest.utils.repository.adapter;

import static org.junit.Assert.assertEquals;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.Test;
import org.omnaest.utils.repository.ElementRepository;

/**
 * @see MapElementRepositoryToMapAdapter
 * @author omnaest
 */
public class MapElementRepositoryToMapAdapterTest
{

    @Test
    public void testEntrySet() throws Exception
    {
        //
        Map<Long, String> map = ElementRepository.of(new ConcurrentHashMap<Long, String>())
                                                 .asMap();
        assertEquals(true, map.isEmpty());

        //
        map.put(1l, "value1");
        map.put(2l, "value2");

        //
        assertEquals(2, map.size());
        assertEquals("value1", map.get(1l));
        assertEquals("value2", map.get(2l));

        //
        map.remove(1l);
        assertEquals(1, map.size());
        assertEquals(null, map.get(1l));
        assertEquals("value2", map.get(2l));

        //
        map.clear();
        assertEquals(true, map.isEmpty());
    }

}
