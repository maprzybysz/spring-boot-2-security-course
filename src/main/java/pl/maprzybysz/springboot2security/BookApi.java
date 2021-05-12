package pl.maprzybysz.springboot2security;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookApi {

    private List<String> bookList;

    public BookApi() {
        this.bookList = new ArrayList<>();
        bookList.add("Spring Boot 2");
        bookList.add("Jaś i Małgosia");
    }

    @GetMapping
    public List<String> getBookList() {
        return bookList;
    }

    @PostMapping
    public void setBookList(@RequestBody String book) {
        bookList.add(book);
    }
}
