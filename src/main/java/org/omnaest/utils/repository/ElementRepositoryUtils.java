package org.omnaest.utils.repository;

import java.io.File;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

import org.omnaest.utils.repository.file.JsonHashFileIndexRepository;

public class ElementRepositoryUtils
{

    private ElementRepositoryUtils()
    {
        super();
    }

    public static <I, D> ElementRepository<I, D> newConcurrentHashMapElementRepository(Supplier<I> idSupplier)
    {
        return ElementRepository.of(new ConcurrentHashMap<>(), idSupplier);
    }

    public static <D> IndexElementRepository<D> newConcurrentHashMapIndexElementRepository()
    {
        return ElementRepository.of(new ConcurrentHashMap<>());
    }

    public static <I, D> MapElementRepository<I, D> newJsonHashFileIndexRepository(File directory, int capacity, Class<I> keyType, Class<D> dataType)
    {
        return new JsonHashFileIndexRepository<>(directory, capacity, keyType, dataType);
    }
}
