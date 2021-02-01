package br.com.evangelistamat.testes;

import br.com.evangelistamat.entidades.Usuario;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class UsuarioTeste {

    @BeforeClass
    public static void setup() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        baseURI = "https://reqres.in";
        basePath = "/api";
    }

    @Test
    public void testeListaMetadadosDoUsuario() {
        given()
            .params("page", "2")
        .when()
            .get("/users")
        .then()
            .statusCode(HttpStatus.SC_OK)
            .body("page", is(2))
            .body("data", is(notNullValue()));
    }

    @Test
    public void testeCriarUsuarioComSucesso() {
        Usuario usuario = new Usuario("Mathews", "desempregado");

        given()
            .contentType(ContentType.JSON)
            .body(usuario)
        .when()
            .post("/user")
        .then()
            .statusCode(HttpStatus.SC_CREATED)
            .body("name", is("Mathew"));
    }
}
