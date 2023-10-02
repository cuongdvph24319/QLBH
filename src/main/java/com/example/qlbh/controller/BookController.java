package com.example.qlbh.controller;

import com.example.qlbh.model.Book;
import com.example.qlbh.model.BookList;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class BookController {


    @PostMapping("/xml")
    public ResponseEntity<Book> getBook(
//            MultipartFile file
            @RequestBody String xmlSrt
    ) throws IOException {
        Book book = new Book();
        try {

            JAXBContext jaxbContext = JAXBContext.newInstance(Book.class);

            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            // Chuyển đổi XML thành đối tượng
            book = (Book) unmarshaller.unmarshal(new StringReader(xmlSrt));

        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(book);
    }


    static List<Book> bookList = new ArrayList<>();

    public static Book convertXmlToBook(String xmlData, Class<Book> book) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(book);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        StringReader reader = new StringReader(xmlData);
        return book.cast(unmarshaller.unmarshal(reader));
    }

    public static String convertBookToXml() throws JAXBException {
        BookList list = new BookList();
        list.setBooks(bookList);

        JAXBContext jaxbContext = JAXBContext.newInstance(BookList.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        StringWriter writer = new StringWriter();

        marshaller.marshal(list, writer);
        return writer.toString();
    }

    @GetMapping("/xml")
    public ResponseEntity<String> getBooksInXML() throws JAXBException {

        if (bookList.isEmpty()) {
            Book book1 = new Book();
            book1.setName("A Song of Ice and Fire");
            book1.setAuthor("George R. R. Martin");
            book1.setLanguage("English");
            book1.setGenre("Epic fantasy");

            bookList.add(book1);
        }
        String xmlResult = convertBookToXml();
        return ResponseEntity.ok(xmlResult);
    }

    @PostMapping("/addxml")
    public ResponseEntity<String> addBookInXML(@RequestBody String xmlData) throws JAXBException {
        Book newBook = convertXmlToBook(xmlData, Book.class);
        bookList.add(newBook);

        String xmlResult = convertBookToXml();

        return ResponseEntity.ok(xmlResult);
    }

    @PutMapping("/updatexml/{name}")
    public ResponseEntity<String> updateXml(
            @PathVariable("name") String name,
            @RequestBody String xmlData
    ) throws JAXBException {
        Book updatedBook = convertXmlToBook(xmlData, Book.class);

        for (int i = 0; i < bookList.size(); i++) {
            if (bookList.get(i).getName().equals(name)) {
                bookList.set(i, updatedBook);

                String xmlResult = convertBookToXml();

                return ResponseEntity.ok(xmlResult);
            }
        }
        return ResponseEntity.ok("Không tìm thấy sách với tên: " + name);
    }
}
