package org.omnaest.utils.repository.adapter;

import java.util.AbstractMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.omnaest.utils.MapperUtils;
import org.omnaest.utils.repository.MapElementRepository;

/**
 * Adapter for a {@link MapElementRepository} to act as a {@link Map}
 * 
 * @author omnaest
 * @param <K>
 * @param <V>
 */
public class MapElementRepositoryToMapAdapter<K, V> extends AbstractMap<K, V> implements Map<K, V>
{
    private final MapElementRepository<K, V> elementRepository;

    public MapElementRepositoryToMapAdapter(MapElementRepository<K, V> elementRepository)
    {
        super();
        this.elementRepository = elementRepository;
    }

    @Override
    public Set<Entry<K, V>> entrySet()
    {
        return this.elementRepository.entries()
                                     .map(MapperUtils.mapBiElementToEntry())
                                     .collect(Collectors.toSet());
    }

    @Override
    public int size()
    {
        return (int) this.elementRepository.size();
    }

    @Override
    public boolean containsKey(Object key)
    {
        return this.mapToKey(key)
                   .map(this.elementRepository::containsId)
                   .orElse(false);
    }

    @Override
    public V get(Object key)
    {
        return this.mapToKey(key)
                   .map(this.elementRepository::getValue)
                   .orElse(null);
    }

    @SuppressWarnings("unchecked")
    private Optional<K> mapToKey(Object key)
    {
        try
        {
            return Optional.ofNullable((K) key);
        }
        catch (ClassCastException e)
        {
            return Optional.empty();
        }
    }

    @Override
    public V remove(Object key)
    {
        V value = this.get(key);
        this.mapToKey(key)
            .ifPresent(this.elementRepository::remove);
        return value;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void putAll(Map<? extends K, ? extends V> map)
    {
        this.elementRepository.putAll((Map<K, V>) map);
    }

    @Override
    public V put(K key, V value)
    {
        V previousValue = this.get(key);
        this.elementRepository.put(key, value);
        return previousValue;
    }

    @Override
    public void clear()
    {
        this.elementRepository.clear();
    }
}
