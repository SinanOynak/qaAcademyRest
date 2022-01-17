package services;

import io.restassured.http.ContentType;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;

public class SampleTest2 {

    //BeforeClass-After Class kullanımı her testden önce ve sonra çalıştırılması gereken testler
    //Before method ve After method her method dan önce ve sonra kullanılır.
    @BeforeClass
    public void postCreatepet(){
    String postData = "{\n" +
            "  \"id\": 1,\n" +
            "  \"category\": {\n" +
            "    \"id\": 1,\n" +
            "    \"name\": \"test\"\n" +
            "  },\n" +
            "  \"name\": \"doggie\",\n" +
            "  \"photoUrls\": [\n" +
            "    \"test\"\n" +
            "  ],\n" +
            "  \"tags\": [\n" +
            "    {\n" +
            "      \"id\": 1,\n" +
            "      \"name\": \"test\"\n" +
            "    }\n" +
            "  ],\n" +
            "  \"status\": \"available\"\n" +
            "}";
    given()
                .body(postData)
                .contentType(ContentType.JSON)
                .log().all().
    when()
                .post("https://petstore.swagger.io/v2/pet").
    then()
                .statusCode(200)
                .log().all();
    }

    @Test
    public void petDetail(){
        int pedId = 1;
        given().log().all().
                when()
                .get("https://petstore.swagger.io/v2/pet/" + pedId).
                then()
                .statusCode(200)
                .log().all();
    }

    @AfterTest
    public void deletePet(){
        given()
                .log().all().header("api_key","special-key").
                when()
                .delete("https://petstore.swagger.io/v2/pet/1").
                then()
                .statusCode(200)
                .log().all();
    }
}
