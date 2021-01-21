package org.omnaest.utils.repository.file;

import static org.junit.Assert.assertEquals;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Test;
import org.omnaest.utils.FileUtils;
import org.omnaest.utils.MapUtils;
import org.omnaest.utils.SetUtils;
import org.omnaest.utils.repository.ElementRepositoryUtils;
import org.omnaest.utils.repository.MapElementRepository;

/**
 * @see JsonHashFileIndexRepository
 * @author omnaest
 */
public class JsonHashFileIndexRepositoryTest
{
    private MapElementRepository<String, Domain> elementRepository = ElementRepositoryUtils.newJsonHashFileIndexRepository(FileUtils.createRandomTempDirectoryQuietly()
                                                                                                                                    .get(),
                                                                                                                           Integer.MAX_VALUE, 2, String.class,
                                                                                                                           Domain.class);

    @Test
    public void testPutAndGet() throws Exception
    {
        IntStream.range(0, 10)
                 .forEach(counter ->
                 {
                     this.elementRepository.putAll(MapUtils.builder()
                                                           .put("1", new Domain().setField("field1")
                                                                                 .setValue(42 * counter))
                                                           .put("2", new Domain().setField("field2")
                                                                                 .setValue(43 * counter))
                                                           .build());
                     assertEquals("field1", this.elementRepository.get("1")
                                                                  .get()
                                                                  .getField());
                     assertEquals("field2", this.elementRepository.get("2")
                                                                  .get()
                                                                  .getField());
                     assertEquals(42 * counter, this.elementRepository.get("1")
                                                                      .get()
                                                                      .getValue());
                     assertEquals(43 * counter, this.elementRepository.get("2")
                                                                      .get()
                                                                      .getValue());
                     assertEquals(2, this.elementRepository.size());
                     assertEquals(SetUtils.toSet("1", "2"), this.elementRepository.ids()
                                                                                  .collect(Collectors.toSet()));
                 });
    }

    protected static class Domain
    {
        private String field;
        private int    value;

        public String getField()
        {
            return this.field;
        }

        public Domain setField(String field)
        {
            this.field = field;
            return this;
        }

        public int getValue()
        {
            return this.value;
        }

        public Domain setValue(int value)
        {
            this.value = value;
            return this;
        }

        @Override
        public String toString()
        {
            return "Domain [field=" + this.field + ", value=" + this.value + "]";
        }

    }

}
