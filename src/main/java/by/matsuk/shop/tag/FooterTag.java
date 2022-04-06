package by.matsuk.shop.tag;

import jakarta.servlet.jsp.JspTagException;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.TagSupport;

import java.io.IOException;

public class FooterTag extends TagSupport {
    @Override
    public int doStartTag() throws JspTagException {
        try{
            JspWriter out = pageContext.getOut();
            String tagText = "<footer><p class=\"footer\">© 2021-2022 Copyright by Anna Merkul</p></footer>";
            out.write(tagText);
        } catch (IOException e) {
            throw new JspTagException(e);
        }
        return SKIP_BODY;
    }

    @Override
    public int doEndTag(){
        return EVAL_PAGE;
    }
}
