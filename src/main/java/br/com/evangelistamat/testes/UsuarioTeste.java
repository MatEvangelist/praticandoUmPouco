package br.com.evangelistamat.testes;

import br.com.evangelistamat.entidades.Usuario;
import org.apache.http.HttpStatus;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class UsuarioTeste extends BaseTeste {

    private static final String LISTAR_USUARIOS_ENDPOINT = "/users";
    private static final String CRIAR_USUARIOS_ENDPOINT = "/user";


    @Test
    public void testeListaMetadadosDoUsuario() {
        given()
            .params("page", "2")
        .when()
            .get(LISTAR_USUARIOS_ENDPOINT)
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
            .post(CRIAR_USUARIOS_ENDPOINT)
        .then()
            .statusCode(HttpStatus.SC_CREATED)
            .body("name", is("Mathews"));
    }
}
