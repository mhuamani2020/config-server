package com.nttdata.config_server;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.config.environment.Environment;
import org.springframework.cloud.config.server.environment.EnvironmentRepository;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Pruebas del servidor de configuración centralizada.
 * <p>
 * Se activa el perfil {@code native} y se apunta a un repositorio de configuración de
 * pruebas en el classpath, de modo que el test no dependa de la red ni del repositorio Git
 * real. Comprueba que el contexto arranca y que el servidor resuelve propiedades para una
 * aplicación y perfil dados.
 *
 * @author NTT Data
 */
@SpringBootTest(properties = {
        "spring.profiles.active=native",
        "spring.cloud.config.server.native.search-locations=classpath:/config-repo"
})
class ConfigServerApplicationTest {

    @Autowired
    private EnvironmentRepository environmentRepository;

    @Test
    @DisplayName("El contexto del servidor de configuración arranca correctamente")
    void contextLoads() {
        assertThat(environmentRepository).isNotNull();
    }

    @Test
    @DisplayName("El servidor resuelve las propiedades del repositorio de configuración")
    void shouldServePropertiesFromConfigurationRepository() {
        Environment environment = environmentRepository.findOne("test-app", "default", null);

        assertThat(environment).isNotNull();
        assertThat(environment.getPropertySources()).isNotEmpty();
        assertThat(environment.getPropertySources())
                .anyMatch(propertySource ->
                        "config-server-test".equals(propertySource.getSource().get("app.test.value")));
    }
}
