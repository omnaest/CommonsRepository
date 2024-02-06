package org.omnaest.utils.repository.adapter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Set;

import org.junit.Test;
import org.omnaest.utils.SetUtils;
import org.omnaest.utils.repository.ElementRepositoryUtils;

public class SetElementRepositoryToSetAdapterTest
{
    @Test
    public void testCRUD() throws Exception
    {
        //
        Set<Long> set = ElementRepositoryUtils.newConcurrentHashMapIndexElementRepository()
                                              .asSetElementRepository(new Object())
                                              .asSet();
        assertTrue(set.isEmpty());
        assertEquals(0, set.size());
        assertEquals(0, set.stream()
                           .count());

        //
        set.add(1l);
        set.add(2l);
        set.add(3l);
        assertFalse(set.isEmpty());
        assertEquals(3, set.size());
        assertEquals(SetUtils.toSet(1l, 2l, 3l), set);

        //
        set.remove(1l);
        assertEquals(2, set.size());
        assertEquals(SetUtils.toSet(2l, 3l), set);

        //
        set.retainAll(Arrays.asList(2l));
        assertEquals(1, set.size());
        assertEquals(SetUtils.toSet(2l), set);
    }

}
