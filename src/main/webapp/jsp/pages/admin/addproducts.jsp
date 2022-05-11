<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ctg" uri="custom_tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="absolutePath">${pageContext.request.contextPath}</c:set>
<c:choose>
  <c:when test="${not empty language}"> <fmt:setLocale value="${language}" scope="session"/></c:when>
  <c:when test="${empty language}"> <fmt:setLocale value="${language = 'ru_RU'}" scope="session"/></c:when>
</c:choose>
<fmt:setBundle basename="context.language"/>
<html>
<head>
  <title><fmt:message key="postcard.title"/> </title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="/css/style.css">
  <!-- Bootstrap CSS -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
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
  <div class="container justify-content-center">
    <h3 class="text-center p-3"><fmt:message key="postcard.new_product"/></h3>
    </br>
    <form name="AddProductFord" method="post" action="${absolutePath}/controller"  novalidate>
      <input type="hidden" name="command" value="insert_new_product"/>
      </br>
      <div class="form-group" class="mb-3">
        <label class="form-label"><fmt:message key="postcard.product_name"/> </label>
        <input type="text" name="product_name" value="${param.product_name}" class="form-control" required pattern="^[A-Za-zА-Яа-я\s]{3,50}$">
        <c:choose>
          <c:when test="${!empty invalid_product_name}">
            <div class="invalid-feedback-backend" style="color: red">
              <fmt:message key="${invalid_product_name}"/>
            </div>
            <div class="invalid-feedback">
              <fmt:message key="postcard.invalid_product_name"/>
            </div>
          </c:when>
          <c:when test="${!empty not_uniq_product_name}">
            <div class="invalid-feedback-backend" style="color: red">
              <fmt:message key="${not_uniq_product_name}"/>
            </div>
            <div class="invalid-feedback">
              <fmt:message key="postcard.not_uniq_product_name"/>
            </div>
          </c:when>
        </c:choose>
      </div>
      </br>
      <div class="form-group" class="mb-3">
        <label class="form-label"><fmt:message key="postcard.product_author"/></label>
        <input type="text" name="product_author" value="${fn:escapeXml(param.product_author)}" class="form-control" required pattern="^[A-Za-zА-Яа-я\s]{1,50}$">
        <c:if test="${!empty invalid_product_author}">
          <div class="invalid-feedback-backend" style="color: red">
            <fmt:message key="${invalid_product_author}"/>
          </div>
        </c:if>
        <div class="invalid-feedback">
          <fmt:message key="postcard.invalid_product_author"/>
        </div>
      </div>
      </br>
      <div class="form-group" class="mb-3">
        <label class="form-label"><fmt:message key="postcard.product_description"/></label>
        <input type="text" name="product_description" value="${fn:escapeXml(param.product_description)}" class="form-control" required pattern="^.{0,100}$">
        <c:if test="${!empty invalid_product_description}">
          <div class="invalid-feedback-backend" style="color: red">
            <fmt:message key="${invalid_product_description}"/>
          </div>
        </c:if>
        <div class="invalid-feedback">
          <fmt:message key="postcard.invalid_product_description"/>
        </div>
      </div>
      </br>


      <div class="form-group" class="mb-3">
        <label class="form-label"><fmt:message key="postcard.product_discount"/></label>
        <input type="text" name="product_discount" value="${param.product_discount}" class="form-control" required pattern="\d{1,6}(\.[0-9]{1,2})?">
        <div id="discountHelp" class="form-text"><fmt:message key="postcard_discount_pattern"></fmt:message></div>
        <c:if test="${!empty invalid_product_discount}">
          <div class="invalid-feedback-backend" style="color: red">
            <fmt:message key="${invalid_product_discount}"/>
          </div>
        </c:if>
        <div class="invalid-feedback">
          <fmt:message key="postcard.invalid_product_discount"/>
        </div>
      </div>
      </br>
      <div class="form-group" class="mb-3">
        <label class="form-label"><fmt:message key="postcard.product_cost"/></label>
        <input type="text" name="product_price" value="${param.product_price}" class="form-control" required pattern="\d{1,6}(\.[0-9]{1,2})?">
        <div id="costHelp" class="form-text"><fmt:message key="postcard.cost_pattern"></fmt:message></div>
        <c:if test="${!empty invalid_product_price}">
          <div class="invalid-feedback-backend" style="color: red">
            <fmt:message key="${invalid_product_price}"/>
          </div>
        </c:if>
        <div class="invalid-feedback">
          <fmt:message key="postcard.invalid_product_price"/>
        </div>
      </div>
      </br>
      <select class="form-select" aria-label="Default select example" name="product_section">
        <option selected disabled><fmt:message key="postcard.product_section"/></option>
        <c:forEach var="item" items="${applicationScope.section_list}">
          <option value="${item.sectionId}">${item.sectionName}</option>
        </c:forEach>
      </select>
      <c:if test="${!empty invalid_product_section}">
        <div class="invalid-feedback-backend" style="color: red">
          <fmt:message key="${invalid_product_section}"/>
        </div>
      </c:if>
      <div class="invalid-feedback">
        <fmt:message key="postcard.invalid_product_section"/>
      </div>
      </br>
      <div class="text-center mb-3">
        <button type="submit" class="btn btn-primary"><fmt:message key="postcard.insert_postcard"/> </button>
      </div>
    </form>
  </div>

  <div class="text-center">
    <ctg:footertag/>
  </div>
</div>
<script>
  (function () {
    'use strict'
    var forms = document.querySelectorAll('.needs-validation')
    Array.prototype.slice.call(forms)
            .forEach(function (form) {
              form.addEventListener('submit', function (event) {
                if (!form.checkValidity()) {
                  event.preventDefault()
                  event.stopPropagation()
                }
                form.classList.add('was-validated')
              }, false)
            })
  })()
</script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.10.2/dist/umd/popper.min.js" integrity="sha384-7+zCNj/IqJ95wo16oMtfsKbZ9ccEh31eOz1HGyDuCQ6wgnyJNSYdrPa03rtR1zdB" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
</body>
</html>
