package by.matsuk.shop.tag;

import jakarta.servlet.jsp.tagext.TagSupport;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PaginationTag extends TagSupport {
    private static final Logger LOGGER = LogManager.getLogger();
    private final int firstPage = 1;
}
