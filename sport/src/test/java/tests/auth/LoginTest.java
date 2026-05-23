package tests.auth;

import body.auth.LoginBody;
import groovy.json.JsonOutput;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.testng.annotations.Test;
import utils.ConfigReader;

import java.io.FileWriter;
import java.io.IOException;

public class LoginTest {

    @Test
    public void loginTest() throws IOException {
        // 1. Hit endpoint url auth
        RestAssured.baseURI = ConfigReader.getProperty("baseUrl");

        // 2. Generate payload login (ambil payload dari method di src/main/java/body/auth)
        LoginBody loginBody = new LoginBody();

        // 3. Hit endpoint
        Response response = RestAssured.given()
                .header("content-type", "application/json")
                .body(loginBody.loginData().toString())
                .when()
                .post("v1/login")
                .then()
                .extract().response();

        System.out.println("Response : " + response.asString());

        // 4. Extract token
        String token = response.jsonPath().get("data.token");
        System.out.println("Token : " + token);

        // 5. Simpan token di resource token.json
        JSONObject tokenJson = new JSONObject();
        tokenJson.put("token", token);

        // PERBAIKAN: Menggunakan kurung kurawal {} untuk try-with-resources
        try (FileWriter file = new FileWriter("src/resources/json/token.json")) {
            file.write(tokenJson.toJSONString());
            file.flush();

            // PERBAIKAN: Memasukkan println ke dalam method dan blok try
            System.out.println("Token saved to token.json");
        } catch (IOException e) {
            System.out.println("Gagal menyimpan file: " + e.getMessage());
            e.printStackTrace();
        }
    }
}