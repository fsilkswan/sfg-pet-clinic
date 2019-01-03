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
import guru.springframework.sfgpetclinic.model.VetSpecialty;
import guru.springframework.sfgpetclinic.model.Visit;
import guru.springframework.sfgpetclinic.services.OwnerService;
import guru.springframework.sfgpetclinic.services.PetTypeService;
import guru.springframework.sfgpetclinic.services.VetService;
import guru.springframework.sfgpetclinic.services.VetSpecialtyService;
import guru.springframework.sfgpetclinic.services.VisitService;

@Component
public class DataLoader
    implements CommandLineRunner
{
    private final Logger              logger = LoggerFactory.getLogger(getClass());
    private final OwnerService        ownerService;
    private final PetTypeService      petTypeService;
    private final VetService          vetService;
    private final VetSpecialtyService vetSpecialtyService;
    private final VisitService        visitService;

    // @Autowired
    public DataLoader(final OwnerService ownerService, final VetService vetService, final PetTypeService petTypeService, final VetSpecialtyService vetSpecialtyService,
                      /**/final VisitService visitService)
    {
        this.ownerService = ownerService;
        this.vetService = vetService;
        this.petTypeService = petTypeService;
        this.vetSpecialtyService = vetSpecialtyService;
        this.visitService = visitService;
    }

    @Override
    public void run(final String... args)
        throws Exception
    {
        final int count = petTypeService.findAll().size();
        if( count == 0 )
        {
            createData();
        }
    }

    private void createData()
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

        final Visit catVisit = new Visit();
        catVisit.setPet(fionasPet);
        catVisit.setDate(LocalDate.now());
        catVisit.setDescription("Sneezy Kitty");

        visitService.save(catVisit);

        getLogger().info("Created and stored Owners, their Pets and corresponding Visits using.");

        final VetSpecialty radiology = new VetSpecialty();
        radiology.setDescription("Radiology");
        final VetSpecialty savedRadiology = vetSpecialtyService.save(radiology);

        final VetSpecialty surgery = new VetSpecialty();
        surgery.setDescription("Surgery");
        final VetSpecialty savedSurgery = vetSpecialtyService.save(surgery);

        final VetSpecialty dentistry = new VetSpecialty();
        dentistry.setDescription("Dentistry");
        final VetSpecialty savedDentistry = vetSpecialtyService.save(dentistry);

        final Vet vet1 = new Vet();
        vet1.setFirstName("Sam");
        vet1.setLastName("Axe");
        vet1.getSpecialties().add(savedRadiology);
        vetService.save(vet1);

        final Vet vet2 = new Vet();
        vet2.setFirstName("Jessie");
        vet2.setLastName("Porter");
        vet2.getSpecialties().add(savedSurgery);
        vetService.save(vet2);

        getLogger().info("Created and stored Vets and their Specialties.");
    }

    private Logger getLogger()
    {
        return logger;
    }
}