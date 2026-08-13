package com.example.read_cqrs;


import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.json.JSONObject;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class KafkaConsumer {
    @KafkaListener(topics = "cqrs-topic", groupId = "adamsoft")
    public void consumer(String message) throws IOException{
        System.out.println("message:"+message);
        //JSON 파싱
        JSONObject messageObj = new JSONObject(message);

        //MongoDB 컬렉션에 연결
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = mongoClient.getDatabase("mymongo");
        MongoCollection<Document> mongo_books=
                database.getCollection("books");

        //받은 데이터로 삽입할 데이터를 만든다.
        Document book = new Document();
        book.append("bid", messageObj.getLong("bid"));
        mongo_books.insertOne(book);
        mongoClient.close();
    }
}
