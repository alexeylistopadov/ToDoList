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
    private List<ToDoPoint> points;
    private ObjectMapper mapper;

    @Override
    public void init() throws ServletException {
        points = new ArrayList<ToDoPoint>();
        mapper = new ObjectMapper();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        out.print(mapper.writeValueAsString(points));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        points.add(mapper.reader().forType(ToDoPoint.class).<ToDoPoint>readValue(req.getReader().readLine()));
    }
}

