<%--
  Created by IntelliJ IDEA.
  User: quyen
  Date: 20/06/2024
  Time: 11:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Edit Exam</title>
</head>
<body>
<h1>Edit Exam</h1>
<form:form action="/exam/${exam.id}/edit" method="post" modelAttribute="exam">
    <form:label path="name">Name</form:label>
    <form:input path="name" />
    <form:label path="length">Length</form:label>
    <form:input path="length" />
    <form:label path="description">Description</form:label>
    <form:input path="description" />
    <!-- Add more fields as needed -->

    <h2>Lesson Exams</h2>
    <c:forEach items="${exam.lessonExams}" var="lessonExam" varStatus="status">
        <h3>Lesson Exam ${status.index + 1}</h3>
        <form:hidden path="lessonExams[${status.index}].id" value="${lessonExam.id}" />
        <form:label path="lessonExams[${status.index}].numberOfQuestions">Number of Questions</form:label>
        <form:input path="lessonExams[${status.index}].numberOfQuestions" value="${lessonExam.numberOfQuestions}" />
        <!-- Add more fields as needed -->
    </c:forEach>

    <input type="submit" value="Update Exam" />
</form:form>
</body>
</html>
</body>
</html>
