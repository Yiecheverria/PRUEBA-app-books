package com.distribuida.clientes.authors;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;

@ToString
public class AuthorsCliente {
 @Column(name = "id")
 @Getter @Setter Long id;
 @Column(name = "first_name")
 @Getter @Setter String firstName;
 @Column(name = "last_name")
 @Getter @Setter String lastName;

}
