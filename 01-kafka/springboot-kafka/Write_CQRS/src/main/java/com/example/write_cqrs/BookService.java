package com.example.write_cqrs;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Service
@RequiredArgsConstructor

public class BookService {
    private final BookRepository bookRepository;
    private final KafkaProducer kafkaProducer;

    public void saveBook(BookDTO bookDTO){
        try{
            SimpleDateFormat formatter = new SimpleDateFormat(
                    "yyyy-MM-dd", Locale.ENGLISH);
            Date published_date = formatter.parse(bookDTO.getPublished_data());
            Book book = Book.builder()
                    .title(bookDTO.getTitle())
                    .author(bookDTO.getAuthor())
                    .category(bookDTO.getCategory())
                    .pages(bookDTO.getPages())
                    .price(bookDTO.getPrice())
                    .published_date(published_date)
                    .description(bookDTO.getDescription())
                    .build();
            bookRepository.save(book);
            bookDTO.setBid(book.getBid());
            //쓰기 작업을 완료할 때 카프카에게 메시지를 전송
            kafkaProducer.sendMessage(bookDTO);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
