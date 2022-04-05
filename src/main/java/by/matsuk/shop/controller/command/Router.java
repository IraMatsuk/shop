package by.matsuk.shop.controller.command;

/**
 * @author Ira
 * @project Postcard shop
 * The type Router.
 */
public class Router {
    /**
     * The enum Router type.
     */
    public enum RouterType {
        /**
         * Forward router type.
         */
        FORWARD,
        /**
         * Redirect router type.
         */
        REDIRECT
    }

    private String page = PagePath.HOME;
    private RouterType routerType = RouterType.FORWARD;

    /**
     * Instantiates a new Router.
     *
     * @param page       the page
     * @param routerType the router type
     */
    public Router(String page, RouterType routerType) {
        if (page != null) {
            this.page = page;
        }
        if (routerType != null) {
            this.routerType = routerType;
        }
    }

    /**
     * Gets page.
     *
     * @return the page
     */
    public String getPage() {
        return page;
    }

    /**
     * Gets router type.
     *
     * @return the router type
     */
    public RouterType getRouterType() {
        return routerType;
    }
}
