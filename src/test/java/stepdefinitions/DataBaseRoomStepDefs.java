package stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import pojos.RoomPojo;

import java.sql.*;

import static org.junit.Assert.assertEquals;
import static stepdefinitions.MedunnaRoomStepDefs.odaNo;

public class DataBaseRoomStepDefs {
    Statement statement;

    @Given("Database baglantisi kur")
    public void databaseBaglantisiKur() throws SQLException {
        //1.Adim: Connection olustur
        Connection connection = DriverManager.getConnection("jdbc:postgresql://medunna.com:5432/medunna_db_v2", "select_user", "Medunna_pass_@6");

        //2.Adim: Statement olustur
        statement = connection.createStatement();
    }

    @Then("Oda bilgilerini dogrula")
    public void odaBilgileriniDogrula() throws SQLException {
        //resultSet.next() ile database in baslik bolumundeki (metadata) ok bir alt satira iniyor.
        // Bu nedenle sorgudan sonra mutlaka next() kullanmamiz gerekir yoksa Result set baslik bolumunde kalir

        //3.Adim: Query (Sorgu) olustur
        ResultSet resultSet = statement.executeQuery("select * from room where room_number = "+ odaNo);
        resultSet.next();


        RoomPojo expectedData = new RoomPojo(odaNo,"SUITE",true,123,"End To End Test için oluşturulmuştur");
        assertEquals(expectedData.getRoomNumber(), resultSet.getInt("room_number"));
        assertEquals(expectedData.getRoomType(), resultSet.getString("room_type"));
        assertEquals(expectedData.isStatus(), resultSet.getBoolean("status"));
        assertEquals(expectedData.getPrice(), resultSet.getInt("price"));
        assertEquals(expectedData.getDescription(), resultSet.getString("description"));


        //Room Number kullanarak ID noyu aldik
    //     int roomId = resultSet.getInt("id");
    //    System.out.println("roomId = " + roomId);
    }
}
