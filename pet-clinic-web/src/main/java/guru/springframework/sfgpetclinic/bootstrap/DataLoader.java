package guru.springframework.sfgpetclinic.bootstrap;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.model.Pet;
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
        final PetType savedDogPetType = petTypeService.save(dogPetType);

        final PetType catPetType = new PetType();
        catPetType.setName("Cat");
        final PetType savedCatPetType = petTypeService.save(catPetType);

        getLogger().info("Created and stored PetTypes using {} ...", petTypeService.getClass().getSimpleName());

        final Owner owner1 = new Owner();
        owner1.setFirstName("Michael");
        owner1.setLastName("Weston");
        owner1.setAddress("123 Brickerel");
        owner1.setCity("Miami");
        owner1.setTelephone("123123123");

        final Pet mikesPet = new Pet();
        mikesPet.setPetType(savedDogPetType);
        mikesPet.setBirthDate(LocalDate.now());
        mikesPet.setName("Rosco");
        mikesPet.setOwner(owner1);
        owner1.getPets().add(mikesPet);

        ownerService.save(owner1);

        final Owner owner2 = new Owner();
        owner2.setFirstName("Fiona");
        owner2.setLastName("Glenanne");
        owner2.setAddress("123 Brickerel");
        owner2.setCity("Miami");
        owner2.setTelephone("123123123");

        final Pet fionasPet = new Pet();
        fionasPet.setPetType(savedCatPetType);
        fionasPet.setBirthDate(LocalDate.now());
        fionasPet.setName("Just Cat");
        fionasPet.setOwner(owner2);
        owner2.getPets().add(fionasPet);

        ownerService.save(owner2);

        getLogger().info("Created and stored Owners and Pets using {} ...", ownerService.getClass().getSimpleName());

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