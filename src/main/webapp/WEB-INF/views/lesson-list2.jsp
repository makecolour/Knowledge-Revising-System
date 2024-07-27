<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">

<!-- Mirrored from educhamp.themetrades.com/demo/admin/index.html by HTTrack Website Copier/3.x [XR&CO'2014], Fri, 22 Feb 2019 13:08:15 GMT -->
<head>

    <!-- META ============================================= -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="keywords" content=""/>
    <meta name="author" content=""/>
    <meta name="robots" content=""/>

    <!-- DESCRIPTION -->
    <meta name="description" content="EduChamp : Education HTML Template"/>

    <!-- OG -->
    <meta property="og:title" content="EduChamp : Education HTML Template"/>
    <meta property="og:description" content="EduChamp : Education HTML Template"/>
    <meta property="og:image" content=""/>
    <meta name="format-detection" content="telephone=no">

    <!-- FAVICONS ICON ============================================= -->
    <link rel="icon" href="../error-404.html" type="image/x-icon"/>
    <link rel="shortcut icon" type="image/x-icon" href="/assets/images/favicon.png"/>

    <!-- PAGE TITLE HERE ============================================= -->
    <title>EduChamp : Education HTML Template </title>

    <!-- MOBILE SPECIFIC ============================================= -->
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!--[if lt IE 9]>
    <script src="/assets/js/html5shiv.min.js"></script>
    <script src="/assets/js/respond.min.js"></script>
    <![endif]-->

    <!-- All PLUGINS CSS ============================================= -->
    <link rel="stylesheet" type="text/css" href="/assets/css/assets.css">
    <link rel="stylesheet" type="text/css" href="/assets/vendors/calendar/fullcalendar.css">

    <!-- TYPOGRAPHY ============================================= -->
    <link rel="stylesheet" type="text/css" href="/assets/css/typography.css">

    <!-- SHORTCODES ============================================= -->
    <link rel="stylesheet" type="text/css" href="/assets/css/shortcodes/shortcodes.css">

    <!-- STYLESHEETS ============================================= -->
    <link rel="stylesheet" type="text/css" href="/assets/css/style.css">
    <link rel="stylesheet" type="text/css" href="/assets/css/dashboard.css">
    <link class="skin" rel="stylesheet" type="text/css" href="/assets/css/color/color-1.css">

</head>
<body class="ttr-opened-sidebar ttr-pinned-sidebar">

<jsp:include page="dashboard-header.jsp"></jsp:include>

<!--Main container start -->
<main class="ttr-wrapper">
    <div class="modal fade" id="confirmModal" tabindex="-1" role="dialog" aria-labelledby="confirmModalLabel"
         aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="confirmModalLabel">Confirmation</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    Are you sure you want to change the status?
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                    <button type="button" class="btn btn-primary" id="confirmButton">OK</button>
                </div>
            </div>
        </div>
    </div>
    <div class="container-fluid">
        <div class="db-breadcrumb">
            <h4 class="breadcrumb-title">Dashboard</h4>
            <ul class="db-breadcrumb-list">
                <li><a href="#"><i class="fa fa-home"></i>Home</a></li>
                <li>Dashboard</li>
            </ul>
        </div>
        <div class="" style="display: flex; flex-direction: column">
            <%--            <c:choose>--%>
            <%--                <c:when test="${user.role.name == 'ADMIN'}">--%>
            <div class="">
                <div class="heading-bx left">
                    <h2 class="title-head">Lesson</h2>
                </div>

                <div style="display: flex; flex-direction: row; justify-content: space-between">
                    <form action="${pageContext.request.contextPath}/lesson/search" method="get" class="form-group col-8" id="searchForm">
                        <div class="d-flex flex-row align-items-center">
                            <div class="mr-2">
                                <input name="lessonName" type="text" class="form-control" placeholder="Enter lesson name" value="${sessionScope.name}">
                            </div>
                            <div class="mr-2">
                                <select name="subjectId" class="form-control" id="subjectSelect" onchange="submitForm()">
                                    <c:forEach items="${subjectSet}" var="sub">
                                        <option value="${sub.id}" ${sub.id == subjectId ? 'selected' : ''}>${sub.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="mr-2">
                                <select name="status" class="form-control" id="statusSelect" onchange="submitForm()">
                                    <option value="" <c:if test="${empty param.status}">selected</c:if>>All</option>
                                    <option value="true" <c:if test="${'true' == param.status}">selected</c:if>>Active</option>
                                    <option value="false" <c:if test="${'false' == param.status}">selected</c:if>>Inactive</option>
                                </select>
                            </div>
                        </div>
                    </form>

                    <div>
                        <a href="/lesson-adding"
                           class="btn btn-secondary">Add</a>
                    </div>
                </div>


                <div class="table-responsive">
                    <c:if test="${empty lessonList}">
                        <div class="alert alert-warning" role="alert">
                            No Lesson found
                        </div>
                    </c:if>
                    <c:if test="${not empty lessonList}">

                    <table class="table table-striped">
                        <thead>
                        <tr>
                            <th>Id
                                <a href="${pageContext.request.contextPath}/lesson/search?lessonName=${param.lessonName}&subjectId=${param.subjectId}&status=${param.status}&order=asc&column=id">↑</a>
                                <a href="${pageContext.request.contextPath}/lesson/search?lessonName=${param.lessonName}&subjectId=${param.subjectId}&status=${param.status}&order=desc&column=id">↓</a>
                            </th>
                            <th>Name
                                <a href="${pageContext.request.contextPath}/lesson/search?lessonName=${param.lessonName}&subjectId=${param.subjectId}&status=${param.status}&order=asc&column=name">↑</a>
                                <a href="${pageContext.request.contextPath}/lesson/search?lessonName=${param.lessonName}&subjectId=${param.subjectId}&status=${param.status}&order=desc&column=name">↓</a>
                            </th>
                            <th>Subject
                                <a href="${pageContext.request.contextPath}/lesson/search?lessonName=${param.lessonName}&subjectId=${param.subjectId}&status=${param.status}&order=asc&column=subject.name">↑</a>
                                <a href="${pageContext.request.contextPath}/lesson/search?lessonName=${param.lessonName}&subjectId=${param.subjectId}&status=${param.status}&order=desc&column=subject.name">↓</a>
                            </th>
                            <th>Order
                                <a href="${pageContext.request.contextPath}/lesson/search?lessonName=${param.lessonName}&subjectId=${param.subjectId}&status=${param.status}&order=asc&column=order">↑</a>
                                <a href="${pageContext.request.contextPath}/lesson/search?lessonName=${param.lessonName}&subjectId=${param.subjectId}&status=${param.status}&order=desc&column=order">↓</a>
                            </th>
                            <th>Status
                                <a href="${pageContext.request.contextPath}/lesson/search?lessonName=${param.lessonName}&subjectId=${param.subjectId}&&status=${param.status}&order=asc&column=status">↑</a>
                                <a href="${pageContext.request.contextPath}/lesson/search?lessonName=${param.lessonName}&subjectId=${param.subjectId}&&status=${param.status}&order=desc&column=status">↓</a>
                            </th>
                            <th>Action</th>
                        </tr>
                        </thead>
                        <tbody>

                        <c:forEach var="list" items="${lessonList}">
                            <tr>
                                <td>${list.id}</td>
                                <td>${list.name}</td>
                                <td>${list.subject.name}</td>
                                <td>${list.order}</td>
                                <td>${list.status ? "Active" : "Inactive"}</td>
                                <td>
                                    <button type="submit" class="btn btn-primary" onclick="window.location.href='/question-list/${list.id}'">View Questions</button>
                                    <button type="button" class="btn btn-primary" onclick="window.location.href='/flash-cards/${list.id}'">View Flash Cards</button>
                                    <form action="/lesson/edit/${list.id}" method="get" style="display: inline;">
                                        <button type="submit" class="btn btn-danger">Edit</button>
                                    </form>
                                    <form id="statusForm"
                                          action="/lesson/edit2/${list.id}/page=${currentPage}/size=${totalPages}"
                                          method="post"
                                          style="display: inline;">
                                        <button type="button" class="btn btn-warning" data-toggle="modal"
                                                data-target="#confirmModal">
                                                ${!list.status ? "Active" : "Inactive"}
                                        </button>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <div aria-label="Page navigation">
                        <ul class="pagination">
                            <c:if test="${currentPage > 0}">
                                <li class="page-item">
                                    <a class="page-link"
                                       href="${pageContext.request.contextPath}/lesson/search?lessonName=${param.lessonName}&subjectId=${param.subjectId}&status=${param.status}&page=${currentPage-1}&size=${9}">Previous</a>
                                </li>
                            </c:if>
                            <c:if test="${totalPages > 0}">
                                <c:forEach var="i" begin="0" end="${totalPages - 1}">
                                    <li class="page-item ${i == currentPage ? 'active' : ''}">
                                        <a class="page-link"
                                           href="${pageContext.request.contextPath}/lesson/search?lessonName=${param.lessonName}&subjectId=${param.subjectId}&status=${param.status}&page=${i}&size=${9}">${i + 1}</a>
                                    </li>
                                </c:forEach>
                            </c:if>
                            <c:if test="${currentPage < totalPages - 1}">
                                <li class="page-item">
                                    <a class="page-link"
                                       href="${pageContext.request.contextPath}/lesson/search?lessonName=${param.lessonName}&subjectId=${param.subjectId}&status=${param.status}&page=${currentPage+1}&size=${9}">Next</a>
                                </li>
                            </c:if>
                        </ul>
                    </div>
                    </c:if>

                </div>
            </div>
            <%--                </c:when>--%>

            <%--                <c:when test="${user.role.name == 'TEACHER'}">--%>

            <%--                </c:when>--%>

            <%--                <c:when test="${user.role.name == 'STUDENT'}">--%>

            <%--                </c:when>--%>

            <%--                <c:otherwise>--%>
            <%--                    <h1>Unauthorized access</h1>--%>
            <%--                </c:otherwise>--%>
            <%--            </c:choose>--%>
        </div>
    </div>
</main>
<div class="ttr-overlay"></div>

<!-- External JavaScripts -->
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
<script src='/assets/vendors/scroll/scrollbar.min.js'></script>
<script src="/assets/js/functions.js"></script>
<script src="/assets/vendors/chart/chart.min.js"></script>
<script src="/assets/js/admin.js"></script>
<script src='/assets/vendors/calendar/moment.min.js'></script>
<script src='/assets/vendors/calendar/fullcalendar.js'></script>
<script src='/assets/vendors/switcher/switcher.js'></script>
<script type="text/javascript" src="/assets/dist/purify.min.js">
    const clean = DOMPurify.sanitize(dirty);
</script>
<script>
    function submitForm() {
        document.getElementById('searchForm').submit();
    }
</script>
<script>
    function submitForm2() {
        document.forms[0].submit();
    }
</script>
<script>
    $(document).ready(function () {
        $('#confirmButton').click(function () {
            $('#statusForm').submit();
        });
    });
</script>
<script type="text/javascript" src="/assets/dist/xss.js"></script>
<script>
    // apply function filterXSS in the same way
    var html = filterXSS('<script>alert("xss");</scr' + "ipt>");
</script>
<script>
    $(document).ready(function () {

        $('#calendar').fullCalendar({
            header: {
                left: 'prev,next today',
                center: 'title',
                right: 'month,agendaWeek,agendaDay,listWeek'
            },
            defaultDate: '2019-03-12',
            navLinks: true, // can click day/week names to navigate views

            weekNumbers: true,
            weekNumbersWithinDays: true,
            weekNumberCalculation: 'ISO',

            editable: true,
            eventLimit: true, // allow "more" link when too many events
            events: [
                {
                    title: 'All Day Event',
                    start: '2019-03-01'
                },
                {
                    title: 'Long Event',
                    start: '2019-03-07',
                    end: '2019-03-10'
                },
                {
                    id: 999,
                    title: 'Repeating Event',
                    start: '2019-03-09T16:00:00'
                },
                {
                    id: 999,
                    title: 'Repeating Event',
                    start: '2019-03-16T16:00:00'
                },
                {
                    title: 'Conference',
                    start: '2019-03-11',
                    end: '2019-03-13'
                },
                {
                    title: 'Meeting',
                    start: '2019-03-12T10:30:00',
                    end: '2019-03-12T12:30:00'
                },
                {
                    title: 'Lunch',
                    start: '2019-03-12T12:00:00'
                },
                {
                    title: 'Meeting',
                    start: '2019-03-12T14:30:00'
                },
                {
                    title: 'Happy Hour',
                    start: '2019-03-12T17:30:00'
                },
                {
                    title: 'Dinner',
                    start: '2019-03-12T20:00:00'
                },
                {
                    title: 'Birthday Party',
                    start: '2019-03-13T07:00:00'
                },
                {
                    title: 'Click for Google',
                    url: 'http://google.com/',
                    start: '2019-03-28'
                }
            ]
        });

    });

</script>
</body>

<!-- Mirrored from educhamp.themetrades.com/demo/admin/index.html by HTTrack Website Copier/3.x [XR&CO'2014], Fri, 22 Feb 2019 13:09:05 GMT -->
</html>