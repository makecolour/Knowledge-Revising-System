<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Upload Excel</title>
</head>
<body>
<div>
    <!-- Display success message if present -->
    <div th:if="${success}" class="alert alert-success">
        <p th:text="${success}"></p>
    </div>

    <!-- Display error message if present -->
    <div th:if="${error}" class="alert alert-danger">
        <p th:text="${error}"></p>
    </div>

    <!-- Upload form -->
    <form th:action="@{/uploadExcel}" method="post" enctype="multipart/form-data">
        <input type="file" name="file">
        <button type="submit">Upload</button>
    </form>
</div>
</body>
</html>