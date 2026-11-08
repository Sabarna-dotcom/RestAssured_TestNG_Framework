package com.be.testcases;

import com.be.payloads.Payload;
import com.be.pojo.Product;
import com.be.routes.Routes;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Properties;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ProductsTest extends BaseClass{

    @Test
    public void testGetAllProducts() {

        Response response = given()
                .when()
                .get(Routes.GET_ALL_PRODUCTS);

        response.then()
                .statusCode(200)
                .body("size()", greaterThan(0));

    }

    @Test
    public void testGetProductByID() {

        int productID = configReader.getIntProperty("productId");

        Response response = given().pathParam("id",productID)
                            .when()
                            .get(Routes.GET_PRODUCT_BY_ID);

        response
                .then()
                .statusCode(200)
                .log().body();

    }

    @Test
    public void testGetLimitedProducts() {

//        Response response = given().queryParam("limit",5)
//                .when()
//                .get(Routes.GET_PRODUCTS_WITH_LIMIT);

        Response response = given().pathParam("limit",5)
                .when()
                .get(Routes.GET_PRODUCTS_WITH_LIMIT);

        response
                .then()
                .statusCode(200)
                .log().body()
                .body("size()",equalTo(5));

    }

    @Test
    public  void testGetDescendingSortedProducts() {
        Response response = given()
                .when().pathParam("order","desc")
                    .get(Routes.GET_PRODUCTS_SORTED)
                .then()
                    .statusCode(200)
                    .extract().response();

        List<Integer> products = response.jsonPath().getList("id",Integer.class);

        Assert.assertTrue(isSortedDescending(products));
    }



    @Test
    public  void testGetAscendingSortedProducts() {
        Response response = given()
                .when().pathParam("order","asce")
                .get(Routes.GET_PRODUCTS_SORTED)
                .then()
                .statusCode(200)
                .extract().response();

        List<Integer> products = response.jsonPath().getList("id",Integer.class);

        Assert.assertTrue(isSortedAscending(products));
    }

    @Test
    public void testGetAllCategories() {
        given().when()
                .get(Routes.GET_ALL_CATEGORIES)
                .then()
                .statusCode(200)
                .body("size()",greaterThan(0));
    }

    @Test
    public void testGetProductsByCategory() {
        given()
                .when()
                    .pathParam("category","electronics")
                    .get(Routes.GET_PRODUCTS_BY_CATEGORY)
                .then()
                    .statusCode(200)
                    .body("size()",greaterThan(0))
                    .body("category",everyItem(equalTo("electronics")));
    }

    @Test
    public void testAddNewProduct() {
        Product newProduct = Payload.productPayload();

        int productID = given()
                .contentType(ContentType.JSON)
                .body(newProduct)
                .when()
                    .post(Routes.CREATE_PRODUCT)
                .then()
                    .log().body()
                    .statusCode(201)
                    .body("id",notNullValue())
                    .body("title",equalTo(newProduct.getTitle()))
                    .extract().jsonPath().getInt("id");

        System.out.println(productID);
    }

    @Test
    public void testUpdateProduct() {
        int productId = configReader.getIntProperty("productId");

        Product updatedPayload = Payload.productPayload();

        given()
                .contentType(ContentType.JSON)
                .body(updatedPayload)
                .pathParam("id", productId)

                .when()
                .put(Routes.UPDATE_PRODUCT)

                .then()
                .log().body()
                .statusCode(200)
                .body("title", equalTo(updatedPayload.getTitle()));
    }

    @Test
    public void testDeleteProduct() {
        int productId = configReader.getIntProperty("productId");

        given()
                .pathParam("id", productId)

                .when()
                .delete(Routes.DELETE_PRODUCT)

                .then()
                .statusCode(200);
    }


}
