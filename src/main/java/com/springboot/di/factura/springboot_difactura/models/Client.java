package com.springboot.di.factura.springboot_difactura.models;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;
// import org.springframework.web.context.annotation.SessionScope;

// import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * La anotación @SessionScope en Spring se utiliza para definir el alcance de un
 * bean de modo que su instancia sea única y se mantenga durante el ciclo de
 * vida de una sesión HTTP. Esto significa que un bean con @SessionScope será
 * creado una vez por sesión y compartido por todas las solicitudes que
 * pertenecen a esa sesión.
 * 
 * Uso de @SessionScope
 * Propósito
 * Mantener Estado a Través de Múltiples Solicitudes: Ideal para escenarios
 * donde necesitas mantener información del usuario a través de varias
 * solicitudes dentro de la misma sesión.
 * Sesiones de Usuario: Utilizado comúnmente para datos que deben persistir
 * durante la sesión del usuario, como el carrito de compras en una aplicación
 * de comercio electrónico, datos de autenticación del usuario, etc.
 */
@Component
// @SessionScope
@RequestScope
// @JsonIgnoreProperties({ "targetSource", "advisors" })
public class Client {

    @Value("${client.name}")
    private String name;
    @Value("${client.lastname}")
    private String lastname;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}
