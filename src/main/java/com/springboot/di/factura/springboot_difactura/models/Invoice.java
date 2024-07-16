package com.springboot.di.factura.springboot_difactura.models;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

// import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

/**
 * @RequestScope
 *               La anotación @RequestScope se utiliza para definir el alcance
 *               (scope) de un bean en una aplicación web de Spring.
 *               Específicamente, indica que una nueva instancia del bean se
 *               creará para cada solicitud HTTP y será destruida una vez que la
 *               solicitud haya sido procesada.
 * @JsonIgnoreProperties
 *                       La anotación @JsonIgnoreProperties se utiliza en el
 *                       contexto de la serialización y deserialización JSON con
 *                       Jackson, una biblioteca popular para trabajar con JSON
 *                       en Java. Esta anotación indica que ciertas propiedades
 *                       del objeto deben ser ignoradas durante la serialización
 *                       y deserialización JSON.
 * @RequestScope
 *               ¿Qué Hace?
 *               La anotación @RequestScope indica que el bean Invoice debe ser
 *               creado y mantenido durante el ciclo de vida de una única
 *               solicitud HTTP. Cada vez que una nueva solicitud HTTP llega al
 *               servidor, una nueva instancia del bean Invoice se creará y se
 *               destruirá al finalizar la solicitud.
 * 
 *               ¿Por Qué Usarlo?
 *               Manejo de Estado: Si necesitas que cada solicitud tenga su
 *               propio estado, @RequestScope es ideal. Por ejemplo, si cada
 *               solicitud necesita su propia factura independiente de otras
 *               solicitudes, usar @RequestScope asegura que cada solicitud HTTP
 *               trate con una instancia única de Invoice.
 *               Ciclo de Vida Corto: Para beans que no necesitan ser
 *               compartidos entre solicitudes y cuyo estado debe ser limpio al
 *               inicio de cada solicitud, @RequestScope proporciona un ciclo de
 *               vida adecuado.
 *               Contexto de la Aplicación
 *               En la clase Invoice, @RequestScope garantiza que cada factura
 *               sea única para cada solicitud HTTP y se destruye
 *               automáticamente al terminar la solicitud. Esto es útil para
 *               evitar compartir inadvertidamente el estado de una factura
 *               entre diferentes solicitudes.
 * @JsonIgnoreProperties
 *                       ¿Qué Hace?
 *                       La anotación @JsonIgnoreProperties se usa para
 *                       especificar que ciertas propiedades del bean deben ser
 *                       ignoradas durante la serialización y deserialización
 *                       JSON. En tu caso, targetSource y advisors serán
 *                       ignorados cuando Invoice se convierta a JSON y
 *                       viceversa.
 * 
 *                       ¿Por Qué Usarlo?
 *                       Seguridad y Privacidad: Ignorar propiedades sensibles o
 *                       internas que no deben ser expuestas en la respuesta
 *                       JSON.
 *                       Simplificación: Reducir el tamaño del JSON omitido
 *                       propiedades no necesarias para el cliente o para evitar
 *                       errores en el proceso de serialización/deserialización.
 *                       Optimización: Mejorar el rendimiento al evitar la
 *                       serialización de propiedades innecesarias.
 *                       Contexto de tu Aplicación
 *                       En tu clase Invoice, @JsonIgnoreProperties({
 *                       "targetSource", "advisors" }) asegura que las
 *                       propiedades targetSource y advisors no se incluyan en
 *                       la representación JSON de una factura. Esto es útil si
 *                       estas propiedades son internas y no relevantes para la
 *                       respuesta JSON que espera el cliente.
 */
@Component
@RequestScope
// @JsonIgnoreProperties({ "targetSource", "advisors" })
public class Invoice {

    @Autowired
    private Client client;

    @Value("${invoice.description.office}")
    private String description;

    @Autowired
    @Qualifier("default")
    private List<Item> items;

    /**
     * La anotación @PostConstruct en Spring Boot se usa para marcar un método que
     * debe ser ejecutado después de que la inyección de dependencias en un bean de
     * Spring se haya completado. Es decir, cuando el contenedor de Spring ha
     * terminado de crear el bean y de inyectar todas sus dependencias, este método
     * es llamado automáticamente.
     * 
     * Usos Comunes de @PostConstruct
     * Inicialización de Recursos: Para abrir conexiones a bases de datos, iniciar
     * conexiones de red, etc.
     * Configuración Adicional: Para configurar propiedades del bean que dependen de
     * otras dependencias inyectadas.
     * Validaciones: Para realizar validaciones adicionales después de la creación
     * del bean.
     */
    @PostConstruct
    public void init() {
        System.out.println("Creando el componente de la factura");
        // System.out.println(client);
        // System.out.println(description);
        client.setName(client.getName().concat(" Cambios"));
        description = description.concat(" del cliente: ").concat(client.getName()).concat(" ")
                .concat(client.getLastname());
    }

    /**
     * La anotación @PreDestroy en Spring Boot se usa para marcar un método que debe
     * ser ejecutado justo antes de que un bean sea destruido por el contenedor de
     * Spring. Esto es útil para realizar cualquier limpieza o liberación de
     * recursos que el bean haya estado utilizando, como cerrar conexiones de base
     * de datos, liberar hilos, etc.
     * Este método es invocado automáticamente cuando el contenedor de Spring decide
     * destruir el bean, generalmente durante el apagado de la aplicación.
     * Consideraciones
     * 
     * @PreDestroy debe ser utilizado en métodos public y sin parámetros.
     *             Al igual que @PostConstruct, la anotación @PreDestroy pertenece
     *             al paquete javax.annotation.
     *             Si el bean es un singleton, el método anotado con @PreDestroy se
     *             ejecutará cuando el contenedor de Spring se apague. Para beans
     *             con alcance más corto, como el alcance de solicitud, el método se
     *             ejecutará cuando el contenedor determine que el bean ya no es
     *             necesario.
     */
    @PreDestroy
    public void destroy() {
        System.out.println("Desruyendo el componente o bean invocado");
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public int getTotal() {
        // int total = 0;
        // for (Item item : items) {
        // total += item.getImporte();
        // }

        int total = items.stream()
                .map(item -> item.getImporte())
                .reduce(0, (sum, importe) -> sum + importe);

        return total;
    }

}
