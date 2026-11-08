package com.be.testcases;

import com.be.payloads.Payload;
import com.be.pojo.Product;
import com.be.routes.Routes;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class ProductDataDrivenTest extends BaseClass{

    @Test(dataProvider = "jsonDataProvider", dataProviderClass = com.be.utils.DataProviders.class)
    public void testAddNewProduct(Map<String, String> data) {

        String title = data.get("title");
        double price = Double.parseDouble(data.get("price"));
        String category = data.get("category");
        String description = data.get("description");
        String image = data.get("image");

        Product newProduct = new Product(title, price, description, image, category);

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

        given()
                .pathParam("id", productID)

                .when()
                .delete(Routes.DELETE_PRODUCT)

                .then()
                .statusCode(200);
    }


}
