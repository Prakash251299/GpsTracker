package com.example.gps_tracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@SpringBootApplication
public class GpsTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(GpsTrackerApplication.class, args);
	}
}


// @Controller
// class WebController {
//     @GetMapping("/")
//     public String index() {
//         return "index";
//     }
// }

// @EnableWebSocketMessageBroker
// class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
//     @Override
//     public void registerStompEndpoints(StompEndpointRegistry registry) {
//         registry.addEndpoint("/ws").setAllowedOrigins("*").withSockJS();
//     }
// }

// class Location {
//     private String id;
//     private double latitude;
//     private double longitude;
    
//     // Constructors, Getters, Setters
// }

// @Controller
// class LocationController {
//     @MessageMapping("/send-location")
//     @SendTo("/topic/get-location")
//     public Location sendLocation(Location location) {
//         return location;
//     }
// }
