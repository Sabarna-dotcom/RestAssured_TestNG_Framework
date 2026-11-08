package com.be.payloads;

import com.be.pojo.Product;
import com.github.javafaker.Faker;

import java.util.Random;

public class Payload {

    private static final Faker faker = new Faker();

    private static final String categories[] = {
            "electronics", "furniture", "clothing", "books", "beauty"
    };

    private static Random random = new Random();

    //Product Payload
    public static Product productPayload()
    {
        String name = faker.commerce().productName();
        double price = Double.parseDouble(faker.commerce().price());
        String description = faker.lorem().sentence();
        String imageUrl = "https://i.pravatar.cc/100";
        String category = categories[random.nextInt(categories.length)];

        return new Product(description, price, name, imageUrl, category);
    }
}
