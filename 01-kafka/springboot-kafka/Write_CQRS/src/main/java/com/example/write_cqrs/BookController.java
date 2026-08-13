package com.example.write_cqrs;

import jakarta.persistence.*;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
@RestController
@RequiredArgsConstructor

public class BookController {
    private final BookService bookService;

    @GetMapping("/")
    public String index(){
        return "homepage";
    }

    @GetMapping("/health")
    public String healthCheck(){
        return "success";
    }

    @PostMapping("/cqrs/book")
    public String saveBook(@RequestBody BookDTO bookDTO){
        bookService.saveBook(bookDTO);
        return "success";
    }
}
