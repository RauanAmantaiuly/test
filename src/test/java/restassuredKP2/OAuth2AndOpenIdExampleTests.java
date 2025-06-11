package restassuredKP2;

import io.restassured.response.Response;

import java.util.Map;
import java.util.Scanner;

import static io.restassured.RestAssured.*;
import static io.restassured.http.ContentType.JSON;

public class OAuth2AndOpenIdExampleTests {

    // ==== Подставьте свои значения из Auth0 ====
    private static final String DOMAIN        = "dev-30bziiwrbon1bzwz.us.auth0.com";
    private static final String CLIENT_ID     = "QppZJ0lc3dcUZOzZweJYFO9goAJrUijs";
    private static final String CLIENT_SECRET = "djVRjzAshCgDi89zy4MYoVUCRpYG7ZiZjLa-zMW42cg4_iW8_f0tr9SCLwDIRlZp";
    private static final String AUDIENCE      = "https://test-api";
    private static final String USERNAME      = "r_amantaiuly@mail.ru";
    private static final String PASSWORD      = "Kaktusbro228";
    // ==========================================

    public static void main(String[] args) {
        // Устанавливаем базовый URI к вашему домену Auth0
        baseURI = "https://" + DOMAIN;

        // -------------------------------
        // 1) ROPG с realm
        // -------------------------------
        Response r1 = given()
                .contentType(JSON)
                .body(Map.of(
                        "grant_type",    "http://auth0.com/oauth/grant-type/password-realm",
                        "realm",         "Username-Password-Authentication",
                        "username",      USERNAME,
                        "password",      PASSWORD,
                        "audience",      AUDIENCE,
                        "scope",         "openid profile email",
                        "client_id",     CLIENT_ID,
                        "client_secret", CLIENT_SECRET
                ))
                .when().post("/oauth/token")
                .then().statusCode(403)  // ожидаем mfa_required
                .extract().response();

        String mfaToken = r1.path("mfa_token");
        if (mfaToken == null) {
            throw new IllegalStateException("Не пришёл mfa_token — включите Require MFA и Database-Connection для пользователя");
        }
        System.out.println("✅ Шаг 1: получили mfa_token");

        // -------------------------------
        // 2) Отправляем email-OTP через /mfa/challenge
        // -------------------------------
        Response challenge = given()
                .contentType(JSON)
                .body(Map.of(
                        "mfa_token",      mfaToken,
                        "challenge_type", "oob",
                        "oob_channels",   new String[]{ "email" }
                ))
                .when().post("/mfa/challenge")
                .then().statusCode(200)
                .extract().response();

        String oobCode = challenge.path("oob_code");
        if (oobCode == null) {
            throw new IllegalStateException("Не пришёл oob_code — проверьте Email Provider и политику MFA");
        }
        System.out.println("✅ Шаг 2: oob_code = " + oobCode + " (код выслан на e-mail)");

        // -------------------------------
        // 3) Вводим код из письма
        // -------------------------------
        System.out.print("Введите OTP из письма: ");
        String otp = new Scanner(System.in).nextLine().trim();

        // -------------------------------
        // 4) Подтверждаем OTP → получаем токены
        // -------------------------------
        Response r3 = given()
                .contentType(JSON)
                .body(Map.of(
                        "grant_type",    "http://auth0.com/oauth/grant-type/mfa-otp",
                        "client_id",     CLIENT_ID,
                        "client_secret", CLIENT_SECRET,
                        "mfa_token",     mfaToken,
                        "oob_code",      oobCode,
                        "binding_code",  otp
                ))
                .when().post("/oauth/token")
                .then().statusCode(200)
                .extract().response();

        String accessToken = r3.path("access_token");
        String idToken     = r3.path("id_token");
        System.out.println("→ OAuth2 Access Token: " + accessToken);
        System.out.println("→ OpenID ID Token:    " + idToken);

        // -------------------------------
        // 5) Вызов защищённого API
        // -------------------------------
        given()
                .auth().oauth2(accessToken)
                .when().get(AUDIENCE + "/protected")
                .then().statusCode(200).log().body();

        // -------------------------------
        // 6) Запрос /userinfo
        // -------------------------------
        given()
                .header("Authorization", "Bearer " + idToken)
                .when().get("https://" + DOMAIN + "/userinfo")
                .then().statusCode(200).log().body();
    }
}
