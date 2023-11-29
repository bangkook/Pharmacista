
package com.example.pharmacysystem.controller;
import com.example.pharmacysystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@CrossOrigin(
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE},
        allowedHeaders = {"Authorization", "Content-Type"},
        allowCredentials = "true",
        exposedHeaders = "X-Custom-Header"
)

public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping("/login")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<Boolean> checkUserExist(@RequestParam(name = "email") String email) {
        return null;
    }
}
