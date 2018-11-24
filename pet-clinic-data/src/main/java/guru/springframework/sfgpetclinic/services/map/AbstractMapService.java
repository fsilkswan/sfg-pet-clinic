package guru.springframework.sfgpetclinic.services.map;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import guru.springframework.sfgpetclinic.services.CrudService;

public abstract class AbstractMapService<T, ID>
    implements CrudService<T, ID>
{
    protected Map<ID, T> map = new HashMap<>();

    @Override
    public void delete(final T entity)
    {
        map.entrySet().removeIf(entry -> entry.getValue().equals(entity));
    }

    @Override
    public void deleteById(final ID id)
    {
        map.remove(id);
    }

    @Override
    public Set<T> findAll()
    {
        return new HashSet<>(map.values());
    }

    @Override
    public T findById(final ID id)
    {
        return map.get(id);
    }

    public T save(final ID id, final T entity)
    {
        map.put(id, entity);

        return entity;
    }
}