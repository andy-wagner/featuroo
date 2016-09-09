package uk.co.creativefootprint.featuroo.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RestController;
import uk.co.creativefootprint.featuroo.repository.ConversionRepository;
import uk.co.creativefootprint.featuroo.repository.ExperimentRepository;
import uk.co.creativefootprint.featuroo.repository.ParticipantRepository;
import uk.co.creativefootprint.featuroo.service.ExperimentService;

@RestController
@ComponentScan(basePackages = "uk.co.creativefootprint")
@EnableAutoConfiguration
public class FeaturooApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(FeaturooApplication.class, args);
    }

    @Bean
    public ExperimentService getExperimentService(ExperimentRepository experimentRepository,
                                                  ParticipantRepository participantRepository,
                                                  ConversionRepository conversionRepository,
                                                  AppConfig appConfig){
        return new ExperimentService(experimentRepository,participantRepository,
                                     conversionRepository, appConfig.getDefaultTrafficFraction());
    }

    @Bean ExperimentRepository getExperimentRepository(DbConfig dbConfig){
        return new ExperimentRepository(dbConfig.getDriver(), dbConfig.getConnection(),
                                        dbConfig.getUser(), dbConfig.getPassword());
    }

    @Bean ParticipantRepository getParticipantRepository(DbConfig dbConfig){
        return new ParticipantRepository(dbConfig.getDriver(), dbConfig.getConnection(),
                                         dbConfig.getUser(), dbConfig.getPassword());
    }

    @Bean ConversionRepository getConvertionRepository(DbConfig dbConfig){
        return new ConversionRepository(dbConfig.getDriver(), dbConfig.getConnection(),
                                        dbConfig.getUser(), dbConfig.getPassword());
    }
}
