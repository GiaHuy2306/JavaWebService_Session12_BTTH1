package com.btth1.service;

import com.btth1.entity.Book;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class BookService {
    private final List<Book> books = new ArrayList<>();
    private final AtomicLong idCounter = new AtomicLong(1); // Tự động tăng ID

    public BookService() {
        // Thêm sẵn vài cuốn sách để test cho tiện
        books.add(new Book(idCounter.getAndIncrement(), "Lập trình Java", "Nguyễn Văn A", 150000.0));
        books.add(new Book(idCounter.getAndIncrement(), "Spring Boot Căn Bản", "Trần Thị B", 200000.0));
    }

    public List<Book> getAllBooks() {
        return books;
    }

    public Optional<Book> getBookById(Long id) {
        return books.stream().filter(book -> book.getId().equals(id)).findFirst();
    }

    public Book addBook(Book book) {
        book.setId(idCounter.getAndIncrement());
        books.add(book);
        return book;
    }

    public Optional<Book> updateBook(Long id, Book updatedBook) {
        return getBookById(id).map(existingBook -> {
            existingBook.setTitle(updatedBook.getTitle());
            existingBook.setAuthor(updatedBook.getAuthor());
            existingBook.setPrice(updatedBook.getPrice());
            return existingBook;
        });
    }

    public boolean deleteBook(Long id) {
        return books.removeIf(book -> book.getId().equals(id));
    }
}
