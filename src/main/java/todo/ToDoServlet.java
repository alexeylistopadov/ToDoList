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

    @Override
    public void init() throws ServletException {
        points = new ArrayList<ToDoPoint>();
        points.add(new ToDoPoint("content", true, true));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        PrintWriter out = resp.getWriter();
        out.print(mapper.writeValueAsString(points));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        points.add(mapper.reader().forType(ToDoPoint.class).<ToDoPoint>readValue(req.getReader().readLine()));
    }
}

