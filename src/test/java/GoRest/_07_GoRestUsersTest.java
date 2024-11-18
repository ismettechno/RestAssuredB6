package GoRest;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class _07_GoRestUsersTest {

    Faker randomUreteci=new Faker();
    RequestSpecification reqSpec;
    int userID=0;

    @BeforeClass
    public void Setup()
    {
        baseURI="https://gorest.co.in/public/v2/";

        reqSpec = new RequestSpecBuilder()
                .addHeader("Authorization","Bearer f92bf3f56439b631d9ed928b3540968e747c8e75309c876420fb349cbb420ed1") // token
                .setContentType(ContentType.JSON)
                .build();
    }

    @Test
    public void CreateUser()
    {
        String rndFullName= randomUreteci.name().fullName();
        String rndEMail= randomUreteci.internet().emailAddress();

        Map<String,String> newUser=new HashMap<>(); // postman deki body kısmı map olarak hazırlandı
        newUser.put("name",rndFullName);
        newUser.put("gender","Male");
        newUser.put("email",rndEMail);
        newUser.put("status","active");

        userID=
        given()
                .spec(reqSpec)
                .body(newUser)

                .when()
                .post("users")  // http ile başlamıyorsa baseURI başına geliyor

                .then()
                .log().body()
                .statusCode(201)
                .extract().path("id")
        ;

        System.out.println("userID = " + userID);
    }

    // GetUserById testini yapınız
    @Test(dependsOnMethods = "CreateUser")
    public void GetUserById() {

        given()
                .spec(reqSpec)
                .log().uri()

                .when()
                .get("users/"+userID)

                .then()
                .log().body()
                .statusCode(200)
                .body("id",equalTo(userID))
        ;
    }

    // UpdateUser testini yapınız
    @Test(dependsOnMethods = "GetUserById")   // bu aşamadan sonra class çalıştırılmalı
    public void UpdateUser()
    {
         String updName="İsmet Temur";

         Map<String,String> updUser=new HashMap<>();
         updUser.put("name",updName);

         given()
                 .spec(reqSpec)
                 .body(updUser)

                 .when()
                 .put("users/"+userID)

                 .then()
                 .log().body()
                 .statusCode(200)
                 .body("id", equalTo(userID))
                 .body("name", equalTo(updName))
         ;
    }


}
















