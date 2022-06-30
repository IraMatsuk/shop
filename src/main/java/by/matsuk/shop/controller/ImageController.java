package by.matsuk.shop.controller;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@WebServlet(name = "imageController", urlPatterns = {"/uploadImage"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 10,
        maxRequestSize = 1024 * 1024 * 5 * 5)
public class ImageController extends HttpServlet {
    private static final String CONTENT_TYPE = "image/jpeg";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws  IOException {
        String path = request.getParameter(Parameter.IMAGE_PATH);
        byte[] imageBytes = Files.readAllBytes(Paths.get(path));
        response.setContentType(CONTENT_TYPE);
        response.setContentLength(imageBytes.length);
        response.getOutputStream().write(imageBytes);
    }
}
