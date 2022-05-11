package by.matsuk.shop.controller.listener;

import by.matsuk.shop.model.connection.ConnectionPool;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;

@WebListener
public class ContextListener implements ServletContextListener {

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        ConnectionPool.getInstance().destroyPool();
    }
}