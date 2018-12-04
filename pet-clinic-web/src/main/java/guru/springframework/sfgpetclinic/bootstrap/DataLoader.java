package guru.springframework.sfgpetclinic.bootstrap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.model.Vet;
import guru.springframework.sfgpetclinic.services.OwnerService;
import guru.springframework.sfgpetclinic.services.VetService;

@Component
public class DataLoader
    implements CommandLineRunner
{
    private final Logger       logger = LoggerFactory.getLogger(getClass());
    private final OwnerService ownerService;
    private final VetService   vetService;

    // @Autowired
    public DataLoader(final OwnerService ownerService, final VetService vetService)
    {
        super();

        this.ownerService = ownerService;
        this.vetService = vetService;
    }

    @Override
    public void run(final String... args)
        throws Exception
    {
        final Owner owner1 = new Owner();
        owner1.setId(1L);
        owner1.setFirstName("Michael");
        owner1.setLastName("Weston");
        ownerService.save(owner1);

        final Owner owner2 = new Owner();
        owner2.setId(2L);
        owner2.setFirstName("Fiona");
        owner2.setLastName("Glenanne");
        ownerService.save(owner2);

        getLogger().info("Created and stored Owners using {} ...", ownerService.getClass().getSimpleName());

        final Vet vet1 = new Vet();
        vet1.setId(1L);
        vet1.setFirstName("Sam");
        vet1.setLastName("Axe");
        vetService.save(vet1);

        final Vet vet2 = new Vet();
        vet2.setId(2L);
        vet2.setFirstName("Jessie");
        vet2.setLastName("Porter");
        vetService.save(vet2);

        getLogger().info("Created and stored Vets using {} ...", vetService.getClass().getSimpleName());
    }

    private Logger getLogger()
    {
        return logger;
    }
}