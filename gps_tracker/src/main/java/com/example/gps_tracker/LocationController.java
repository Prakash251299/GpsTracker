package com.example.gps_tracker;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

@Controller
public class LocationController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    // Store active users with session IDs
    private static final Map<String, String> activeUsers = new ConcurrentHashMap<>();

    @MessageMapping("/send-location")
    @SendTo("/topic/get-location")
    public Location sendLocation(Location location, SimpMessageHeaderAccessor headerAccessor) {
        String sessionId = headerAccessor.getSessionId(); 
        location.setId(sessionId); 
        activeUsers.put(sessionId, sessionId); // Track active users
        return location;
    }

    // Handle new WebSocket connection
    @EventListener
    public void handleConnect(SessionConnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = headerAccessor.getSessionId();
        System.out.println("User connected: " + sessionId);
    }

    // Handle WebSocket disconnect event
    @EventListener
    public void handleDisconnect(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = headerAccessor.getSessionId();

        if (sessionId != null && activeUsers.containsKey(sessionId)) {
            System.out.println("User disconnected: " + sessionId);
            
            // Remove user from active users list
            activeUsers.remove(sessionId);

            // Send a special signal to remove the marker
            Location location = new Location();
            location.setId(sessionId);
            location.setLatitude(Double.NaN);
            location.setLongitude(Double.NaN);

            // Notify only about the disconnected user
            messagingTemplate.convertAndSend("/topic/get-location", location);
        }
    }
}











// package com.example.gps_tracker;

// import org.springframework.messaging.handler.annotation.MessageMapping;
// import org.springframework.messaging.handler.annotation.SendTo;
// import org.springframework.stereotype.Controller;
// import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
// import org.springframework.web.socket.messaging.SessionDisconnectEvent;
// import org.springframework.context.event.EventListener;
// import org.springframework.messaging.simp.stomp.StompHeaderAccessor;

// import org.springframework.messaging.simp.SimpMessagingTemplate;
// import org.springframework.beans.factory.annotation.Autowired;



// @Controller
// public class LocationController {


//     private SimpMessagingTemplate messagingTemplate; // Inject SimpMessagingTemplate

//     @MessageMapping("/send-location")
//     @SendTo("/topic/get-location")
//     public Location sendLocation(Location location, SimpMessageHeaderAccessor headerAccessor) {
//         String sessionId = headerAccessor.getSessionId(); // Get WebSocket session ID
//         location.setId(sessionId); // Assign session ID as the unique ID
//         return location;
//     }
    
//      // Handle WebSocket disconnect event
//     @EventListener
//     public void handleDisconnect(SessionDisconnectEvent event) {
//         StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
//         String sessionId = headerAccessor.getSessionId();
//         if (sessionId != null) {
//             System.out.println("User disconnected: " + sessionId);
//             // Notify all clients to remove the marker of the disconnected user
//             Location location = new Location();
//             location.setId(sessionId);
//             location.setLatitude(999.0); // Signal frontend to remove marker
//             location.setLongitude(999.0);
//             // WebSocketConfig.messagingTemplate.convertAndSend("/topic/get-location", location);


//             messagingTemplate.convertAndSend("/topic/get-location", location);
//         }
//     }
// }
