import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import todo.services.TodoService;
import todo.services.TodoDAO;
import todo.TodoServlet;


public class Main {
    public static void main(String[] args) throws Exception {
        Server server = new Server(8080 );

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        TodoDAO todoDAO = new TodoDAO();
        todoDAO.connect();

        TodoService todoService = new TodoService(todoDAO);

        ServletHolder todoServletHolder = new ServletHolder(new TodoServlet(todoService));
        context.addServlet(todoServletHolder,"/api/todos/*");

        server.start();
        server.join();
    }
}