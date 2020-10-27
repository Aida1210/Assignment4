package interfaces;

import models.Book;

import java.util.List;

public interface BookRepository extends EntityRepository<Book> {

    List<Book> getAllBooks();

    List<Book> getMyBorrowedBooks(String name);

    void addReader(Book book);

    void removeReader(Book book);

    Book getBookByName(String name);


}
