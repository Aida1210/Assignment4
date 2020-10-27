package controllers;//import services.implementations.UserServiceImpl;
//import services.UserService;

import interfaces.BookRepository;
import models.Book;
import models.Product;
import repository.BookRepositoryImpl;
import services.ProductService;
import services.implementations.ProductServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@WebServlet(name = "controllers.Main")
public class Main extends HttpServlet {
    public static int iHitCounter;
    private final BookRepository bookRepository=new BookRepositoryImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("reqValue"));
//        if (productService.getProductById(id) != null) {
//            productService.deleteFood(id);
//        }
        response.sendRedirect(request.getContextPath() + "/main");

    }

    public void init() throws ServletException {
        iHitCounter = 0;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Book> bookList=bookRepository.getAllBooks();
        bookRepository.removeReader(bookList.get(0));
//        if (request.getParameter("sort_by") != null)
//            Collections.sort(products);
        request.setAttribute("books",bookList);
        request.getRequestDispatcher("/main.jsp").forward(request, response);
    }
}
