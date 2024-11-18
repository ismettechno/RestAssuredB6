package GoRest;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;

import java.util.HashMap;
import java.util.Map;

public class _07_GoRestUsersTest {

    Faker randomUreteci=new Faker();
    RequestSpecification reqSpec;


    @BeforeClass
    public void Setup()
    {
        baseURI="https://gorest.co.in/public/v2/";

        reqSpec = new RequestSpecBuilder()
                .addHeader("Authorization","Bearer f92bf3f56439b631d9ed928b3540968e747c8e75309c876420fb349cbb420ed1") // token
                .setContentType(ContentType.JSON)
                .build();
    }

    public void CreateUser()
    {
        //body mi hazırlayacağım
//        {
//            "name":"{{$randomFullName}}",
//                "gender":"Male",
//                "email":"{{$randomEmail}}",
//                "status":"active"
//        }
        String rndFullName= randomUreteci.name().fullName();
        String rndEMail= randomUreteci.internet().emailAddress();

        Map<String,String> newUser=new HashMap<>();
        newUser.put("name",rndFullName);
        newUser.put("gender","Male");
        newUser.put("email",rndEMail);
        newUser.put("status","active");

        given()
                .spec(reqSpec)
                .body(newUser)

                .when()
                .post("users")  // http ile başlamıyorsa baseURI başına geliyor

                .then()

        ;

    }

}
