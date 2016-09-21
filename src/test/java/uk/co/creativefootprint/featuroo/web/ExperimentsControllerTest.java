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
import uk.co.creativefootprint.featuroo.view.ExperimentView;

import java.util.Arrays;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

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
                .withFrafficFraction(100d);

        given()
                .contentType(ContentType.JSON)
                .body(view).
        when()
                .post("/experiments").
        then().
                statusCode(HttpStatus.SC_CREATED).
                body("name", is("experiment1"));
    }
}