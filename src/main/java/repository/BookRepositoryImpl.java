package repository;

import interfaces.BookRepository;
import interfaces.Repository;
import models.Book;

import javax.ws.rs.BadRequestException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class BookRepositoryImpl implements BookRepository {
    private final Repository dbrepo = new PostgresRepository();

    @Override
    public List<Book> getAllBooks() {
        String sql = "SELECT * FROM books";
        return query(sql);
    }

    @Override
    public List<Book> getMyBorrowedBooks(String name) {
        String sql = "SELECT * FROM books WHERE username='" + name + "'";
        return query(sql);
    }

    @Override
    public void addReader(Book book) {
        String sql = "UPDATE books SET stock = stock - 1 WHERE isbn =" + book.getIsbn();
        try {
            Statement stmt = dbrepo.getConnection().createStatement();
            stmt.execute(sql);

        } catch (SQLException e) {
            throw new BadRequestException("Cannot run SQL statement: " + e.getSQLState());

        }
    }

    @Override
    public void removeReader(Book book) {
        String sql = "UPDATE books SET stock = stock + 1 WHERE isbn =" + book.getIsbn();
        try {
            Statement stmt = dbrepo.getConnection().createStatement();
            stmt.execute(sql);

        } catch (SQLException e) {
            throw new BadRequestException("Cannot run SQL statement: " + e.getSQLState());

        }
    }

    @Override
    public Book getBookByName(String name) {
        return queryOne("SELECT * FROM books WHERE name='"+name+"';");
    }


    @Override
    public void add(Book entity) {
        String sql = "INSERT INTO books(name,author,stock,image) " +
                "VALUES(?, ?, ?, ?)";
        try {
            PreparedStatement stmt = dbrepo.getConnection().prepareStatement(sql);
            stmt.setString(1, entity.getName());
            stmt.setString(2, entity.getAuthor());
            stmt.setInt(3, entity.getStock());
            stmt.setString(4, entity.getImage());
            stmt.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    @Override
    public void update(Book entity) {

    }

    @Override
    public void remove(Book entity) {
        try {
            Statement stmt = dbrepo.getConnection().createStatement();
            stmt.execute("DELETE from books where isbn=" + entity.getIsbn());

        } catch (SQLException e) {
            throw new BadRequestException("Cannot run SQL statement: " + e.getSQLState());

        }
    }

    @Override
    public List<Book> query(String sql) {
        try {
            Statement stmt = dbrepo.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            LinkedList<Book> books = new LinkedList<>();
            while (rs.next()) {
                Book book = new Book(
                        rs.getInt("isbn"),
                        rs.getString("name"),
                        rs.getString("author"),
                        rs.getString("image"),
                        rs.getInt("stock"));
                books.add(book);
            }
            return books;
        } catch (SQLException ex) {
            throw new BadRequestException("Cannot run SQL statement: " + ex.getSQLState());
        }
    }

    @Override
    public Book queryOne(String sql) {
        try {
            Statement stmt = dbrepo.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                return new Book(
                        rs.getInt("isbn"),
                        rs.getString("name"),
                        rs.getString("author"),
                        rs.getString("image"),
                        rs.getInt("stock"));
            }
        } catch (SQLException ex) {
            throw new BadRequestException("Cannot run SQL statement: " + ex.getMessage());
        }
        return null;
    }
}
