package com.example.gps_tracker;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;

@Controller
public class LocationController {


    @MessageMapping("/send-location")
    @SendTo("/topic/get-location")
    public Location sendLocation(Location location, SimpMessageHeaderAccessor headerAccessor) {
        String sessionId = headerAccessor.getSessionId(); // Get WebSocket session ID
        location.setId(sessionId); // Assign session ID as the unique ID
        return location;
    }
    
    // @MessageMapping("/send-location")
    // @SendTo("/topic/get-location")
    // public Location sendLocation(Location location) {
    //     return location;
    // }
}
