package todo.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import todo.TodoPoint;

import java.io.IOException;
import java.io.StringWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TodoDAO {

    private final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private final String URL = "jdbc:mysql://localhost:3306/todo";

    private final String USER = "root";
    private final String PASS = "root";

    private Connection connection;
    private ObjectMapper mapper = new ObjectMapper();

    public void connect() throws ClassNotFoundException, SQLException {
        Class.forName(JDBC_DRIVER);
        connection = DriverManager.getConnection(URL, USER, PASS);
    }

    public void addPoint(TodoPoint todoPoint) throws SQLException, IOException {
        String query = "insert into todo_point (content, tags) value(?,?);";
        PreparedStatement statement = connection.prepareStatement(query);
        StringWriter sw = new StringWriter();
        mapper.writeValue(sw, todoPoint.getTags());
        statement.setString(1, todoPoint.getContent());
        statement.setString(2, sw.toString());
        statement.executeUpdate();
    }

    public TodoPoint getPoint(Long id) throws SQLException, IOException {
        String query = "select * from todo_point where id=?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setLong(1, id);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            return parse(resultSet);
        }
        return null;
    }

    public List<TodoPoint> getPoints() throws SQLException, IOException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from todo_point");
        List<TodoPoint> todoPoints = new ArrayList<TodoPoint>();
        while (resultSet.next()) {
            todoPoints.add(parse(resultSet));
        }
        return todoPoints;
    }

    public void updatePoint(TodoPoint todoPoint) throws SQLException, IOException {
        String query = "update todo_point set content=?, tags=? where id=?";
        PreparedStatement statement = connection.prepareStatement(query);
        StringWriter sw = new StringWriter();
        mapper.writeValue(sw, todoPoint.getTags());
        statement.setString(1, todoPoint.getContent());
        statement.setString(2, sw.toString());
        statement.setLong(3, todoPoint.getId());
        statement.executeUpdate();
    }

    public void patchPoint(Long id, TodoPoint todoPoint) throws SQLException {
        String query = "update todo_point set content=?, tags=? where id=?";
        PreparedStatement statement = connection.prepareStatement(query);
        StringWriter sw = new StringWriter();
        if (todoPoint.getContent() != null) {
            statement.setString(1, todoPoint.getContent());
        }

        try {
            mapper.writeValue(sw, todoPoint.getTags());
            statement.setString(2, sw.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        statement.setLong(3, id);
        statement.executeUpdate();
    }

    public void deletePoint (Long id) throws SQLException {
        String query = "delete from todo_point where id=?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setLong(1,id);
        statement.execute();
    }

    private TodoPoint parse(ResultSet resultSet) throws SQLException {
        TodoPoint todoPoint = new TodoPoint();
        todoPoint.setId(resultSet.getLong("id"));
        todoPoint.setContent(resultSet.getString("content"));
        try {
            todoPoint.setTags(mapper.readValue(resultSet.getString("tags"), ArrayList.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return todoPoint;
    }

}
