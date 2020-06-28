package org.omnaest.utils.repository.internal;

import java.util.stream.Stream;

import org.omnaest.utils.functional.BidirectionalFunction;
import org.omnaest.utils.optional.NullOptional;
import org.omnaest.utils.repository.ElementRepository;

public class MappedElementRepository<I, OI, D, OD> implements ElementRepository<I, D>
{
    protected ElementRepository<OI, OD>    elementRepository;
    protected BidirectionalFunction<OD, D> dataMapper;
    protected BidirectionalFunction<OI, I> idMapper;

    public MappedElementRepository(ElementRepository<OI, OD> elementRepository, BidirectionalFunction<OI, I> idMapper, BidirectionalFunction<OD, D> dataMapper)
    {
        super();
        this.elementRepository = elementRepository;
        this.idMapper = idMapper;
        this.dataMapper = dataMapper;
    }

    @Override
    public NullOptional<D> get(I id)
    {
        return this.elementRepository.get(this.idMapper.backward()
                                                       .apply(id))
                                     .map(this.dataMapper.forward());
    }

    @Override
    public Stream<I> ids(IdOrder idOrder)
    {
        return this.elementRepository.ids(idOrder)
                                     .map(this.idMapper.forward());
    }

    @Override
    public long size()
    {
        return this.elementRepository.size();
    }

    @Override
    public void put(I id, D element)
    {
        this.elementRepository.put(this.idMapper.backward()
                                                .apply(id),
                                   this.dataMapper.backward()
                                                  .apply(element));
    }

    @Override
    public void remove(I id)
    {
        this.elementRepository.remove(this.idMapper.backward()
                                                   .apply(id));
    }

    @Override
    public I add(D element)
    {
        return this.idMapper.forward()
                            .apply(this.elementRepository.add(this.dataMapper.backward()
                                                                             .apply(element)));
    }

    @Override
    public ElementRepository<I, D> clear()
    {
        this.elementRepository.clear();
        return this;
    }

}
