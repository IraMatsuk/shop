<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="ctg" uri="custom_tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="absolutePath">${pageContext.request.contextPath}</c:set>
<c:choose>
  <c:when test="${not empty language}"> <fmt:setLocale value="${language}" scope="session"/></c:when>
  <c:when test="${empty language}"> <fmt:setLocale value="${language = 'ru_RU'}" scope="session"/></c:when>
</c:choose>
<fmt:setBundle basename="context.language"/>
<html>
  <head>
    <title><fmt:message key="profile.title"/> </title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${absolutePath}/css/style.css">
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <script type="text/javascript">
      window.history.forward();
      function noBack() {
        window.history.forward();
      }
    </script>
  </head>
  <body>
  <div class="page">
    <header>
      <%@include file="../header/header.jsp"%>
    </header>
    <div class="row">
      <div class="container col-12 col-sm-6 mt-3">
        <div class="row justify-content-center">
          <div class="col-8">
            <h3><c:out value="${user.firstName}"/> </h3>
            <h3><c:out value="${user.lastName}"/> </h3>
          </div>
        </div>
        <dl class="row my-3">
          <dt class="col-2">
            <fmt:message key="registration.email"/>
          </dt>
          <dd class="col-10">
            <p><c:out value="${user.email}"/> </p>
          </dd>
          <dt class="col-2">
            <fmt:message key="registration.phone"/>
          </dt>
          <dd class="col-10">
            <p><c:out value="${user.phoneNumber}"/></p>
          </dd>
        </dl>
      </div>
    </div>
    <div class="text-center">
      <ctg:footertag/>
    </div>
  </div>
  <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.10.2/dist/umd/popper.min.js" integrity="sha384-7+zCNj/IqJ95wo16oMtfsKbZ9ccEh31eOz1HGyDuCQ6wgnyJNSYdrPa03rtR1zdB" crossorigin="anonymous"></script>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
  </body>
</html>
