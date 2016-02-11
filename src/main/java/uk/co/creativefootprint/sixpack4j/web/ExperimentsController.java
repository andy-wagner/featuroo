package uk.co.creativefootprint.sixpack4j.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uk.co.creativefootprint.sixpack4j.model.*;
import uk.co.creativefootprint.sixpack4j.repository.ConversionRepository;
import uk.co.creativefootprint.sixpack4j.repository.ExperimentRepository;
import uk.co.creativefootprint.sixpack4j.repository.ParticipantRepository;
import uk.co.creativefootprint.sixpack4j.service.ExperimentService;
import uk.co.creativefootprint.sixpack4j.view.*;

import java.util.Arrays;
import java.util.Date;

@RestController
@RequestMapping("/experiments")
public class ExperimentsController {

    ExperimentService experimentService;

    private static String DB_DRIVER="org.h2.Driver";
    private static String DB_CONNECTION="jdbc:h2:~/sixpack-test";
    private static String DB_USER="";
    private static String DB_PASSWORD="";

    public ExperimentsController(){
        experimentService = new ExperimentService(
                new ExperimentRepository(DB_DRIVER,DB_CONNECTION,DB_USER,DB_PASSWORD),
                new ParticipantRepository(DB_DRIVER,DB_CONNECTION,DB_USER,DB_PASSWORD),
                new ConversionRepository(DB_DRIVER,DB_CONNECTION,DB_USER,DB_PASSWORD));
    }

    @RequestMapping(method = RequestMethod.GET, path="participate")
    public ResponseEntity<ParticipationResultView> participate(@RequestParam("experiment") String experiment,
                                                               @RequestParam("alternatives") String[] alternatives,
                                                               @RequestParam("client_id") String clientId,
                                                               @RequestParam("traffic_fraction") double trafficFraction) {

        Experiment existing = experimentService.getExperiment(experiment);
        if(existing == null)
            experimentService.createExperiment(experiment,
                                               experiment,
                                               Arrays.asList(alternatives),
                                               trafficFraction,
                                               new UniformChoiceStrategy());

        ParticipationResult result = experimentService.participate(experiment, new Client(clientId),new Date());

        ParticipationResultView view =  new ParticipationResultView(
                new AlternativeView(result.getAlternative().getName()),
                new ExperimentView(experiment),
                clientId,
                "ok"
        );

        return new ResponseEntity<ParticipationResultView>(view, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, path="convert")
    public ResponseEntity<ConversionResultView> convert(@RequestParam("experiment") String experiment,
                                                            @RequestParam("client_id") String clientId,
                                                            @RequestParam("kpi") String kpi) {

        Alternative alternative = experimentService.convert(experiment, new Client(clientId),new Date());

        ConversionResultView view =  new ConversionResultView(
                new AlternativeView(alternative.getName()),
                new ExperimentView(experiment),
                new ConversionView(null, kpi),
                clientId
        );

        return new ResponseEntity<ConversionResultView>(view, HttpStatus.OK);
    }

}
