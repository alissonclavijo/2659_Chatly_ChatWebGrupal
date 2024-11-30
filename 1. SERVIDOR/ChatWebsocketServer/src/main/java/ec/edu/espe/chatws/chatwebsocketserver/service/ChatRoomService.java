package ec.edu.espe.chatws.chatwebsocketserver.service;

import ec.edu.espe.chatws.chatwebsocketserver.entity.ChatRoom;

import java.util.List;

public interface ChatRoomService {
    ChatRoom findById(long roomId);
    List<ChatRoom> findAll();
    ChatRoom createChatRoom(ChatRoom chatRoom);

    // Nuevo método para eliminar un usuario de una sala
    boolean removeUserFromRoom(Long roomId, Long userId);
}

