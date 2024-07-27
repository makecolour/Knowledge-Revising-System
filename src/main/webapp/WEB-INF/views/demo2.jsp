<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Demo Page</title>
</head>
<body>
<h1>Success </h1>
<h1>"${subject.role.speciality}" </h1>

<%--<h1>"${a}" </h1>--%>
<%--<h1>Lesson Names</h1>--%>
<%--<c:forEach var="entry" items="${lessonNames}">--%>
<%--    <p>Key: ${entry.key} - Value: ${entry.value}</p>--%>
<%--</c:forEach>--%>

<h1>Lesson Status</h1>
<c:forEach var="entry" items="${list}">
    <p>Name: ${entry.name} - Status: ${entry.status} - Id: ${entry.id}</p>
</c:forEach>


</body>
</html>
