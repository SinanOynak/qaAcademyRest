package services;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class SampleTest3 {
    //Data Provider amacı birden fazla kombinasyonu tek method ile test etmemize yaramaktadır.

    @DataProvider(name = "dataProvider")
    public Object[][] dataProvider(){
            return new Object[][] {
                    {2,200},
                    {1,200}
            };
    }

    @Test(dataProvider = "dataProvider")
    public void petDetail(int pedId, int statusCode){
        given()
                .log().all().
                when()
                .get("https://petstore.swagger.io/v2/pet/" + pedId).
                then()
                .statusCode(statusCode)
                .log().all();
    }
}
