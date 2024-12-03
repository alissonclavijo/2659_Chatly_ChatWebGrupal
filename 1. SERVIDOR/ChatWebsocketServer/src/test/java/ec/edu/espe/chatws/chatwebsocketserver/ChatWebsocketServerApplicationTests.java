package ec.edu.espe.chatws.chatwebsocketserver;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ChatWebsocketServerApplicationTests {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void contextLoads() {
        // Verificar que el contexto de Spring se cargue correctamente
        assertThat(applicationContext).isNotNull();
    }
}
