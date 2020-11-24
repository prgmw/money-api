package br.com.money.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "permissao")
public class Permissao {

    @Id
    private Long codigo;

    private String descricao;
}
