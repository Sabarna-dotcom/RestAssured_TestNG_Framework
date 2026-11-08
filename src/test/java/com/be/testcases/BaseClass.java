package com.be.testcases;

import com.be.routes.Routes;
import com.be.utils.ConfigReader;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.testng.annotations.BeforeClass;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeTest;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.List;


public class BaseClass {

    ConfigReader configReader;
    RequestLoggingFilter requestLoggingFilter;
    ResponseLoggingFilter responseLoggingFilter;


    @BeforeTest
    public void setup() {
        configReader = new ConfigReader();
        RestAssured.baseURI = Routes.BASE_URL;
    }

    @BeforeClass
    public void setupLogging() {

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(".\\logs\\test_logging.log");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        PrintStream log = new PrintStream(fos, true);

        requestLoggingFilter = new RequestLoggingFilter(log);
        responseLoggingFilter = new ResponseLoggingFilter(log);

        RestAssured.filters(requestLoggingFilter, responseLoggingFilter);
    }

    public boolean isSortedAscending(List<Integer> products) {

        for(int i=0;i<products.size()-1;i++) {
            if(products.get(i)>=products.get(i+1)) {
                return false;
            }
        }
        return true;
    }

    public boolean isSortedDescending(List<Integer> products) {

        for(int i=0;i<products.size()-1;i++) {
            if(products.get(i)<=products.get(i+1)) {
                return false;
            }
        }
        return true;
    }

}
