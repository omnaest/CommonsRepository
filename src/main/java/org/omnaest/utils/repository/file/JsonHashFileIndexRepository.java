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
package org.omnaest.utils.repository.file;

import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.omnaest.utils.JSONHelper;
import org.omnaest.utils.JSONHelper.JsonStringConverter;
import org.omnaest.utils.file.ConcurrentHashTextFileIndex;
import org.omnaest.utils.file.HashTextFileIndex;
import org.omnaest.utils.file.MultithreadedHashTextFileIndex;
import org.omnaest.utils.file.TextFileIndex;
import org.omnaest.utils.optional.NullOptional;
import org.omnaest.utils.repository.MapElementRepository;

/**
 * {@link MapElementRepository} based on {@link HashTextFileIndex} and {@link JSONHelper}
 * 
 * @author omnaest
 */
public class JsonHashFileIndexRepository<I, D> implements MapElementRepository<I, D>
{
    private TextFileIndex          fileIndex;
    private JsonStringConverter<I> keyConverter;
    private JsonStringConverter<D> dataConverter;
    private Class<I>               keyType;
    private Class<D>               dataType;

    public JsonHashFileIndexRepository(File directory, int capacity, int numberOfThreads, Class<I> keyType, Class<D> dataType)
    {
        super();
        this.keyType = keyType;
        this.dataType = dataType;
        this.fileIndex = numberOfThreads >= 2 ? new MultithreadedHashTextFileIndex(directory, capacity, numberOfThreads)
                : new ConcurrentHashTextFileIndex(directory, capacity);
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
    public void putAll(Map<I, D> map)
    {
        this.fileIndex.putAll(Optional.ofNullable(map)
                                      .orElse(Collections.emptyMap())
                                      .entrySet()
                                      .stream()
                                      .collect(Collectors.toMap(entry -> this.keyConverter.serializer()
                                                                                          .apply(entry.getKey()),
                                                                entry -> this.dataConverter.serializer()
                                                                                           .apply(entry.getValue()))));
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
    public Map<I, D> getAll(Collection<I> ids)
    {
        return this.fileIndex.getAll(Optional.ofNullable(ids)
                                             .orElse(Collections.emptyList())
                                             .stream()
                                             .map(id -> this.keyConverter.serializer()
                                                                         .apply(id))
                                             .collect(Collectors.toList()))
                             .entrySet()
                             .stream()
                             .collect(Collectors.toMap(entry -> this.keyConverter.deserializer()
                                                                                 .apply(entry.getKey()),
                                                       entry -> this.dataConverter.deserializer()
                                                                                  .apply(entry.getValue())));
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

    @Override
    public Class<I> getKeyType()
    {
        return this.keyType;
    }

    @Override
    public Class<D> getDataType()
    {
        return this.dataType;
    }

}
