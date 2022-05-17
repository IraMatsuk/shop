<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ctg" uri="custom_tags" %>
<c:set var="absolutePath">${pageContext.request.contextPath}</c:set>
<c:choose>
    <c:when test="${not empty sessionScope.language}"> <fmt:setLocale value="${sessionScope.language}" scope="session"/></c:when>
    <c:when test="${empty sessionScope.language}"> <fmt:setLocale value="${sessionScope.language = 'ru_RU'}" scope="session"/></c:when>
</c:choose>
<fmt:setBundle basename="context.language"/>
<jsp:useBean id="order_list" scope="request" type="java.util.List"/>
<html>
    <head>
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
        <script type="text/javascript" src="${absolutePath}/js/f5.js"></script>
        <title><fmt:message key="header.orders"/> </title>
    </head>
    <body>
        <div class="page">
            <header>
                <%@include file="../header/header.jsp"%>
            </header>
            <div class="container-fluid">
                <c:choose>
                    <c:when test="${sessionScope.user.role eq 'CLIENT'}">
                        <c:choose>
                            <c:when test="${order_list.isEmpty()}">
                                <h3 class="text-center" style="padding-top: 20px">
                                    <fmt:message key="order.empty_confirmed_order"/>
                                </h3>
                            </c:when>
                            <c:otherwise>
                                <h3 class="text-center">
                                    <fmt:message key="order.confirmed"/>
                                </h3>
                                <table class="table table-striped">
                                    <thead>
                                    <tr>
                                        <th scope="col"><fmt:message key="order.id"/></th>
                                        <th scope="col"><fmt:message key="order.date_state_change"/> </th>
                                        <th scope="col"><fmt:message key="order.state"/> </th>
                                        <th scope="col"><fmt:message key="postcard.product_price"/></th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach var="order" items="${order_list}">
                                        <tr>
                                            <td><c:out value="${order.orderId}"/></td>
                                            <td><c:out value="${order.orderDate.toLocalDate()} ${order.orderDate.toLocalTime()}"/></td>
                                            <td><c:out value="${order.orderState}"/></td>
                                            <td><c:out value="${order.totalCost}"/></td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </c:otherwise>
                        </c:choose>
                    </c:when>
                    <c:when test="${sessionScope.user.role eq 'ADMIN'}">
                        <c:choose>
                            <c:when test="${order_list.isEmpty()}">
                                <h3 class="text-center"><fmt:message key="order.empty_confirmed_order"/> </h3>
                            </c:when>
                            <c:otherwise>
                                <h3 class="text-center"><fmt:message key="order.confirmed"/> </h3>
                                <div class="row">
                                    <div class="col" >
                                        <form name="delete_orders" action="${absolutePath}/controller" method="post">
                                            <input type="hidden" name="command" value="delete_orders">
                                            <button type="submit" class="btn btn-danger" ><fmt:message key="action.delete_old_orders"/> </button>
                                        </form>
                                    </div>
                                </div>

                                <table class="table table-striped">
                                    <thead>
                                    <tr>
                                        <th scope="col" class="col"><fmt:message key="order.id"/></th>
                                        <th scope="col" class="col"><fmt:message key="order.date_state_change"/> </th>
                                        <th scope="col" class="col"><fmt:message key="order.state"/> </th>
                                        <th scope="col" class="col"><fmt:message key="postcard.product_price"/></th>
<%--                                        <th scope="col" class="col"><fmt:message key="order.payment"/></th>--%>
                                        <th scope="col" class="col"><fmt:message key="order.address"/></th>
<%--                                        <th scope="col" class="col"><fmt:message key="order.comment"/></th>--%>
                                        <th scope="col" class="col"><fmt:message key="registration.login"/></th>
                                        <th scope="col" class="col"><fmt:message key="registration.phone"/> </th>
<%--                                        <th scope="col" class="col-2"><fmt:message key="menu.products"/> </th--%>
                                        <th scope="col" class="col"><fmt:message key="admin.users_action"/></th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach var="orderItem" items="${order_list}">
                                        <tr>
                                            <td class="col"><c:out value="${orderItem.order.orderId}"/></td>
                                            <td><c:out value="${orderItem.order.orderDate.toLocalDate()} ${orderItem.order.orderDate.toLocalTime()}"/></td>
                                            <td class="col"><c:out value="${orderItem.order.orderState}"/></td>
                                            <td class="col"><c:out value="${orderItem.order.totalCost}"/></td>
<%--                                            <td class="col"><c:out value="${orderItem.order.typePayment}"/> </td>--%>
                                            <td class="col"><c:out value="${orderItem.order.address}"/></td>
<%--                                            <td class="col"><c:out value="${orderItem.order.userComment}"/></td>--%>
                                            <td class="col"><c:out value="${orderItem.user.login}"/> </td>
                                            <td class="col"><c:out value="${orderItem.user.phoneNumber}"/> </td>
                                            <td class="col-2">
                                                <c:forEach var="item" items="${orderItem.menuList}">
                                                    <p style="margin-top: 0; margin-bottom: 0"><c:out value="${item.nameFood}"/> - <c:out value="${item.amount}"/></p>
                                                </c:forEach>
                                            </td>
                                            <td class="col">
                                                <div class="btn-group">
                                                    <button type="button" class="btn btn-danger dropdown-toggle btn-sm" data-bs-toggle="dropdown" aria-expanded="false">
                                                        <fmt:message key="order.change_state"/>
                                                    </button>
                                                    <ul class="dropdown-menu">
                                                        <li><a class="dropdown-item" href="${absolutePath}/controller?command=change_order_state&state=PROCESSING&id=${orderItem.order.orderId}">created</a></li>
                                                        <li><a class="dropdown-item" href="${absolutePath}/controller?command=change_order_state&state=COMPLETED&id=${orderItem.order.orderId}">payed</a></li>
                                                        <li><a class="dropdown-item" href="${absolutePath}/controller?command=change_order_state&state=CANCELLED&id=${orderItem.order.orderId}">cancelled</a></li>
                                                        <li><a class="dropdown-item" href="${absolutePath}/controller?command=change_order_state&state=RECEIVED&id=${orderItem.order.orderId}">processing</a></li>
                                                        <li><a class="dropdown-item" href="${absolutePath}/controller?command=change_order_state&state=RECEIVED&id=${orderItem.order.orderId}">sent</a></li>
                                                        <li><a class="dropdown-item" href="${absolutePath}/controller?command=change_order_state&state=CANCELLED&id=${orderItem.order.orderId}">completed</a></li>
                                                    </ul>
                                                </div>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </c:otherwise>
                        </c:choose>
                    </c:when>
                </c:choose>
            </div>
            <div class="text-center">
                <ctg:footertag/>
            </div>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.10.2/dist/umd/popper.min.js" integrity="sha384-7+zCNj/IqJ95wo16oMtfsKbZ9ccEh31eOz1HGyDuCQ6wgnyJNSYdrPa03rtR1zdB" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
    </body>
</html>
