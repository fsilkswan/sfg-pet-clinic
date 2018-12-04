package guru.springframework.sfgpetclinic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SfgPetClinicApplication
{
    public static final String SERVICE_LAYER_STRATEGY_DATABASE = "database_services";
    public static final String SERVICE_LAYER_STRATEGY_MAP      = "map_services";

    private static final String DEFAULT = "default";

    public static void main(final String[] args)
    {
        SpringApplication.run(SfgPetClinicApplication.class, args);
    }

    // @Configuration
    // @Profile({ DEFAULT, SERVICE_LAYER_STRATEGY_MAP })
    // protected static class MapServiceLayerConfiguration
    // {
    // @Bean
    // @Primary
    // public OwnerService ownerMapService()
    // {
    // return new OwnerMapService();
    // }
    //
    // @Bean
    // @Primary
    // public PetService petMapService()
    // {
    // return new PetMapService();
    // }
    //
    // @Bean
    // @Primary
    // public VetService vetMapService()
    // {
    // return new VetMapService();
    // }
    // }
}