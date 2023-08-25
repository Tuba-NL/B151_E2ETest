package stepdefinitions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import pojos.RoomPojo;

import static base_url.MedunnaBaseUrl.spec;
import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;


public class ApiRoomStepDefs {
    Response response;
    RoomPojo expectedData;
    @Given("GET Request gonderilir")
    public void getRequestGonderilir() {
        // Set the Url-->https://medunna.com/api/rooms/72086
        spec.pathParams("first","api","second","rooms","third","72111");

        //Set the expected data
        expectedData = new RoomPojo(98765321,"TWIN",false,100.00,"T&H");


        //Send the request and get the response
        response = given(spec).when().get("{first}/{second}/{third}");

    }



    @Then("Body dogrulanir")
    public void bodyDogrulanir() throws JsonProcessingException {
        RoomPojo actualData = new ObjectMapper().readValue(response.asString(), RoomPojo.class);

        assertEquals(200, response.statusCode());
        assertEquals(expectedData.getRoomNumber(),actualData.getRoomNumber());
        assertEquals(expectedData.getRoomType(),actualData.getRoomType());
        assertEquals(expectedData.isStatus(),actualData.isStatus());
        assertEquals(expectedData.getPrice(),actualData.getPrice());
        assertEquals(expectedData.getDescription(),actualData.getDescription());

    }
}
