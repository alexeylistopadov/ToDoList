package todo;

import com.fasterxml.jackson.databind.ObjectMapper;
import todo.services.TodoService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
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
            try {
                doPatch(req, resp);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            super.service(req,resp);
        }
    }

    protected void doPatch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, IllegalAccessException, SQLException {
        if (req.getPathInfo() == null) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        TodoPoint point = mapper.readValue(req.getInputStream(), TodoPoint.class);
        todoService.patchPoint(Long.parseLong(req.getPathInfo().substring(1)), point);
        resp.setStatus(HttpServletResponse.SC_OK);
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType(APPLICATION_JSON);
        if (req.getPathInfo() == null) {
            try {
                mapper.writeValue(resp.getWriter(), todoService.getPoints());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return;
        }

        try {
            TodoPoint todoPoint = todoService.getPointById(Long.parseLong(req.getPathInfo().substring(1)));
            mapper.writeValue(resp.getWriter(), todoPoint);
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TodoPoint point = mapper.readValue(req.getInputStream(), TodoPoint.class);
        try {
            todoService.addPoint(point);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            todoService.updatePoint(mapper.readValue(req.getInputStream(), TodoPoint.class));
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}

