package com.example.gps_tracker;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {
    @GetMapping("/")
    public String index() {
        return "index"; // This should return the Thymeleaf template
    }
}



// package com.example.gps_tracker;

// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

// @RestController
// // @RequestMapping("/api")
// public class WebController {
//     // @GetMapping("/hello")
//     @GetMapping("/")
//     public String index() {
//         // return "Hello, WebSocket is configured!";
//         return "index";
//     }
// }
