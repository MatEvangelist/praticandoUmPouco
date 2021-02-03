package br.com.evangelistamat.testes;

import br.com.evangelistamat.entidades.Usuario;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.is;

public class RegistroTeste extends BaseTeste {

    private static final String REGISTRAR_USUARIO_ENDPOINT = "/register";
    private static final String LOGIN_USUARIO_ENDPOINT = "/login";

    @BeforeClass
    public static void setupRegistro() {
        RestAssured.responseSpecification = new ResponseSpecBuilder()
                .expectStatusCode(HttpStatus.SC_BAD_REQUEST).build();
    }

    @Test
    public void testeNaoEfetuaRegistroComSenhaFaltando() {
        Usuario usuario = new Usuario();
        usuario.setEmail("mathews.pee@gmail.com");

        given()
            .body(usuario)
        .when()
            .post(LOGIN_USUARIO_ENDPOINT)
        .then()
            .statusCode(HttpStatus.SC_BAD_REQUEST)
            .body("error", is("Missing password"));
    }

    @Test
    public void testeLoginNaoEfetuado() {
        Usuario usuario = new Usuario();
        usuario.setEmail("mathews.pee@gmail.com");

        given()
            .body(usuario)
        .when()
            .post(REGISTRAR_USUARIO_ENDPOINT)
        .then()
            .statusCode(HttpStatus.SC_BAD_REQUEST)
            .body("error", is("Missing password"));
    }
}
