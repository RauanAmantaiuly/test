package test1;

import io.restassured.RestAssured;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;

public class SoapRequest {
    public static void main(String[] args) throws IOException {
        RestAssured.baseURI = "https://api.example.com/soap";

        String soapBody = new String(Files.readAllBytes(Paths.get("src/test/resources/request.xml")));

        Response response = given()
                .header("Content-Type", "text/xml; charset=UTF-8")
                .body(soapBody)
                .when()
                .post();

        System.out.println("Status: " + response.getStatusCode());
        System.out.println("Response:\n" + response.getBody().asString());

        XmlPath xmlPath = new XmlPath(response.getBody().asString());
        String result = xmlPath.getString("Envelope.Body.YourOperationResponse.Result");
        System.out.println("Extracted result: " + result);
    }
}

