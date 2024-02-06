package org.omnaest.utils.repository.internal;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.omnaest.utils.MapUtils;
import org.omnaest.utils.StreamUtils;
import org.omnaest.utils.repository.ElementRepository;
import org.omnaest.utils.repository.SetElementRepository;

public class ElementRepositoryToSetElementRepositoryAdapter<I> implements SetElementRepository<I>
{
    private ElementRepository<I, Object> elementRepository;
    private Object                       nullValue;

    @SuppressWarnings("unchecked")
    public ElementRepositoryToSetElementRepositoryAdapter(ElementRepository<I, ?> elementRepository, Object nullValue)
    {
        super();
        this.elementRepository = (ElementRepository<I, Object>) elementRepository;
        this.nullValue = nullValue;
    }

    @Override
    public Stream<I> stream()
    {
        return this.elementRepository.ids();
    }

    @Override
    public SetElementRepository<I> clear()
    {
        this.elementRepository.clear();
        return this;
    }

    @Override
    public SetElementRepository<I> addAll(Iterable<I> elements)
    {
        this.elementRepository.putAll(MapUtils.toMap(Optional.ofNullable(elements)
                                                             .map(StreamUtils::fromIterable)
                                                             .orElse(Stream.empty())
                                                             .collect(Collectors.toSet()),
                                                     this.nullValue));
        return this;
    }

    @Override
    public SetElementRepository<I> removeAll(Iterable<I> elements)
    {
        Optional.ofNullable(elements)
                .map(StreamUtils::fromIterable)
                .orElse(Stream.empty())
                .forEach(this.elementRepository::remove);
        return this;
    }

    @Override
    public SetElementRepository<I> retainAll(Iterable<I> elements)
    {
        Set<I> retainableElements = Optional.ofNullable(elements)
                                            .map(StreamUtils::fromIterable)
                                            .orElse(Stream.empty())
                                            .collect(Collectors.toSet());
        Set<I> elementsToRemove = this.elementRepository.ids()
                                                        .filter(element -> !retainableElements.contains(element))
                                                        .collect(Collectors.toSet());
        this.removeAll(elementsToRemove);
        return this;
    }

    @Override
    public boolean containsAll(Iterable<I> elements)
    {
        return Optional.ofNullable(elements)
                       .map(StreamUtils::fromIterable)
                       .orElse(Stream.empty())
                       .allMatch(this.elementRepository::containsId);
    }

    @Override
    public long size()
    {
        return this.elementRepository.size();
    }

    @Override
    public Class<I> getElementType()
    {
        return this.elementRepository.getKeyType();
    }

}
