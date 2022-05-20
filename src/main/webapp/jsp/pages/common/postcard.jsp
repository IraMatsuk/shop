<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="ctg" uri="custom_tags" %>
<c:set var="absolutePath">${pageContext.request.contextPath}</c:set>
<c:choose>
    <c:when test="${not empty language}"><fmt:setLocale value="${language}" scope="session"/></c:when>
    <c:when test="${empty language}"><fmt:setLocale value="${language = 'ru_RU'}" scope="session"/></c:when>
</c:choose>
<fmt:setBundle basename="context.language"/>
<fmt:message key="postcard.title" var="catalog"/>
<html>
    <head>
        <title>${catalog}</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="${absolutePath}/css/style.css">
        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script type="text/javascript">
            window.history.forward();
            function noBack() {
                window.history.forward();
            }
        </script>
        <script src="${absolutePath}/js/counter.js"></script>
    </head>
    <body class="body">
        <div class="page">
            <header>
                <%@include file="../header/header.jsp"%>
            </header>
            <div class="container">
                <div class="row">
                    <div class="col-2 text-center">
                        <fmt:message key="action.sort"/>
                    </div>
                    <div class="col-auto">
                        <form name="sortByPrice" action="${absolutePath}/controller">
                            <input type="hidden" name="command" value="sort_all_postcards_by_price" >
                            <c:if test="${not empty param.section_id}">
                                <input type="hidden" name="section_id" value="${param.section_id}">
                            </c:if>
                            <button type="submit" class="btn btn-warning btn-sm"">
                                <fmt:message key="postcard.sort_by_price"/>
                            </button>
                        </form>
                    </div>
                    <div class="col-auto">
                        <form name="sortByPopularity" action="${absolutePath}/controller">
                            <input type="hidden" name="command" value="sort_all_postcards_by_popularity">
                            <c:if test="${not empty param.section_id}">
                                <input type="hidden" name="section_id" value="${param.section_id}">
                            </c:if>
                            <button type="submit" class="btn btn-danger btn-sm">
                                <fmt:message key="postcard.sort_by_popularity"/>
                            </button>
                        </form>
                    </div>
                </div>
            </div>
            <div class="box box_padding catalog-wrapp catalog-body">
                <div class="catalog">

                    <c:choose>
                        <c:when test="${empty requestScope.postcard_list}">
                            <h3 class="text-center">
                                <fmt:message key="postcard.empty"/>
                            </h3>
                        </c:when>
                        <c:otherwise>
                            <c:forEach items="${requestScope.postcard_list}" var="postcard">
                                <div class="catalog-item">
                                    <div class="product">
                                        <div class="product_header">
                                            <div class="product_title">${postcard.postcardName}</div>
                                        </div>
                                        <div class="product_figure">
                                            <c:choose>
                                                <c:when test="${postcard.picturePath eq 'picture/default_image.png'}">
                                                    <img src="${postcard.picturePath}" alt="" class="product_img">
                                                </c:when>
                                                <c:otherwise>
                                                    <img src="${postcard.picturePath}" alt="" class="product_img">
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                        <div class="product_info">
                                            <fmt:message key="postcard.product_author"/>
                                            <c:out value="${postcard.postcardAuthor}"/>
                                        </div>
                                        <c:choose>
                                            <c:when test="${not empty postcard.description}">
                                                <div class="product_consist mb-2"><fmt:message key="postcard.product_description"/>
                                                    <c:out value="${postcard.description}"/>
                                                    <br><br><br>
                                                </div>
                                            </c:when>
                                            <c:otherwise>
                                                <div class="product_consist mb-2">
                                                    <br><br><br>
                                                </div>
                                            </c:otherwise>
                                        </c:choose>

                                        <div class="">
                                            <div class="product_price "><b id="price"><c:out value="${postcard.price}"/> </b>
                                                <fmt:message key="postcard.product_money"/>
                                            </div>
                                            <div class="product_price" ><b id="discount">
                                                <fmt:message key="postcard.product_discount"/></b>
                                                <fmt:formatNumber type="number"  maxFractionDigits="0" value="${postcard.discount}"/>%
                                            </div>
                                            <div class="product_price"><b id="total_price">
                                                <fmt:message key="postcard.product_price"/></b>
                                                <fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2"  value="${postcard.price - postcard.discount / 100  * postcard.price}"/>   </div>
                                            <c:if test="${user.role eq 'CLIENT'}">
                                                <form action="${absolutePath}/controller" method="post">
                                                    <input type="hidden" name="command" value="add_product_to_cart">
                                                    <input type="hidden" name="selected" value="${postcard.postcardId}">
                                                    <div class="product_actions">
                                                        <div class="counter">
                                                            <div class="counter_btn counter_btn_minus btn-secondary">-</div>
                                                            <input  type="text"  class="counter_number" max="100" id="product_number" name="product_number" value="1">
                                                            <div class="counter_btn counter_btn_plus btn-secondary">+</div>
                                                        </div>
                                                        <button type="submit" class="btn btn-primary js_add-to-cart">
                                                            <fmt:message key="action.to_cart"/>
                                                        </button>
                                                    </div>
                                                </form>
                                            </c:if>
                                            <c:if test="${user.role eq 'ADMIN'}">
                                                <form name="uploadPhoto" method="post" action="${absolutePath}/controller" enctype="multipart/form-data">
                                                    <input type="hidden" name="command" value="upload_product_photo">
                                                    <input type="hidden" name="product_name" value="${postcard.postcardName}">
                                                    </br>
                                                    <div class="form-group mb-2">
                                                        <label class="form-label">
                                                            <fmt:message key="postcard.picture"/>
                                                        </label>
                                                        <input type="file" name="picture_path" class="form-control form-control-sm">
                                                    </div>
                                                    <button type="submit" class="btn btn-primary btn-sm mb-2">
                                                        <fmt:message key="postcard.insert_postcard"/>
                                                    </button>
                                                </form>
                                                <div>
                                                    <div class="row">
                                                        <div class="col">
                                                            <a class="btn btn-info btn-sm" href="${absolutePath}/controller?command=go_to_update_product_page&id=<c:out value="${postcard.postcardId}"/>" role="button">
                                                                <fmt:message key="profile.update"/>
                                                            </a>
                                                        </div>
                                                        <div class="col">
                                                            <form action="${absolutePath}/controller" method="post">
                                                                <input type="hidden" name="command" value="delete_product">
                                                                <input type="hidden" name="id" value="${postcard.postcardId}">
                                                                <button type="submit" class="btn btn-danger btn-sm">
                                                                    <fmt:message key="action.delete"/>
                                                                </button>
                                                            </form>
                                                        </div>
                                                    </div>
                                                </div>
                                            </c:if>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                </div>
                </form>
            </div>
        </div>
        <div class="pages">
            <div class="justify-content-center" >
                <ctg:pagination currentPage="${requestScope.currentPage}" lastPage="${requestScope.lastPage}" url="${requestScope.command_url}"/>
            </div>
        </div>
        <div class="text-center">
            <ctg:footertag/>
        </div>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.10.2/dist/umd/popper.min.js" integrity="sha384-7+zCNj/IqJ95wo16oMtfsKbZ9ccEh31eOz1HGyDuCQ6wgnyJNSYdrPa03rtR1zdB" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
    </body>
</html>