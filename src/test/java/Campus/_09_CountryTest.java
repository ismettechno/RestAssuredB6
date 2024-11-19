package Campus;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;


public class _09_CountryTest {

    Faker randomUreteci = new Faker();
    RequestSpecification reqSpec;
    String ulkeAdi = "";
    String ulkeKodu = "";

    @BeforeClass
    public void Setup() {
        baseURI = "https://test.mersys.io";

        Map<String, String> userCredential = new HashMap<>();
        userCredential.put("username", "turkeyts");
        userCredential.put("password", "TechnoStudy123");
        userCredential.put("rememberMe", "true");

        Cookies cookies =
                given()
                        .contentType(ContentType.JSON)
                        .body(userCredential)

                        .when()
                        .post("/auth/login")

                        .then()
                        .log().all()
                        .statusCode(200)
                        .extract().response().detailedCookies();
        ;
        System.out.println("cookies = " + cookies);

        reqSpec = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .addCookies(cookies)
                .build();
    }

    @Test
    public void CreateCountry() {
        ulkeAdi = randomUreteci.address().country() + randomUreteci.number().digits(5);
        ulkeKodu = randomUreteci.address().countryCode() + randomUreteci.number().digits(5);

        Map<String, String> createCountry = new HashMap<>();
        createCountry.put("name", ulkeAdi);
        createCountry.put("code", ulkeKodu);

        given()
                .spec(reqSpec)
                .body(createCountry)

                .when()
                .post("school-service/api/countries")

                .then()
                .log().body()
                .statusCode(201)
        ;
    }

    @Test(dependsOnMethods = "CreateCountry")
    public void CreateCountryNegative() {
        //aynı ülke adı ve kodu gönderildiğinde kaydetmemesi lazım
        // already kelimesi body de var mı ?
        Map<String, String> createCountry = new HashMap<>();
        createCountry.put("name", ulkeAdi);
        createCountry.put("code", ulkeKodu);

        given()
                .spec(reqSpec)
                .body(createCountry)

                .when()
                .post("school-service/api/countries")

                .then()
                .log().body()
                .statusCode(400)
                .body("message", containsStringIgnoringCase("already"))
        ;

    }


    @Test
    public void updateCountry()  {




    }

}