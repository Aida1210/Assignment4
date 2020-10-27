package controllers;

import models.User;
import services.UserService;
import services.implementations.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@WebServlet(name = "controllers.Profile")
public class Profile extends HttpServlet {
    private final UserService userService=new UserServiceImpl();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cookie[] cookies =request.getCookies();
        //Displaying User name value from cookie
        for (Cookie cookie:cookies){
            if(cookie.getName().equals("username")){
                cookie.setMaxAge(0);
                cookie.setValue("");
                response.addCookie(cookie);
            }
            if(cookie.getName().equals("role")){
                cookie.setMaxAge(0);
                cookie.setValue("");
                response.addCookie(cookie);
            }
        }
        response.sendRedirect(request.getContextPath()+"/login");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean userExistence=false;
        String username=null;
        Cookie[] cookies =request.getCookies();
        //Displaying User name value from cookie
        for (Cookie cookie:cookies){
            if(cookie.getName().equals("username")){
              request.setAttribute("username",cookie.getValue());
              username=cookie.getValue();
              userExistence=true;
              break;
            }
        }
        if(userExistence){
            User user=userService.getUserByUsername(username);
            Set<String> userInfo=new HashSet<>();
            userInfo.add(user.getName());
            userInfo.add(user.getSurname());
            userInfo.add(user.getRole());
            request.setAttribute("info",userInfo);
            request.getRequestDispatcher("/profile.jsp").forward(request,response);
        }else {
            response.sendRedirect(request.getContextPath()+"/login");
        }
    }
}
