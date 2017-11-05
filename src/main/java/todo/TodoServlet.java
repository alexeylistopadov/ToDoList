package todo;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.jws.WebService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@WebServlet(name = "todos", urlPatterns = {"/api/todos/*"})
public class TodoServlet extends HttpServlet {
    public static final String APPLICATION_JSON = "application/json";

    private ObjectMapper mapper = new ObjectMapper();
    private TodoService todoService = new TodoService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType(APPLICATION_JSON);
        mapper.writeValue(resp.getWriter(), todoService.getPoints());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType(APPLICATION_JSON);
        TodoPoint point = mapper.readValue(req.getInputStream(), TodoPoint.class);
        todoService.addPoint(point);
        mapper.writeValue(resp.getWriter(), point);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getPathInfo() == null){
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        try {
            todoService.deletePoint(Long.parseLong(req.getPathInfo().substring(1)));
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        }catch (NumberFormatException e){
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}

