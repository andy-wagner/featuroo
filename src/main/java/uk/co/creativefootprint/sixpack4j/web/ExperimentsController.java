package uk.co.creativefootprint.sixpack4j.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.co.creativefootprint.sixpack4j.exception.ExperimentNotFoundException;
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

    @RequestMapping(method = RequestMethod.POST, path="")
    public ResponseEntity<String> create(
            @RequestParam("experiment") String experiment,
            @RequestParam("alternatives") String[] alternatives,
            @RequestParam("client_id") String clientId,
            @RequestParam(name = "traffic_fraction", required = false) double trafficFraction) {

        Experiment e = experimentService.createExperiment(experiment,
                experiment,
                Arrays.asList(alternatives),
                trafficFraction,
                new UniformChoiceStrategy());

        return new ResponseEntity<String>(e.getName(), HttpStatus.CREATED);
    }


    /*
     Here for backwards compatibility with the sixpack api. Consider posting to the
     create endpoint first and then participating.
     */
    @RequestMapping(method = RequestMethod.GET, path="participate")
    public ResponseEntity<ParticipationResultView> participateSixPack(
            @RequestParam("experiment") String experiment,
            @RequestParam("alternatives") String[] alternatives,
            @RequestParam("client_id") String clientId,
            @RequestParam(name = "traffic_fraction", required = false) double trafficFraction) {

        Experiment existing = experimentService.getExperiment(experiment);

        if (existing == null)
            experimentService.createExperiment(experiment,
                    experiment,
                    Arrays.asList(alternatives),
                    trafficFraction,
                    new UniformChoiceStrategy());

        ParticipationResult result = experimentService.participate(experiment, new Client(clientId), new Date());

        ParticipationResultView view =  new ParticipationResultView(
                new AlternativeView(result.getAlternative().getName()),
                new ExperimentView(experiment),
                clientId,
                "ok"
        );

        return new ResponseEntity<ParticipationResultView>(view, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, path="participate/{experiment}")
    public ResponseEntity<ParticipationResultView> participate(
            @PathVariable("experiment") String experiment,
            @RequestParam("client_id") String clientId) {

        Experiment existing = experimentService.getExperiment(experiment);
        if(existing == null)
            throw new ExperimentNotFoundException(experiment);

        ParticipationResult result = experimentService.participate(experiment, new Client(clientId),new Date());

        ParticipationResultView view =  new ParticipationResultView(
                new AlternativeView(result.getAlternative().getName()),
                new ExperimentView(experiment),
                clientId,
                "ok"
        );

        return new ResponseEntity<ParticipationResultView>(view, HttpStatus.OK);
    }


    /*
     Here for backwards compatibility with the sixpack api. Consider using the similar POST
     /convert endpoint
     */
    @RequestMapping(method = RequestMethod.GET, path="convert/{experiment}")
    public ResponseEntity<ConversionResultView> convertSixPack(
            @PathVariable("experiment") String experiment,
            @RequestParam("client_id") String clientId,
            @RequestParam(name = "kpi", required = false) String kpi) {
        return convert(experiment, clientId, kpi, kpi);
    }

    @RequestMapping(method = RequestMethod.POST, path="convert/{experiment}")
    public ResponseEntity<ConversionResultView> convert(
            @PathVariable("experiment") String experiment,
            @RequestParam("client_id") String clientId,
            @RequestParam(name = "kpi", required = false) String kpi,
            @RequestParam(name = "goal", required = false) String goal) {

        goal = goal != null ? goal : kpi;

        Alternative alternative = experimentService.convert(experiment, new Client(clientId), new Date(), new Goal(goal));

        ConversionResultView view =  new ConversionResultView(
                new AlternativeView(alternative.getName()),
                new ExperimentView(experiment),
                new ConversionView(null, goal),
                clientId
        );

        return new ResponseEntity<ConversionResultView>(view, HttpStatus.OK);
    }

}
