package guru.springframework.sfgpetclinic.services.map;

import static java.util.Collections.unmodifiableSet;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import guru.springframework.sfgpetclinic.model.BaseEntity;
import guru.springframework.sfgpetclinic.services.CrudService;

public abstract class AbstractMapService<T extends BaseEntity>
    implements CrudService<T, Long>
{
    private final Map<Long, T> map = new HashMap<>();

    @Override
    public final void delete(final T entity)
    {
        map.entrySet().removeIf(entry -> entry.getValue().equals(entity));
    }

    @Override
    public final void deleteById(final Long id)
    {
        map.remove(id);
    }

    @Override
    public final Set<T> findAll()
    {
        return unmodifiableSet(new HashSet<>(map.values()));
    }

    @Override
    public final T findById(final Long id)
    {
        return map.get(id);
    }

    @Override
    public T save(final T entity)
    {
        if( entity == null )
        {
            throw new RuntimeException("Entity cannot be null!");
        }

        if( entity.getId() == null )
        {
            entity.setId(getNextId());
        }

        map.put(entity.getId(), entity);

        return entity;
    }

    private Long getNextId()
    {
        try
        {
            return Collections.max(map.keySet()) + 1;
        }
        catch( final NoSuchElementException noSuElEx )
        {
            return 1L;
        }
    }
}