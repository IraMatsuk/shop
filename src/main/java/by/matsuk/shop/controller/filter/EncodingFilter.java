package by.matsuk.shop.controller.filter;

import jakarta.servlet.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * The type Encoding filter.
 */
public class EncodingFilter implements Filter {
    private static final Logger logger = LogManager.getLogger();
    private static final String CODE = "encoding";
    private String code;

    @Override
    public void init(FilterConfig config) {
        logger.info("Encoding filter: method - init");
        code = config.getInitParameter(CODE);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        String codeRequest = request.getCharacterEncoding();
        if(code != null && !code.equalsIgnoreCase(codeRequest)){
            request.setCharacterEncoding(code);
            response.setCharacterEncoding(code);
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        logger.info("Encoding filter: method - destroy");
        code = null;
    }
}
