<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>EduChamp : Education HTML Template </title>

    <link rel="icon" href="../error-404.html" type="image/x-icon"/>
    <link rel="shortcut icon" type="image/x-icon" href="/assets/images/favicon.png"/>

    <link rel="stylesheet" type="text/css" href="/assets/css/assets.css">
    <link rel="stylesheet" type="text/css" href="/assets/vendors/calendar/fullcalendar.css">
    <link rel="stylesheet" type="text/css" href="/assets/css/typography.css">
    <link rel="stylesheet" type="text/css" href="/assets/css/shortcodes/shortcodes.css">
    <link rel="stylesheet" type="text/css" href="/assets/css/style.css">
    <link rel="stylesheet" type="text/css" href="/assets/css/dashboard.css">
    <link class="skin" rel="stylesheet" type="text/css" href="/assets/css/color/color-1.css">
</head>
<body class="ttr-opened-sidebar ttr-pinned-sidebar">

<jsp:include page="dashboard-header.jsp"></jsp:include>

<main class="ttr-wrapper">
    <div class="container-fluid">
        <div class="db-breadcrumb">
            <h4 class="breadcrumb-title">Dashboard</h4>
            <ul class="db-breadcrumb-list">
                <li><a href="#"><i class="fa fa-home"></i>Home</a></li>
                <li>Dashboard</li>
            </ul>
        </div>
        <div class="" style="display: flex; flex-direction: column">
            <div class="">
                <div class="heading-bx left">
                    <h2 class="title-head">Subject List</h2>
                </div>

                <div style="display: flex; flex-direction: row; justify-content: space-between">
                    <form action="${pageContext.request.contextPath}/subject/search" method="get" class="form-group col-10" id="searchForm">
                        <div class="d-flex flex-row align-items-center">
                            <div class="mr-2">
                                <input name="name" type="text" class="form-control" placeholder="Enter subject name or code" value="${sessionScope.name}">
                            </div>
                            <div class="mr-2">
                                <select name="speciality" class="form-control" onchange="submitForm()">
                                    <option value="">All</option>
                                    <c:forEach items="${specialitySet}" var="role">
                                        <option value="${role.id}" <c:if test="${role.id == param.speciality}">selected</c:if>>
                                                ${role.speciality}
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="mr-2">
                                <select name="status" class="form-control" onchange="submitForm()">
                                    <option value="" <c:if test="${empty param.status}">selected</c:if>>All</option>
                                    <option value="true" <c:if test="${'true' == param.status}">selected</c:if>>Active</option>
                                    <option value="false" <c:if test="${'false' == param.status}">selected</c:if>>Inactive</option>
                                </select>
                            </div>
                            <div class="mr-2">
                                <select name="manager" class="form-control" onchange="submitForm()">
                                    <option value="">All</option>
                                    <c:forEach items="${managerSet}" var="role">
                                        <option value="${role.id}" <c:if test="${role.id == param.manager}">selected</c:if>>
                                                ${role.fullName} - ${role.id}
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                    </form>

                    <div>
                        <a href="${pageContext.request.contextPath}/subject/adding" class="btn btn-secondary">Add</a>
                    </div>
                </div>

                <div class="table-responsive">
                    <table class="table table-striped">
                        <thead>
                        <tr>
                            <th>Id
<%--                                <a href="${pageContext.request.contextPath}/subject/search?name=${param.name}&speciality=${param.speciality}&manager=${param.manager}&status=${param.status}&order=asc&column=subject.id">↑</a>--%>
<%--                                <a href="${pageContext.request.contextPath}/subject/search?name=${param.name}&speciality=${param.speciality}&manager=${param.manager}&status=${param.status}&order=desc&column=subject.id">↓</a>--%>
                            </th>
                            <th>Name
<%--                                <a href="${pageContext.request.contextPath}/subject/search?name=${param.name}&speciality=${param.speciality}&manager=${param.manager}&status=${param.status}&order=asc&column=subject.name">↑</a>--%>
<%--                                <a href="${pageContext.request.contextPath}/subject/search?name=${param.name}&speciality=${param.speciality}&manager=${param.manager}&status=${param.status}&order=desc&column=subject.name">↓</a>--%>
                            </th>
                            <th>Code
<%--                                <a href="${pageContext.request.contextPath}/subject/search?name=${param.name}&speciality=${param.speciality}&manager=${param.manager}&status=${param.status}&order=asc&column=subject.code">↑</a>--%>
<%--                                <a href="${pageContext.request.contextPath}/subject/search?name=${param.name}&speciality=${param.speciality}&manager=${param.manager}&status=${param.status}&order=desc&column=subject.code">↓</a>--%>
                            </th>
                            <th>Speciality
<%--                                <a href="${pageContext.request.contextPath}/subject/search?name=${param.name}&speciality=${param.speciality}&manager=${param.manager}&status=${param.status}&order=asc&column=speciality">↑</a>--%>
<%--                                <a href="${pageContext.request.contextPath}/subject/search?name=${param.name}&speciality=${param.speciality}&manager=${param.manager}&status=${param.status}&order=desc&column=speciality">↓</a>--%>
                            </th>
                            <th>Manager
<%--                                <a href="${pageContext.request.contextPath}/subject/search?name=${param.name}&speciality=${param.speciality}&manager=${param.manager}&status=${param.status}&order=asc&column=manager">↑</a>--%>
<%--                                <a href="${pageContext.request.contextPath}/subject/search?name=${param.name}&speciality=${param.speciality}&manager=${param.manager}&status=${param.status}&order=desc&column=manager">↓</a>--%>
                            </th>
                            <th>Status
<%--                                <a href="${pageContext.request.contextPath}/subject/search?name=${param.name}&speciality=${param.speciality}&manager=${param.manager}&status=${param.status}&order=asc&column=subject.status">↑</a>--%>
<%--                                <a href="${pageContext.request.contextPath}/subject/search?name=${param.name}&speciality=${param.speciality}&manager=${param.manager}&status=${param.status}&order=desc&column=subject.status">↓</a>--%>
                            </th>
                            <th>Action</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:if test="${empty subjectList}">
                            <div class="alert alert-warning" role="alert">No course found</div>
                        </c:if>
                        <c:forEach var="list" items="${subjectList}">
                            <tr>
                                <td>${list.id}</td>
                                <td>${list.name}</td>
                                <td>
                                    <a href="<c:url value='/lessons-list/${list.id}' />">${list.code}</a>
                                </td>
                                <td>${list.role.speciality}</td>
                                <td><c:forEach var="manager" items="${list.subjectManager}">
                                    <div style="display: flex; flex-direction: column">
                                        <a>
                                                ${manager.user.fullName}
                                        </a>
                                    </div>


                                </c:forEach> </td>
                                <td>${list.status ? "Active" : "Inactive"}</td>
                                <td>
                                    <form action="/subject/${list.id}" method="get" style="display: inline;">
                                        <button type="submit" class="btn btn-danger">Edit</button>
                                    </form>

                                    <form id="statusForm-${list.id}"  action="/subject/edit2/${list.id}/page=${currentPage}/size=${totalPages}"  method="post" style="display: inline;">
                                        <button type="button" class="btn ${list.status ? 'btn-danger' : 'btn-success'}" data-toggle="modal" data-target="#statusModal-${list.id}">
                                                ${list.status ? "Inactive" : "Active"}
                                        </button>
                                    </form>


                                </td>
                            </tr>

                            <!-- Modal -->
                            <div class="modal fade" id="statusModal-${list.id}" tabindex="-1" role="dialog" aria-labelledby="statusModalLabel-${list.id}" aria-hidden="true">
                                <div class="modal-dialog" role="document">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <h5 class="modal-title" id="statusModalLabel-${list.id}">Confirm Change Status</h5>
                                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                <span aria-hidden="true">&times;</span>
                                            </button>
                                        </div>
                                        <div class="modal-body">
                                            Are you sure you want to change the status of the course: ${list.name}?
                                        </div>
                                        <div class="modal-footer">
                                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                                            <button type="submit" form="statusForm-${list.id}" class="btn ${list.status ? 'btn-danger' : 'btn-success'}">${list.status ? "Inactive" : "Active"}</button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                        </tbody>
                    </table>
                    <div aria-label="Page navigation">
                        <ul class="pagination">
                            <c:if test="${currentPage > 0}">
                                <li class="page-item">
                                    <a class="page-link"
                                       href="${pageContext.request.contextPath}/subject/search?name=${param.name}&speciality=${param.speciality}&status=${param.status}&manager=${param.manager}&page=${currentPage-1}&size=${9}">Previous</a>
                                </li>
                            </c:if>
                            <c:if test="${totalPages > 0}">
                                <c:forEach var="i" begin="0" end="${totalPages - 1}">
                                    <li class="page-item ${i == currentPage ? 'active' : ''}">
                                        <a class="page-link"
                                           href="${pageContext.request.contextPath}/subject/search?name=${param.name}&speciality=${param.speciality}&status=${param.status}&manager=${param.manager}&page=${i}&size=${9}">${i + 1}</a>
                                    </li>
                                </c:forEach>
                            </c:if>
                            <c:if test="${currentPage < totalPages - 1}">
                                <li class="page-item">
                                    <a class="page-link"
                                       href="${pageContext.request.contextPath}/subject/search?name=${param.name}&speciality=${param.speciality}&status=${param.status}&manager=${param.manager}&page=${currentPage+1}&size=${9}">Next</a>
                                </li>
                            </c:if>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
</main>

<div class="ttr-overlay"></div>

<script src="/assets/js/jquery.min.js"></script>
<script src="/assets/vendors/bootstrap/js/popper.min.js"></script>
<script src="/assets/vendors/bootstrap/js/bootstrap.min.js"></script>
<script src="/assets/vendors/bootstrap-select/bootstrap-select.min.js"></script>
<script src="/assets/vendors/bootstrap-touchspin/jquery.bootstrap-touchspin.js"></script>
<script src="/assets/vendors/magnific-popup/magnific-popup.js"></script>
<script src="/assets/vendors/counter/waypoints-min.js"></script>
<script src="/assets/vendors/counter/counterup.min.js"></script>
<script src="/assets/vendors/imagesloaded/imagesloaded.js"></script>
<script src="/assets/vendors/masonry/masonry.js"></script>
<script src="/assets/vendors/masonry/filter.js"></script>
<script src="/assets/vendors/owl-carousel/owl.carousel.js"></script>
<script src="/assets/vendors/scroll/scrollbar.min.js"></script>
<script src="/assets/js/functions.js"></script>
<script src="/assets/vendors/chart/chart.min.js"></script>
<script src="/assets/js/admin.js"></script>
<script src="/assets/vendors/calendar/moment.min.js"></script>
<script src="/assets/vendors/calendar/fullcalendar.js"></script>
<script src="/assets/vendors/switcher/switcher.js"></script>
<script>
    function submitForm() {
        document.getElementById('searchForm').submit();
    }

    $(document).ready(function () {
        // Ensure that modals close correctly
        $('.modal').on('hidden.bs.modal', function () {
            $(this).find('form')[0].reset();
        });
    });
</script>
</body>

</html>
