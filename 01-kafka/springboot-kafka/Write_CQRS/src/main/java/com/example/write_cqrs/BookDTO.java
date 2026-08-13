package com.example.write_cqrs;

import lombok.Data;

@Data

public class BookDTO {
    private Long bid;

    private String title;
    private String author;
    private String category;
    private int pages;
    private int price;
    private String published_data;
    private String description;
}
