package GoRest;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

// GoRest Posts kaynağındaki API leri test ediniz.
// create,getId,update, delete, deleteNegative
public class _08_GoRestPostsTest {

        //randomUreteci.lorem().sentence()
        //randomUreteci.lorem().paragraph()

    Faker randomUreteci=new Faker();
    RequestSpecification reqSpec;
    int postID=0;

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
    public void CreatePost()
    {
        String rndTitle= randomUreteci.lorem().sentence();
        String rndParagraph= randomUreteci.lorem().paragraph();

        Map<String,String> newUser=new HashMap<>(); // postman deki body kısmı map olarak hazırlandı
        newUser.put("user_id","7530342");
        newUser.put("title",rndTitle);
        newUser.put("body",rndParagraph);

        postID=
                given()
                        .spec(reqSpec)
                        .body(newUser)

                        .when()
                        .post("posts")  // http ile başlamıyorsa baseURI başına geliyor

                        .then()
                        .log().body()
                        .statusCode(201)
                        .extract().path("id")
        ;

        System.out.println("postID = " + postID);
    }

    // GetPostById testini yapınız
    @Test(dependsOnMethods = "CreatePost")
    public void GetPostById() {

        given()
                .spec(reqSpec)
                .log().uri()

                .when()
                .get("posts/"+postID)

                .then()
                .log().body()
                .statusCode(200)
                .body("id",equalTo(postID))
        ;
    }

    // UpdatePost testini yapınız
    @Test(dependsOnMethods = "GetPostById")   // bu aşamadan sonra class çalıştırılmalı
    public void UpdatePost()
    {
        String updTitle="İsmet Temur Post Test";

        Map<String,String> updPost=new HashMap<>();
        updPost.put("title",updTitle);

        given()
                .spec(reqSpec)
                .body(updPost)

                .when()
                .put("posts/"+postID)

                .then()
                .log().body()
                .statusCode(200)
                .body("id", equalTo(postID))
                .body("title", equalTo(updTitle))
        ;
    }

    // DeletePost testini yapınız
    @Test(dependsOnMethods = "UpdatePost")
    public void DeletePost()
    {
        given()
                .spec(reqSpec)

                .when()
                .delete("posts/"+postID)

                .then()
                .statusCode(204)
        ;
    }

    @Test(dependsOnMethods = "DeletePost")
    public void DeletePostNegative()
    {
        given()
                .spec(reqSpec)

                .when()
                .delete("posts/"+postID)

                .then()
                .statusCode(404)
        ;
    }


}
