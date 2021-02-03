package br.com.evangelistamat.testes;

import br.com.evangelistamat.entidades.Usuario;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class UsuarioTesteAlternativo {

    private static final String BASE_URL = "https://reqres.in";
    private static final String BASE_PATH = "/api";
    private static final String LISTAR_USUARIOS_ENDPOINT = "/users";
    private static final String CRIAR_USUARIOS_ENDPOINT = "/user";
    private static final String MOSTRAR_USUARIOS_ENDPOINT = "/users/{userId}";

    @BeforeClass
    public static void setup() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    public void testeListaMetadadosDoUsuario() {
        String uri = getUri(LISTAR_USUARIOS_ENDPOINT);

        given()
            .params("page", "2")
        .when()
            .get(uri)
        .then()
            .contentType(ContentType.JSON)
            .statusCode(HttpStatus.SC_OK)
            .body("page", is(2))
            .body("data", is(notNullValue()));
    }

    @Test
    public void testeCriarUsuarioComSucesso() {
        String uri = getUri(CRIAR_USUARIOS_ENDPOINT);

        // Usuario usuario = new Usuario("Mathews", "desempregado", "mathews.pee@gmail.com", "Evangelista");
        Map<String, String> usuario = new HashMap<>();
        usuario.put("name", "Mathews");
        usuario.put("job", "desempregado");

        given()
            .contentType(ContentType.JSON)
            .body(usuario)
        .when()
            .post(uri)
        .then()
            .contentType(ContentType.JSON)
            .statusCode(HttpStatus.SC_CREATED)
            .body("name", is("Mathews"));
    }

    @Test
    public void testeTamanhoDosItensMostradosIgualAoPerPage() {
        String uri = getUri(LISTAR_USUARIOS_ENDPOINT);

        int pageEsperado = 2;
        int perPageEsperado = getPerPageEsperado(pageEsperado);

        given()
            .param("page", pageEsperado)
        .when()
            .get(uri)
        .then()
            .contentType(ContentType.JSON)
            .statusCode(HttpStatus.SC_OK)
            .body(
                "page", is(2),
                "data.size()", is(perPageEsperado),
                "data.findAll{it.avatar.startsWith('https://reqres.in/img/faces/')}.size()", is(perPageEsperado)
            );
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testeMostrarUsuarioEspecifico(){
        String uri = getUri(MOSTRAR_USUARIOS_ENDPOINT);

        Usuario usuario =
        given()
            .pathParam("userId", 2)
        .when()
            .get(uri)
        .then()
            .contentType(ContentType.JSON)
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
        String uri = getUri(LISTAR_USUARIOS_ENDPOINT);

        return given()
                    .param("page", page)
                .when()
                .   get(uri)
                .then()
                    .statusCode(HttpStatus.SC_OK)
                    .extract()
                        .path("per_page");
    }

    private String getUri(String endpoint) {
        return BASE_URL + BASE_PATH + endpoint;
    }
}
