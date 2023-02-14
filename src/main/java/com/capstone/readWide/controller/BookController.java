package com.capstone.readWide.controller;
// import com.fasterxml.jackson.databind.ObjectMapper;

import java.lang.Iterable;
import java.util.Optional;
// import java.util.HashMap;

import com.capstone.readWide.model.Book;
import com.capstone.readWide.model.Reflection;
import com.capstone.readWide.repository.BookRepository;
import com.capstone.readWide.repository.ReflectionRepository;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.util.ObjectUtils;
// import org.springframework.http.HttpEntity;
// import org.springframework.http.HttpHeaders;
// import org.springframework.http.HttpMethod;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.client.RestTemplate;

@CrossOrigin
@RestController
@RequestMapping("/books")
public class BookController {

    private final BookRepository bookRepository;
    private final ReflectionRepository reflectionRepository;

    public BookController(final BookRepository bookRepository, final ReflectionRepository reflectionRepository) {
        this.bookRepository = bookRepository;
        this.reflectionRepository = reflectionRepository;
    }

    @GetMapping("/{id}")
    public Book getBook(@PathVariable Integer id) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent()) {
            return book.get();
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @GetMapping
    public Iterable<Book> getAllBooks() {
        return this.bookRepository.findAll();
    }

    @GetMapping("/search")
    public Optional<Book> searchBooks(@RequestParam(name="title", required=false) String title, @RequestParam(name="author", required=false) String author) {
        if (title != null && author != null) {
            return this.bookRepository.findByTitleAndAuthor(title, author);
        } else if (title != null) {
            return this.bookRepository.findByTitle(title);
        } else if (author != null) {
            return this.bookRepository.findByAuthor(author);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Sorry, you must enter a valid title or author, or the Book you entered does not exist on ReadWide!");
        }
    }

    // private HashMap<String, String> callGoogleBooksApi(String bookTitle) {
    //     String url = "https://www.googleapis.com/books/v1/volumes?q={queryParameter}";

    //     RestTemplate template = new RestTemplate();
    //     HttpHeaders headers = new HttpHeaders();
    //     HttpEntity requestEntity = new HttpEntity<>(headers);

    //     HashMap<String, String> uriVariables = new HashMap<>();
    //     uriVariables.put("queryParameter", bookTitle);

    //     ResponseEntity<HashMap> response = template.exchange(url, HttpMethod.GET, requestEntity, HashMap.class, uriVariables);

    //     return response.getBody();
    // }

    // public static HashMap<String, String> printApiResult() {
    //     System.out.println(callGoogleBooksApi("Purple+Hibiscus"));
    //     return callGoogleBooksApi("Purple+Hibiscus");
    // }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED, reason = "Book was successfully added!")
    public Book addBook(@RequestBody Book book) {
        if (ObjectUtils.isEmpty(book.getTitle())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You must input a title to add a book to ReadWide!");
        }

        // HashMap googleApiResponse = callGoogleBooksApi(book.getTitle());

        // HashMap<String, String> bookToAdd = new HashMap<String, String>();
        // bookToAdd.put(googleApiResponse.get("items"));
        
        // ObjectMapper mapper = new ObjectMapper();
        // Book bookObjectToAdd = mapper.convertValue(bookToAdd, Book.class);

        Optional<Book> existingBook = bookRepository
                .findByTitleAndAuthor(book.getTitle(), book.getAuthor());
        if (existingBook.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, 
                    "This Book already exists on ReadWide! You can add a reflection instead!");
        }
        Book newBook = this.bookRepository.save(book);
        return newBook;
    }

    // @DeleteMapping("/delete/{id}")
    // public Book deleteBook(@PathVariable("id") Integer id) {
    //     Optional<Iterable<Reflection>> reflectionsToDelete = this.reflectionRepository.findByBookId(id);
    //     if (reflectionsToDelete.isPresent())
    //     {
    //         this.reflectionRepository.deleteAll(reflectionsToDelete.get());
    //     }

    //     Optional<Book> bookToDeleteOptional = this.bookRepository.findById(id);
    //     if (!bookToDeleteOptional.isPresent()) {
    //         return null;
    //     }
    //     Book bookToDelete = bookToDeleteOptional.get();
    //     this.bookRepository.delete(bookToDelete);
    //     return bookToDelete;
    // }
}
