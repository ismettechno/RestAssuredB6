import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

public class _01_ApiTest {

    @Test
    public void Test1()
    {
        // 1- Endpoint i çağırmadna önce hazırlıkların yapıldığı bölüm : Request, gidecek body
        //                                                           token
        // 2- Endpoint in çağrıldığı bölüm  : Endpoint in çağrılması(METOD: GET,POST ..)
        // 3- Endpoint çağrıldıktan sonraki bölüm : Response, Test(Assert), data

        given().
                //1.blümle ilgili işler : giden body,token
                when().
                //2.blümle ilgili işler : metod , endpoint
                then()
                //3.bölümle ilgili işler: gelen data, assert,test
        ;
    }


}
