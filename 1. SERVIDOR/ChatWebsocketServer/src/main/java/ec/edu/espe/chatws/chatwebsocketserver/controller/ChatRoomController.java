package ec.edu.espe.chatws.chatwebsocketserver.controller;

import ec.edu.espe.chatws.chatwebsocketserver.entity.ChatMessage;
import ec.edu.espe.chatws.chatwebsocketserver.entity.ChatRoom;
import ec.edu.espe.chatws.chatwebsocketserver.service.ChatMessageService;
import ec.edu.espe.chatws.chatwebsocketserver.service.ChatRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat-rooms")
public class ChatRoomController {
    @Autowired
    private ChatRoomService chatRoomService;

    @Autowired
    private ChatMessageService chatMessageService;

    @GetMapping
    public List<ChatRoom> getChatRooms() {
        return chatRoomService.findAll();
    }

    @PostMapping
    public ChatRoom createChatRoom(@RequestBody ChatRoom chatRoom) {
        return chatRoomService.createChatRoom(chatRoom);
    }

    @GetMapping("/{roomId}/messages")
    public List<ChatMessage> getChatRoomMessages(@PathVariable long roomId) {
        return chatMessageService.findByRoomId(roomId);
    }

    @DeleteMapping("/{roomId}/exit")
    public ResponseEntity<String> exitRoom(@PathVariable Long roomId, @RequestParam Long userId) {
        boolean isRemoved = chatRoomService.removeUserFromRoom(roomId, userId);
        if (isRemoved) {
            return ResponseEntity.ok("User successfully exited the room");
        }
        return ResponseEntity.badRequest().body("Failed to exit the room");
    }
}
