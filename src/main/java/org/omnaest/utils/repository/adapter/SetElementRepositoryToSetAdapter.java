package org.omnaest.utils.repository.adapter;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Stream;

import org.omnaest.utils.repository.SetElementRepository;
import org.omnaest.utils.stream.Streamable;

public class SetElementRepositoryToSetAdapter<I> implements Set<I>, Streamable<I>
{
    private SetElementRepository<I> repository;

    public SetElementRepositoryToSetAdapter(SetElementRepository<I> repository)
    {
        super();
        this.repository = repository;
    }

    @Override
    public int size()
    {
        return (int) this.repository.size();
    }

    @Override
    public boolean isEmpty()
    {
        return this.size() == 0;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean contains(Object o)
    {
        try
        {
            return this.repository.contains((I) o);
        }
        catch (ClassCastException e)
        {
            return false;
        }
    }

    @Override
    public Object[] toArray()
    {
        return this.repository.stream()
                              .toArray();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T[] toArray(T[] a)
    {
        return this.repository.stream()
                              .toArray(size -> (T[]) Array.newInstance(this.repository.getElementType(), size));
    }

    @Override
    public boolean add(I e)
    {
        boolean result = !this.repository.contains(e);
        this.repository.add(e);
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean remove(Object o)
    {
        boolean result = this.repository.contains((I) o);
        this.repository.remove((I) o);
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean containsAll(Collection<?> c)
    {
        return this.repository.containsAll((Iterable<I>) c);
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean addAll(Collection<? extends I> c)
    {
        this.repository.addAll((Iterable<I>) c);
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean retainAll(Collection<?> c)
    {
        this.repository.retainAll((Iterable<I>) c);
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean removeAll(Collection<?> c)
    {
        this.repository.removeAll((Iterable<I>) c);
        return true;
    }

    @Override
    public void clear()
    {
        this.repository.clear();
    }

    @Override
    public Stream<I> stream()
    {
        return this.repository.stream();
    }

    @Override
    public Iterator<I> iterator()
    {
        return this.stream()
                   .iterator();
    }

}
