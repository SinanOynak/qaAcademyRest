package services;

import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import models.OrderRequest;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class SampleTest {

    @Test
    public void sample(){
        Response response = RestAssured.get("https://petstore.swagger.io/v2/store/inventory");

        System.out.println("getBody: "+response.asString());
        System.out.println("getbody: "+response.getBody().asString());
        System.out.println("getStatusCode: "+response.getStatusCode());
        System.out.println("getContent-Type: "+response.getHeader("Content-Type"));
        System.out.println("getHeaders: "+response.getHeaders());
        System.out.println("getTime: "+response.getTime());

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test
    public void getInventory(){
        given().log().all().
                when()
                .get("https://petstore.swagger.io/v2/store/inventory").
                then()
                .statusCode(200)
                .time(lessThan(2000L))
                .log().all();
    }

    @Test
    public void getPetDetail(){
        int pedId = 3;
        given().log().all().
                when()
                .get("https://petstore.swagger.io/v2/pet/" + pedId).
                then()
                .statusCode(200)
                .body("id",equalTo(pedId))
                .body("status",startsWith("Res"))
                .body("category.id",equalTo(1))
                .body("tags[0].id",equalTo(0)).log().all();
    }

    //post isteği
    @Test
    public void postCreatePet(){
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

    //model oluşturarak
    @Test
    public void postOrder(){
        OrderRequest orderRequest = new OrderRequest(1,1,1,"2022-01-16T11:49:46.958+0000","placed",true);
        String request = new Gson().toJson(orderRequest);

        given()
                .log().all().header("Content-Type","application/json")
                .body(request).
                when()
                .post("https://petstore.swagger.io/v2/store/order").
                then()
                .statusCode(200)
                .log().all();
    }

    //query params kullanma
    @Test
    public void postUpdatePet(){
        int pedId = 3;
        Map<String,Object> queryParams = new HashMap<>();
        queryParams.put("name","doggiee");
        queryParams.put("status","available");

        given()
                .log().all()
                .queryParams(queryParams).
                when()
                .post("https://petstore.swagger.io/v2/pet/" + pedId).
                then()
                .statusCode(200)
                .log().all();
    }

}
