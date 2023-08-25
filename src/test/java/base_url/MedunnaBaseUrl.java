package base_url;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static utilities.AuthenticationMedunna.generateToken;

public class MedunnaBaseUrl {
    public static RequestSpecification spec;
    //Testi calistrdigimizda spec objesinin null gelmemesi icin setup() methodunun calistirilmasi gerekir.
    //Cucumber' da her testten once calistirmak istedigimiz methodlari Hooks class' i icesine ekleriz
    public  static void setUp(){
        spec =new RequestSpecBuilder().setBaseUri("https://medunna.com")
                .addHeader("Authorization","Bearer " + generateToken())
                .setContentType(ContentType.JSON)
                .build();
    }
}
