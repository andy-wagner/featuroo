package uk.co.creativefootprint.sixpack4j.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/experiments")
public class ExperimentsController {

    @RequestMapping(method = RequestMethod.GET)
    String getExperiments() {
        return "blah";
    }

}
