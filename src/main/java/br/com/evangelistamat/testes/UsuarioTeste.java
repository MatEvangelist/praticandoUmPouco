package br.com.evangelistamat.testes;

import br.com.evangelistamat.entidades.Usuario;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class UsuarioTeste extends BaseTeste {

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
        Usuario usuario = new Usuario("Mathews", "desempregado", "mathews.pee@gmail.com");

        given()
            .body(usuario)
        .when()
            .post("/user")
        .then()
            .statusCode(HttpStatus.SC_CREATED)
            .body("name", is("Mathews"));
    }
}
