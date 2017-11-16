package todo;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Objects;

@WebServlet(name = "todos", urlPatterns = {"/api/todos/*"})
public class TodoServlet extends HttpServlet {

    public static final String APPLICATION_JSON = "application/json";

    private final ObjectMapper mapper = new ObjectMapper();
    private final TodoService todoService;

    public TodoServlet(TodoService todoService) {
        this.todoService = todoService;
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getMethod();

        if (Objects.equals(method, "PATCH")){
            doPatch(req, resp);
        } else {
            super.service(req,resp);
        }
    }

    protected void doPatch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getPathInfo() == null) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        try {
            TodoPoint point = mapper.readValue(req.getInputStream(), TodoPoint.class);
            todoService.patchPoint(Long.parseLong(req.getPathInfo().substring(1)), point);
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (IllegalStateException | IllegalAccessException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType(APPLICATION_JSON);
        if (req.getPathInfo() == null) {
            mapper.writeValue(resp.getWriter(), todoService.getPoints());
            return;
        }
        try {
            TodoPoint todoPoint = todoService.getPointById(Long.parseLong(req.getPathInfo().substring(1)));
            mapper.writeValue(resp.getWriter(), todoPoint);
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TodoPoint point = mapper.readValue(req.getInputStream(), TodoPoint.class);
        todoService.addPoint(point);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        todoService.updatePoint(mapper.readValue(req.getInputStream(), TodoPoint.class));
        resp.setStatus(HttpServletResponse.SC_ACCEPTED);
    }


    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getPathInfo() == null) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        try {
            todoService.deletePoint(Long.parseLong(req.getPathInfo().substring(1)));
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }


}

