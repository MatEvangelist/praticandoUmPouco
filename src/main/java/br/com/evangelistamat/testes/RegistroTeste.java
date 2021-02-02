package br.com.evangelistamat.testes;

import br.com.evangelistamat.entidades.Usuario;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class RegistroTeste extends BaseTeste {

    @Test
    public void testeNaoEfetuaRegistroComSenhaFaltando() {
        Usuario usuario = new Usuario();
        usuario.setEmail("mathews.pee@gmail.com");

        given()
            .body(usuario)
        .when()
            .post("/register")
        .then()
            .statusCode(HttpStatus.SC_BAD_REQUEST)
            .body("error", is("Missing password"));
    }
}
