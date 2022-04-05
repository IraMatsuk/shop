<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <link rel="stylesheet" type="text/css" href="css/style.css" />
    <title>Login Page</title>
  </head>
  <body>
    <div class ="container">
      <section id="content">
        <p><font color="red">${errorMessage}</font></p>
        <form action="${pageContext.servletContext.contextPath}/controller?command=login" method="POST">
          <h1>Login to the system</h1>
          <div>
            <input placeholder="Login" required="" id ="login" name="loginName" type="text" />
          </div>
          <div>
            <input placeholder="Password" required="" id ="password" name="password" type="password" />
          </div>
          <div>
            <input type="submit" value="Sign in" />
          </div>
          <div>
            <a href="${pageContext.servletContext.contextPath}/controller?command=registration_page">Registration</a>
          </div>
        </form>
      </section>
    </div>
  </body>
</html>
