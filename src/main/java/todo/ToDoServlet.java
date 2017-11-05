package todo;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class ToDoServlet extends HttpServlet {
    public static final String APPLICATION_JSON = "application/json";

    private List<ToDoPoint> points = new ArrayList<>();
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType(APPLICATION_JSON);
        mapper.writeValue(resp.getWriter(), points);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType(APPLICATION_JSON);
        points.add(mapper.readValue(req.getInputStream(), ToDoPoint.class));
        mapper.writeValue(resp.getWriter(), points.get(points.size() - 1));
    }
}

