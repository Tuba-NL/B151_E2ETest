package stepdefinitions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import pojos.RoomPojo;

import java.util.List;

import static base_url.MedunnaBaseUrl.spec;
import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static stepdefinitions.MedunnaRoomStepDefs.odaNo;


public class ApiRoomStepDefs {
    Response response;
    RoomPojo expectedData;

    @Given("GET Request gonderilir")
    public void getRequestGonderilir() {
        //Oda ID`sini alma -->https://medunna.com/api/rooms?sort=createdDate,desc
        spec.pathParams("first","api","second","rooms")
                .queryParam("sort","createdDate,desc");

        response = given(spec).when().get("{first}/{second}");
        List<Object> roomId = response.jsonPath().getList("findAll{it.roomNumber=="+odaNo+"}.id");
        System.out.println("roomId = " + roomId);

        /*
        Hoca boyle yapti
        // Oda ID'sini alma --> https://medunna.com/api/rooms?sort=createdDate%2Cdesc
        spec.pathParams("first", "api", "second", "rooms")
        .queryParam("sort", "createdDate,desc");
        Response response1 = given(spec).when().get("{first}/{second}");
        Object roomId = response1.jsonPath().getList("findAll{it.roomNumber==" + odaNo + "}.id").get(0);
        System.out.println("Room ID: " + roomId);
         */


        // Set the Url-->https://medunna.com/api/rooms/72086
        spec.pathParams("first","api","second","rooms","third",roomId.get(0));

        //Set the expected data
        expectedData = new RoomPojo(odaNo,"SUITE",true,123.0,"End To End Test için oluşturulmuştur");


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
