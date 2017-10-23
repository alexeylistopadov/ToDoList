package main.java;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.*;
import javax.servlet.http.*;

public class ToDoServlet extends HttpServlet {
    private List<String> points;

    @Override
    public void init() throws ServletException {
        points = new ArrayList<String>();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("points",points);
        req.getRequestDispatcher("/index.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        points.add(req.getParameter("point"));
        resp.sendRedirect("/todo");
    }

}

