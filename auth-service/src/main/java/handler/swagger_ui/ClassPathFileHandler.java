package handler.swagger_ui;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class ClassPathFileHandler implements HttpHandler {

    private final String resourceBase;

    public ClassPathFileHandler(String resourceBase) {
        // Example: "/swagger-ui"
        this.resourceBase = resourceBase;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            String requestPath = exchange.getRequestURI().getPath();

            // Remove the URL context path (first segment)
            String urlPrefix = exchange.getHttpContext().getPath(); // "/docs-ui"
            String relative = requestPath.substring(urlPrefix.length());

            if (relative.isEmpty() || relative.equals("/")) {
                relative = "/index.html";
            }

            String resourcePath = resourceBase + relative;

            InputStream is = getClass().getResourceAsStream(resourcePath);

            if (is == null) {
                exchange.sendResponseHeaders(404, -1);
                return;
            }

            byte[] bytes = is.readAllBytes();
            String contentType = URLConnection.guessContentTypeFromName(resourcePath);
            if (contentType == null) {
                contentType = "application/octet-stream";
            }

            exchange.getResponseHeaders().add("Content-Type", contentType);
            exchange.sendResponseHeaders(200, bytes.length);

            OutputStream os = exchange.getResponseBody();
            os.write(bytes);
            os.close();
            is.close();

        } catch (Exception e) {
            e.printStackTrace();
            exchange.sendResponseHeaders(500, -1);
        }
    }
}
