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
    <link rel="shortcut icon" type="image/x-icon" href="assets/images/favicon.png" />

    <!-- PAGE TITLE HERE ============================================= -->
    <title>EduChamp : Education HTML Template </title>

    <!-- MOBILE SPECIFIC ============================================= -->
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!--[if lt IE 9]>
    <script src="assets/js/html5shiv.min.js"></script>
    <script src="assets/js/respond.min.js"></script>
    <![endif]-->

    <!-- All PLUGINS CSS ============================================= -->
    <link rel="stylesheet" type="text/css" href="assets/css/assets.css">
    <link rel="stylesheet" type="text/css" href="assets/vendors/calendar/fullcalendar.css">

    <!-- TYPOGRAPHY ============================================= -->
    <link rel="stylesheet" type="text/css" href="assets/css/typography.css">

    <!-- SHORTCODES ============================================= -->
    <link rel="stylesheet" type="text/css" href="assets/css/shortcodes/shortcodes.css">

    <!-- STYLESHEETS ============================================= -->
    <link rel="stylesheet" type="text/css" href="assets/css/style.css">
    <link rel="stylesheet" type="text/css" href="assets/css/dashboard.css">
    <link class="skin" rel="stylesheet" type="text/css" href="assets/css/color/color-1.css">

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



        <c:if test="${sessionScope.error != null}">
            <div class="alert alert-danger" role="alert">
                <h4 class="alert-heading">Unauthorized access</h4>
                <p>You are not authorized to access this page. Please login to access this page.</p>
            </div>
        </c:if>
        <div class="" style="display: flex; flex-direction: column">
            <c:choose>
                <c:when test="${user.role.name != 'STUDENT'}">
                    <div style="margin-bottom: 10px">
                        <div class="heading-bx left">
                            <h2 class="title-head">Member</h2>
                        </div>

                        <div style="display: flex; flex-direction: row; width: 100%">
                            <!-- Total -->
                            <div style="width: 35%; margin-right: 0.5em; background-color: #e6e6e6; border-radius: 12px; padding: 12px;">
                                <h3 style="margin-bottom: 16px">Total Member</h3>
                                <h5 style="margin-bottom: 0">${totalUsers}</h5> <!-- TODO - Total Member Code here later -->
                            </div>

                            <!-- Each Role -->
                            <div style="width: 65%; display: flex; flex-direction: row">
                                <div style="width: 35%">
                                    <div style="margin-right: 0.5em; background-color: #e6e6e6; border-radius: 12px; padding: 8px; display: flex; flex-direction: row; align-items: center">
                                        <h3 style="margin: 0 6px 0 0">Admin:</h3>
                                        <h5 style="margin-bottom: 0">${ADMINCount}</h5> <!-- TODO - Total Member Code here later -->
                                    </div>
                                    <div style="margin-top: 0.5em; margin-right: 0.5em; background-color: #e6e6e6; border-radius: 12px; padding: 8px; display: flex; flex-direction: row; align-items: center">
                                        <h3 style="margin: 0 6px 0 0">Manager:</h3>
                                        <h5 style="margin-bottom: 0">${MANAGERCount}</h5> <!-- TODO - Total Member Code here later -->
                                    </div>
                                </div>

                                <div style="width: 35%">
                                    <div style="margin-right: 0.5em; background-color: #e6e6e6; border-radius: 12px; padding: 8px; display: flex; flex-direction: row; align-items: center">
                                        <h3 style="margin: 0 6px 0 0">Teacher:</h3>
                                        <h5 style="margin-bottom: 0">${TEACHERCount}</h5> <!-- TODO - Total Member Code here later -->
                                    </div>
                                    <div style="margin-top: 0.5em; margin-right: 0.5em; background-color: #e6e6e6; border-radius: 12px; padding: 8px; display: flex; flex-direction: row; align-items: center">
                                        <h3 style="margin: 0 6px 0 0">Student:</h3>
                                        <h5 style="margin-bottom: 0">${STUDENTCount}</h5> <!-- TODO - Total Member Code here later -->
                                    </div>
                                </div>
                            </div>

                        </div>
                    </div>



                    <div class="">
                        <div class="heading-bx left">
                            <h2 class="title-head">System</h2>
                        </div>

                        <div style="display: flex; flex-direction: column;">
                            <!-- Group 1 -->
                            <div style="display: flex; flex-direction: row; justify-content: space-between; width: 100%;">
                                <div style="flex: 1; background-color: #e6e6e6; padding: 20px; border-radius: 12px; margin: 10px;">
                                    <h2>Total Roles</h2>
                                    <p>${roleCounts}</p>
                                </div>
                                <div style="flex: 1; background-color: #e6e6e6; padding: 20px; border-radius: 12px; margin: 10px;">
                                    <h2>Total Classes</h2>
                                    <p>${courseCounts}</p>
                                </div>
                            </div>
                            <!-- Group 2 -->
                            <div style="display: flex; flex-wrap: wrap; justify-content: space-between; width: 100%;">
                                <div style="flex-basis: calc(50% - 20px); background-color: #e6e6e6; padding: 20px; border-radius: 12px; margin: 10px;">
                                    <h2>Total Exam + Questions</h2>
                                    <div style="display: flex; flex-direction: row">
                                        <h3>Total Exams:</h3>
                                        <p> ${examCount}</p>
                                    </div>
                                    <div style="display: flex; flex-direction: row">
                                        <h3>Total Questions: </h3>
                                        <p>${questionCount}</p>
                                    </div>

                                </div>
                                <div style="flex-basis: calc(50% - 20px); background-color: #e6e6e6; padding: 20px; border-radius: 12px; margin: 10px;">
                                    <h2>Total Attempt + Most Attempted Exam</h2>
                                    <div style="display: flex; flex-direction: row">
                                        <h3>Total Attempts: </h3>
                                        <p>${totalAttempt} </p>
                                    </div>
                                    <div style="display: flex; flex-direction: row">
                                        <h3>Most Attempted Exam: </h3>
                                        <p>${mostAttempts} (${mostAttemptsCount})</p>
                                    </div>

                                </div>
                                <div style="flex-basis: calc(50% - 20px); background-color: #e6e6e6; padding: 20px; border-radius: 12px; margin: 10px;">
                                    <h2>Total Lessons</h2>
                                    <p>${totalLesson}</p>
                                </div>
                                <div style="flex-basis: calc(50% - 20px); background-color: #e6e6e6; padding: 20px; border-radius: 12px; margin: 10px;">
                                    <div style="display: flex; flex-direction: column">
                                        <h3>Total Terms/Definitions: </h3>
                                        <p>${totalSS}</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:when>

                <c:when test="${user.role.name == 'TEACHER'}">
                    COMING SOON
                </c:when>

                <c:when test="${user.role.name == 'STUDENT'}">
                    COMING SOON
                </c:when>

                <c:otherwise>
                    <h1>Unauthorized access</h1>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</main>
<div class="ttr-overlay"></div>

<!-- External JavaScripts -->
<script src="assets/js/jquery.min.js"></script>
<script src="assets/vendors/bootstrap/js/popper.min.js"></script>
<script src="assets/vendors/bootstrap/js/bootstrap.min.js"></script>
<script src="assets/vendors/bootstrap-select/bootstrap-select.min.js"></script>
<script src="assets/vendors/bootstrap-touchspin/jquery.bootstrap-touchspin.js"></script>
<script src="assets/vendors/magnific-popup/magnific-popup.js"></script>
<script src="assets/vendors/counter/waypoints-min.js"></script>
<script src="assets/vendors/counter/counterup.min.js"></script>
<script src="assets/vendors/imagesloaded/imagesloaded.js"></script>
<script src="assets/vendors/masonry/masonry.js"></script>
<script src="assets/vendors/masonry/filter.js"></script>
<script src="assets/vendors/owl-carousel/owl.carousel.js"></script>
<script src='assets/vendors/scroll/scrollbar.min.js'></script>
<script src="assets/js/functions.js"></script>
<script src="assets/vendors/chart/chart.min.js"></script>
<script src="assets/js/admin.js"></script>
<script src='assets/vendors/calendar/moment.min.js'></script>
<script src='assets/vendors/calendar/fullcalendar.js'></script>
<script src='assets/vendors/switcher/switcher.js'></script>
<script type="text/javascript" src="/assets/dist/purify.min.js">
    const clean = DOMPurify.sanitize(dirty);
</script>
<script type="text/javascript" src="/assets/dist/xss.js"></script>
<script>
    // apply function filterXSS in the same way
    var html = filterXSS('<script>alert("xss");</scr' + "ipt>");
</script>
<script>
    document.addEventListener('DOMContentLoaded', function() {
        var noAuthorityMessage = /*[[${noAuthorityMessage}]]*/ '';
        if (noAuthorityMessage) {
            $('#noAuthorityModal').modal('show');
        }
    });
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