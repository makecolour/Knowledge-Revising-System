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
                    <h2 class="title-head">Exam</h2>
                </div>

                <div style="display: flex; flex-direction: row; justify-content: space-between">
                    <form action="${pageContext.request.contextPath}/exam-list" method="get"
                          class="form-group col-8">
                        <div class="d-flex flex-row align-items-center">
                            <div class="mr-2">
                                <input name="name" type="text" class="form-control" placeholder="Enter setting name"
                                       value="${sessionScope.name}"></div>
                            <div class="mr-2">
                                <select name="courseId" class="form-control">
                                    <option value="">All</option>
                                    <c:forEach items="${courseList}" var="list">
                                        <option value="${list.id}"
                                                <c:if test="${list.id == param.courseId}">selected</c:if>
                                        >${list.courseName} - ${list.teacher.fullName}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="mr-2">
                                <select name="subjectId" class="form-control">
                                    <option value="">All</option>
                                    <c:forEach items="${subjectList}" var="list">
                                        <option value="${list.id}"
                                                <c:if test="${list.id == param.courseId}">selected</c:if>
                                        >${list.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="mr-2">
                                <select name="status" class="form-control">
                                    <option value="" <c:if test="${empty param.status}">selected</c:if>>All</option>
                                    <option value="true" <c:if test="${'true' == param.status}">selected</c:if>>
                                        Public
                                    </option>
                                    <option value="false" <c:if test="${'false' == param.status}">selected</c:if>>
                                        Private
                                    </option>
                                </select>
                            </div>
                            <div class="col-auto">
                                <button type="submit" class="btn btn-primary">Search</button>
                            </div>
                        </div>
                    </form>

                    <div>
                        <a href="/exam-table/adding"
                           class="btn btn-secondary">Add</a>
                    </div>
                </div>

                <div class="table-responsive">
                    <table class="table table-striped">
                        <thead>
                        <tr>
                            <th>Id</th>
                            <th>Name</th>
                            <th>Time</th>
                            <th>Course</th>
                            <th>Subject</th>
                            <th>Number of question</th>
                            <th>Status</th>
                            <th>Author</th>
                            <th>Action</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:if test="${empty examList}">
                            <div class="alert alert-warning" role="alert">
                                No exam found
                            </div>
                        </c:if>
                        <c:forEach var="list" items="${examList}">
                            <tr>
                                <td>${list.id}</td>
                                <td>${list.name}</td>
                                <td>${list.length}</td>
                                <td>${list.course.courseName}</td>
                                <td>${list.subject.name}</td>
                                <td>${list.numberOfQuestions}</td>
                                <td>${list.status ? "Public" : "Private"}</td>
                                <td>${list.creator.fullName}</td>
                                <td>
                                    <form action="/exam-list/${list.id}" method="get" style="display: inline;">
                                        <button type="submit" class="btn btn-danger">Edit</button>
                                    </form>

                                </td>
                                <td>
                                    <button onclick="window.location.href='/exam=' + ${list.id};">Do exam</button>                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <div aria-label="Page navigation">
                        <ul class="pagination">
                            <c:if test="${currentPage > 0}">
                                <li class="page-item">
                                    <a class="page-link"
                                       href="${pageContext.request.contextPath}/exam-table/search?name=${param.name}&course=${param.course}&status=${param.status}&page=${currentPage - 1}&size=${9}">Previous</a>
                                </li>
                            </c:if>
                            <c:if test="${totalPages > 0}">
                                <c:forEach var="i" begin="0" end="${totalPages - 1}">
                                    <li class="page-item ${i == currentPage ? 'active' : ''}">
                                        <a class="page-link"
                                           href="${pageContext.request.contextPath}/exam-table/search?name=${param.name}&course=${param.course}&status=${param.status}&page=${i}&size=${9}">${i + 1}</a>
                                    </li>
                                </c:forEach>
                            </c:if>
                            <c:if test="${currentPage < totalPages - 1}">
                                <li class="page-item">
                                    <a class="page-link"
                                       href="${pageContext.request.contextPath}/exam-table/search?name=${param.name}&course=${param.course}&status=${param.status}&page=${currentPage + 1}&size=${9}">Next</a>
                                </li>
                            </c:if>
                        </ul>
                    </div>

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