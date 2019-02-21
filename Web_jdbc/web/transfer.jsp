
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/TransferServlet" method="post">
    转出账户：<input type="text" name="out"><br>
    转入账户：<input type="text" name="in"><br>
    转账金额：<input type="text" name="money"><br>
    <input type="submit" value="确认">
</form>
</body>
</html>
