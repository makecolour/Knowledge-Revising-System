<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<form action="/exam=${exam.id}/submit" method="post">
    <c:forEach items="${attempt.attemptHistories}" var="attemptHistory" varStatus="status">
        <h3>Question ${attemptHistory.question.id}: ${attemptHistory.question.content}</h3>
        <c:forEach items="${attemptHistory.question.answers}" var="answer" varStatus="optionStatus">
            <input type="checkbox" id="${answer.id}" name="${attemptHistory.question.id}" value="${answer.id}">
            <label for="${answer.id}">${answer.content}</label><br>
        </c:forEach>
    </c:forEach>
    <input type="submit" value="Submit">
</form>