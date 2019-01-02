package guru.springframework.sfgpetclinic.services.springdatajpa;

import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import guru.springframework.sfgpetclinic.model.Visit;
import guru.springframework.sfgpetclinic.repositories.VisitRepository;
import guru.springframework.sfgpetclinic.services.VisitService;

@Service
@Profile(value = { "springdatajpa" })
public class VisitSDJpaService
    implements VisitService
{
    private final VisitRepository visitRepository;

    public VisitSDJpaService(final VisitRepository visitRepository)
    {
        this.visitRepository = visitRepository;
    }

    @Override
    public void delete(final Visit visit)
    {
        visitRepository.delete(visit);
    }

    @Override
    public void deleteById(final Long id)
    {
        visitRepository.deleteById(id);
    }

    @Override
    public Set<Visit> findAll()
    {
        final Set<Visit> visits = new HashSet<>();
        visitRepository.findAll()
                       .forEach(visits::add);

        return visits;
    }

    @Override
    public Visit findById(final Long id)
    {
        return visitRepository.findById(id)
                              .orElse(null);
    }

    @Override
    public Visit save(final Visit visit)
    {
        return visitRepository.save(visit);
    }
}