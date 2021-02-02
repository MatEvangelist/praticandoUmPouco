package br.com.evangelistamat.testes;

import br.com.evangelistamat.entidades.Usuario;
import org.apache.http.HttpStatus;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class UsuarioTeste extends BaseTeste {

    private static final String LISTAR_USUARIOS_ENDPOINT = "/users";
    private static final String CRIAR_USUARIOS_ENDPOINT = "/user";
    private static final String MOSTRAR_USUARIOS_ENDPOINT = "/users/{userId}";


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

    @Test
    public void testeTamanhoDosItensMostradosIgualAoPerPage() {
        int pageEsperado = 2;
        int perPageEsperado = getPerPageEsperado(pageEsperado);

        given()
            .params("page", pageEsperado)
        .when()
            .get(LISTAR_USUARIOS_ENDPOINT)
        .then()
            .statusCode(HttpStatus.SC_OK)
            .body(
                "page", is(2),
                "data.size()", is(perPageEsperado),
                "data.findAll{it.avatar.startsWith('https://reqres.in/img/faces/')}.size()", is(perPageEsperado)
            );
    }

    @Test
    public void testeMostrarUsuarioEspecifico(){
        Usuario usuario =
        given()
            .pathParam("userId", 2)
        .when()
            .get(MOSTRAR_USUARIOS_ENDPOINT)
        .then()
            .statusCode(HttpStatus.SC_OK)
            .extract()
                .body().jsonPath().getObject("data", Usuario.class);
        //extrai para um objeto data e depois transforma em um objeto da classe usuario

        assertThat(usuario.getEmail(), containsString("@reqres.in"));
        assertThat(usuario.getName(), is("Janet"));
        assertThat(usuario.getLastName(), is("Weaver"));
    }

    /*****************************************************************************/

    private int getPerPageEsperado(int page) {
        return given()
                    .param("page", page)
                .when()
                .   get(LISTAR_USUARIOS_ENDPOINT)
                .then()
                    .statusCode(HttpStatus.SC_OK)
                    .extract()
                        .path("per_page");
    }
}
