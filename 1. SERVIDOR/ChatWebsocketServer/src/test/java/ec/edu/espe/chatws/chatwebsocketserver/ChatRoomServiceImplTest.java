package ec.edu.espe.chatws.chatwebsocketserver.service.impl;

import ec.edu.espe.chatws.chatwebsocketserver.entity.ChatRoom;
import ec.edu.espe.chatws.chatwebsocketserver.repository.ChatRoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ChatRoomServiceImplTest {

    @InjectMocks
    private ChatRoomServiceImpl chatRoomService;

    @Mock
    private ChatRoomRepository chatRoomRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Inicializa los mocks
    }

    @Test
    void testFindById() {
        // Configurar el mock
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setId(1L);
        chatRoom.setName("Test Room");
        when(chatRoomRepository.findById(1L)).thenReturn(Optional.of(chatRoom));

        // Ejecutar método
        ChatRoom result = chatRoomService.findById(1L);

        // Verificar resultados
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Test Room", result.getName());

        // Verificar interacción con el repositorio
        verify(chatRoomRepository, times(1)).findById(1L);
    }

    @Test
    void testFindAll() {
        // Configurar el mock
        ChatRoom room1 = new ChatRoom();
        room1.setId(1L);
        room1.setName("Room 1");

        ChatRoom room2 = new ChatRoom();
        room2.setId(2L);
        room2.setName("Room 2");

        when(chatRoomRepository.findAll()).thenReturn(Arrays.asList(room1, room2));

        // Ejecutar método
        List<ChatRoom> result = chatRoomService.findAll();

        // Verificar resultados
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Room 1", result.get(0).getName());
        assertEquals("Room 2", result.get(1).getName());

        // Verificar interacción con el repositorio
        verify(chatRoomRepository, times(1)).findAll();
    }

    @Test
    void testCreateChatRoom() {
        // Configurar el mock
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setName("New Room");

        when(chatRoomRepository.save(chatRoom)).thenReturn(chatRoom);

        // Ejecutar método
        ChatRoom result = chatRoomService.createChatRoom(chatRoom);

        // Verificar resultados
        assertNotNull(result);
        assertEquals("New Room", result.getName());

        // Verificar interacción con el repositorio
        verify(chatRoomRepository, times(1)).save(chatRoom);
    }
}
