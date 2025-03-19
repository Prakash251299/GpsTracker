package com.example.gps_tracker;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WebController {
    @GetMapping("/")
    public String index() {
        return "index"; // This should return the Thymeleaf template
    }

    @PostMapping("/submit-name")
    public String submitName(@RequestParam("username") String username, Model model) {
        model.addAttribute("username", username); // Pass username to map_screen.html
        return "map_screen"; // Redirects to map_screen.html
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
