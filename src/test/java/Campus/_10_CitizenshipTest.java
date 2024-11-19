package Campus;

import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class _10_CitizenshipTest extends CampusParent{

    String citizenshipID="";
    String citizenshipName="";

    @Test
    public void createCitizenship(){
        citizenshipName= randomUreteci.nation().nationality()+ randomUreteci.number().digits(5);

        Map<String,String> citizenship = new HashMap<>();
        citizenship.put("name",citizenshipName);
        citizenship.put("shortName", "kisaAdi");

        citizenshipID=
        given()
                .spec(reqSpec)
                .body(citizenship)

                .when()
                .post("/school-service/api/citizenships")

                .then()
                .statusCode(201)
                .log().body()
                .extract().path("id")
        ;

        System.out.println("citizenshipID = " + citizenshipID);
    }


    @Test(dependsOnMethods = "createCitizenship")
    public void createCitizenshipNegative(){
        Map<String,String> citizenship = new HashMap<>();
        citizenship.put("name",citizenshipName);
        citizenship.put("shortName", "kisaAdi");

                given()
                        .spec(reqSpec)
                        .body(citizenship)

                        .when()
                        .post("/school-service/api/citizenships")

                        .then()
                        .statusCode(400)
                        .log().body()
                        .body("message", containsStringIgnoringCase("already"))
        ;
    }

    @Test(dependsOnMethods = "createCitizenshipNegative")
    public void updateCitizenship(){

        citizenshipName = "İsmet"+randomUreteci.number().digits(5);

        Map<String,String> updCitizenship = new HashMap<>();
        updCitizenship.put("id",citizenshipID);
        updCitizenship.put("name",citizenshipName);
        updCitizenship.put("shortName", "kisaAdi");

        given()
                .spec(reqSpec)
                .body(updCitizenship)

                .when()
                .put("/school-service/api/citizenships")

                .then()
                .statusCode(200)
                .log().body()
                .body("name", equalTo(citizenshipName))
        ;
    }

    @Test(dependsOnMethods = "updateCitizenship")
    public void deleteCitizenship(){

        given()
                .spec(reqSpec)

                .when()
                .delete("/school-service/api/citizenships/"+citizenshipID)

                .then()
                .statusCode(200)
                .log().body()
        ;
    }

    @Test(dependsOnMethods = "deleteCitizenship")
    public void deleteCitizenshipNegative(){

        given()
                .spec(reqSpec)

                .when()
                .delete("/school-service/api/citizenships/"+citizenshipID)

                .then()
                .statusCode(400)
                .log().body()
                .body("message",equalTo("Citizenship not found"));
        ;
    }

}
