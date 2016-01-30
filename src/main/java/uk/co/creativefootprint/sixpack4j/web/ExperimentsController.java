package uk.co.creativefootprint.sixpack4j.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import uk.co.creativefootprint.sixpack4j.model.Experiment;

@RestController
@RequestMapping("/experiments")
public class ExperimentsController {

    @RequestMapping(method = RequestMethod.GET)
    Experiment getExperiments() {
        return new Experiment();
    }

}
