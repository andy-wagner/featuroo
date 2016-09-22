package uk.co.creativefootprint.featuroo.web;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.co.creativefootprint.featuroo.application.FeaturooApplication;
import uk.co.creativefootprint.featuroo.view.ClientView;
import uk.co.creativefootprint.featuroo.view.ExperimentView;

import java.util.Arrays;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsIn.isIn;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = FeaturooApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
public class ExperimentsControllerTest {

    //@Autowired
    //ExperimentService experimentService;

    @Value("${local.server.port}")
    int port;

    @Before
    public void setUp() {
        RestAssured.port = port;
        SqlTestHelper.resetDb();
    }

    @Test
    public void canCreateExperiment() {

        ExperimentView view = new ExperimentView()
                .withName("experiment1")
                .withAlternatives(Arrays.asList("a", "b"))
                .withFrafficFraction(1d);

        given()
                .contentType(ContentType.JSON)
                .body(view).
        when()
                .post("/experiments").
        then().
                statusCode(HttpStatus.SC_CREATED).
                body("name", is("experiment1"));
    }

    @Test
    public void cantParticipateInNonExistentExperiment() {

        ExperimentView experimentView = new ExperimentView()
                .withName("experiment1")
                .withAlternatives(Arrays.asList("a", "b"))
                .withFrafficFraction(1d);

        ClientView clientView = new ClientView()
                .withClientId("client1");

        //create experiment
        given()
                .contentType(ContentType.JSON)
                .body(experimentView).
        when()
                .post("/experiments");

        //participate in it
        given()
                .contentType(ContentType.JSON)
                .body(clientView).
        when()
                .post("/experiments/participate/experiment1").
        then().
                statusCode(HttpStatus.SC_OK).
                body("status", is("ok")).
                body("alternative.name", isIn(Arrays.asList("a","b"))).
                body("experiment.name", is("experiment1")).
                body("client_id", is("client1"));
    }

    @Test
    public void cantParticipateInNotExistingExperiment() {

        ClientView view = new ClientView()
                .withClientId("client1");

        given()
                .contentType(ContentType.JSON)
                .body(view).
        when()
                .post("/experiments/participate/doesntexist").
        then().
                statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    public void canCreateViaParticipate() {

        given()
                .queryParam("experiment","experiment1")
                .queryParam("alternatives","a")
                .queryParam("alternatives","b")
                .queryParam("client_id","myclient").
        when()
                .get("/experiments/participate").
        then().
                statusCode(HttpStatus.SC_OK).
                body("status", is("ok")).
                body("alternative.name", isIn(Arrays.asList("a","b"))).
                body("experiment.name", is("experiment1")).
                body("client_id", is("myclient"));
    }

    @Test
    public void canAcceptsTrafficFractionViaParticipate() {

        given()
                .queryParam("experiment","experiment1")
                .queryParam("alternatives","a")
                .queryParam("alternatives","b")
                .queryParam("client_id","myclient")
                .queryParam("traffic_fraction",0.5d).
        when()
                .get("/experiments/participate").
        then().
                statusCode(HttpStatus.SC_OK).
                body("status", is("ok")).
                body("alternative.name", isIn(Arrays.asList("a","b"))).
                body("experiment.name", is("experiment1")).
                body("client_id", is("myclient"));
    }


}