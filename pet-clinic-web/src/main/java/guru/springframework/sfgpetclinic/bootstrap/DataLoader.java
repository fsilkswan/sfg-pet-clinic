package guru.springframework.sfgpetclinic.bootstrap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.model.PetType;
import guru.springframework.sfgpetclinic.model.Vet;
import guru.springframework.sfgpetclinic.services.OwnerService;
import guru.springframework.sfgpetclinic.services.PetTypeService;
import guru.springframework.sfgpetclinic.services.VetService;

@Component
public class DataLoader
    implements CommandLineRunner
{
    private final Logger         logger = LoggerFactory.getLogger(getClass());
    private final OwnerService   ownerService;
    private final PetTypeService petTypeService;
    private final VetService     vetService;

    // @Autowired
    public DataLoader(final OwnerService ownerService, final VetService vetService, final PetTypeService petTypeService)
    {
        super();

        this.ownerService = ownerService;
        this.vetService = vetService;
        this.petTypeService = petTypeService;
    }

    @Override
    public void run(final String... args)
        throws Exception
    {
        final PetType dogPetType = new PetType();
        dogPetType.setName("Dog");
        petTypeService.save(dogPetType);

        final PetType catPetType = new PetType();
        catPetType.setName("Cat");
        petTypeService.save(catPetType);

        getLogger().info("Created and stored PetTypes using {} ...", petTypeService.getClass().getSimpleName());

        final Owner owner1 = new Owner();
        owner1.setFirstName("Michael");
        owner1.setLastName("Weston");
        ownerService.save(owner1);

        final Owner owner2 = new Owner();
        owner2.setFirstName("Fiona");
        owner2.setLastName("Glenanne");
        ownerService.save(owner2);

        getLogger().info("Created and stored Owners using {} ...", ownerService.getClass().getSimpleName());

        final Vet vet1 = new Vet();
        vet1.setFirstName("Sam");
        vet1.setLastName("Axe");
        vetService.save(vet1);

        final Vet vet2 = new Vet();
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