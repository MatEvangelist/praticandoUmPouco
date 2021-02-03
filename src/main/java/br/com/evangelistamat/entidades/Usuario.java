package br.com.evangelistamat.entidades;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@JsonIgnoreProperties(ignoreUnknown = true) //ignora propriedades/atributos desconhecidos/n mapeadas
@Data //cria getters e setters para todos atributos
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {

    @JsonAlias("first_name")
    private String name;
    private String job;
    private String email;
    @JsonAlias("last_name")
    private String lastName;

}
