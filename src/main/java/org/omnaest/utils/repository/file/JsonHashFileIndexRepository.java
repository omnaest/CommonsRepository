package org.omnaest.utils.repository.file;

import java.io.File;
import java.util.stream.Stream;

import org.omnaest.utils.JSONHelper;
import org.omnaest.utils.JSONHelper.JsonStringConverter;
import org.omnaest.utils.file.HashTextFileIndex;
import org.omnaest.utils.optional.NullOptional;
import org.omnaest.utils.repository.MapElementRepository;

/**
 * {@link MapElementRepository} based on {@link HashTextFileIndex} and {@link JSONHelper}
 * 
 * @author omnaest
 */
public class JsonHashFileIndexRepository<I, D> implements MapElementRepository<I, D>
{
    private HashTextFileIndex      fileIndex;
    private JsonStringConverter<I> keyConverter;
    private JsonStringConverter<D> dataConverter;

    public JsonHashFileIndexRepository(File directory, int capacity, Class<I> keyType, Class<D> dataType)
    {
        super();
        this.fileIndex = new HashTextFileIndex(directory, capacity);
        this.keyConverter = JSONHelper.converter(keyType);
        this.dataConverter = JSONHelper.converter(dataType);
    }

    @Override
    public void put(I id, D element)
    {
        this.fileIndex.put(this.keyConverter.serializer()
                                            .apply(id),
                           this.dataConverter.serializer()
                                             .apply(element));
    }

    @Override
    public void remove(I id)
    {
        this.fileIndex.remove(this.keyConverter.serializer()
                                               .apply(id));
    }

    @Override
    public NullOptional<D> get(I id)
    {
        return NullOptional.of(this.fileIndex.get(this.keyConverter.serializer()
                                                                   .apply(id))
                                             .map(content -> this.dataConverter.deserializer()
                                                                               .apply(content)));
    }

    @Override
    public Stream<I> ids(IdOrder idOrder)
    {
        if (IdOrder.ARBITRARY.equals(idOrder))
        {
            return this.fileIndex.keys()
                                 .map(key -> this.keyConverter.deserializer()
                                                              .apply(key));
        }
        else
        {
            throw new IllegalArgumentException("Only arbitrary mode is supported");
        }
    }

    @Override
    public MapElementRepository<I, D> clear()
    {
        this.fileIndex.clear();
        return this;
    }

}
