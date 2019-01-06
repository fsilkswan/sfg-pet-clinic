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
        final PetType dogPetType = PetType.builder().name("Dog").build();
        final PetType savedDogPetType = petTypeService.save(dogPetType);

        final PetType catPetType = PetType.builder().name("Cat").build();
        final PetType savedCatPetType = petTypeService.save(catPetType);

        getLogger().info("Created and stored PetTypes using {} ...", petTypeService.getClass().getSimpleName());

        final Owner owner1 = Owner.builder()
                                  .firstName("Michael")
                                  .lastName("Weston")
                                  .address("123 Brickerel")
                                  .city("Miami")
                                  .telephone("123123123")
                                  .build();

        final Pet mikesPet = Pet.builder()
                                .petType(savedDogPetType)
                                .birthDate(LocalDate.now())
                                .name("Rosco")
                                .owner(owner1)
                                .build();

        owner1.getPets().add(mikesPet);
        ownerService.save(owner1);

        final Owner owner2 = Owner.builder()
                                  .firstName("Fiona")
                                  .lastName("Glenanne")
                                  .address("123 Brickerel")
                                  .city("Miami")
                                  .telephone("123123123")
                                  .build();

        final Pet fionasPet = Pet.builder()
                                 .petType(savedCatPetType)
                                 .birthDate(LocalDate.now())
                                 .name("Just Cat")
                                 .owner(owner2)
                                 .build();

        owner2.getPets().add(fionasPet);
        ownerService.save(owner2);

        final Visit catVisit = Visit.builder()
                                    .pet(fionasPet)
                                    .date(LocalDate.now())
                                    .description("Sneezy Kitty")
                                    .build();
        visitService.save(catVisit);

        getLogger().info("Created and stored Owners, their Pets and corresponding Visits using.");

        final VetSpecialty radiology = VetSpecialty.builder().description("Radiology").build();
        final VetSpecialty savedRadiology = vetSpecialtyService.save(radiology);

        final VetSpecialty surgery = VetSpecialty.builder().description("Surgery").build();
        final VetSpecialty savedSurgery = vetSpecialtyService.save(surgery);

        final VetSpecialty dentistry = VetSpecialty.builder().description("Dentistry").build();
        /* final VetSpecialty savedDentistry = */vetSpecialtyService.save(dentistry);

        final Vet vet1 = Vet.builder()
                            .firstName("Sam")
                            .lastName("Axe")
                            .build();

        vet1.getSpecialties().add(savedRadiology);
        vetService.save(vet1);

        final Vet vet2 = Vet.builder()
                            .firstName("Jessie")
                            .lastName("Porter")
                            .build();

        vet2.getSpecialties().add(savedSurgery);
        vetService.save(vet2);

        getLogger().info("Created and stored Vets and their Specialties.");
    }

    private Logger getLogger()
    {
        return logger;
    }
}