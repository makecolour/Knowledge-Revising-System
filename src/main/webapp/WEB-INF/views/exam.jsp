<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">

<!-- Mirrored from educhamp.themetrades.com/demo/admin/index.html by HTTrack Website Copier/3.x [XR&CO'2014], Fri, 22 Feb 2019 13:08:15 GMT -->
<head>

    <!-- META ============================================= -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="keywords" content="" />
    <meta name="author" content="" />
    <meta name="robots" content="" />

    <!-- DESCRIPTION -->
    <meta name="description" content="EduChamp : Education HTML Template" />

    <!-- OG -->
    <meta property="og:title" content="EduChamp : Education HTML Template" />
    <meta property="og:description" content="EduChamp : Education HTML Template" />
    <meta property="og:image" content="" />
    <meta name="format-detection" content="telephone=no">

    <!-- FAVICONS ICON ============================================= -->
    <link rel="icon" href="../error-404.html" type="image/x-icon" />
    <link rel="shortcut icon" type="image/x-icon" href="/assets/images/favicon.png" />

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
            <div style="font-size: larger; font-weight: bolder"> ${exam.name}</div>

            <!-- Exam Information -->
            <div style="display: flex; flex-direction: row; justify-content: space-between">
                <div style="text-align: left">
                    <div style="display: flex; flex-direction: row">
                        <h5>Description:</h5>
                        <p style="margin: 0;">${exam.description}</p>
                    </div>
                    <div style="display: flex; flex-direction: row">
                        <h5>Length:</h5>
                        <p style="margin: 0;">${exam.length}</p>
                    </div>
                    <div style="display: flex; flex-direction: row">
                        <h5>Number of Questions:</h5>
                        <p style="margin: 0;">${exam.numberOfQuestions}</p>
                    </div>
                    <div style="display: flex; flex-direction: row">
                        <h5>Status:</h5>
                        <p style="margin: 0;">
                            <c:if test="${exam.status == false}">
                                <span style="color: red">PRIVATE</span>
                            </c:if>
                            <c:if test="${exam.status == true}">
                                <span style="color: green">PUBLIC</span>
                            </c:if>
                        </p>
                    </div>
                </div>
                <c:if test="${creator==true}">
                    <div style="text-align: center">
                        <a href = "${pageContext.request.contextPath}/exam-list/${examId}" class="btn btn-primary">Edit</a>
                            <%--                <button type="button" href="${btnHref}" class="btn btn-primary">${btnContent}</button>--%>
                    </div>
                </c:if>
                <div style="text-align: center">
                    <a href = "${btnHref}" class="btn btn-primary">${btnContent}</a>
                    <%--                <button type="button" href="${btnHref}" class="btn btn-primary">${btnContent}</button>--%>
                </div>
            </div>


            <!-- Summary -->
            <p style="font-size: larger">Summary of your previous attempts</p>

            <div class="accordion">
                <c:forEach items="${attempts}" var="a" varStatus="i">
                    <c:set var="status" value="${a.status}" />
                    <c:choose>
                        <c:when test="${status == false}">
                            <div class="card">
                                <div class="card-header" id="heading${i.count}">
                                    <h5 class="mb-0">
                                        <a class="btn btn-link" data-toggle="collapse" data-target="#collapse${i.count}" aria-expanded="true" aria-controls="collapse${i.count}">
                                            Attempt ${i.count} - Started on ${a.attemptDate}
                                        </a>
                                    </h5>
                                </div>

                                <div id="collapse${i.count}" class="collapse ${i.count == 1 ? 'show' : ''}" aria-labelledby="heading${i.count}" data-parent="#accordion">
                                    <div class="card-body" style="display: flex; flex-direction: row; justify-content: space-between">
                                        <div>${a.mark} out of 10 / Total Question: ${a.attemptHistories.size()}</div>
                                        <a type="button" class="btn btn-link" href="/exam=${exam.id}/attempt=${a.id}">Review</a>
                                    </div>
                                </div>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="card">
                                <div class="card-header" id="heading${i.count}">
                                    <h5 class="mb-0">
                                        <a class="btn btn-link" data-toggle="collapse" data-target="#collapse${i.count}" aria-expanded="true" aria-controls="collapse${i.count}">
                                            Attempt ${i.count} - Started on ${a.attemptDate} - On-Going
                                        </a>
                                    </h5>
                                </div>

                                <div id="collapse${i.count}" class="collapse ${i.count == 1 ? 'show' : ''}" aria-labelledby="heading${i.count}" data-parent="#accordion">
                                    <div class="card-body" style="display: flex; flex-direction: row; justify-content: space-between">
                                        <div>Test still On-Going</div>
                                        <a type="button" class="btn btn-link" href="${btnHref}">${btnContent}</a>
                                    </div>
                                </div>
                            </div>
                        </c:otherwise>
                    </c:choose>

                </c:forEach>
            </div>
            <!-- Start button -->
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
<script type="text/javascript" src="//assets/dist/purify.min.js">
    const clean = DOMPurify.sanitize(dirty);
</script>
<script type="text/javascript" src="//assets/dist/xss.js"></script>
<script>
    // apply function filterXSS in the same way
    var html = filterXSS('<script>alert("xss");</scr' + "ipt>");
</script>
<script>
    $(document).ready(function() {

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