<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">

<!-- Mirrored from educhamp.themetrades.com/demo/admin/index.html by HTTrack Website Copier/3.x [XR&CO'2014], Fri, 22 Feb 2019 13:08:15 GMT -->
<head>
    <style>
        .upload-button {
            margin-left: auto;
        }
    </style>
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
                <li><a href="${pageContext.request.contextPath}/home"><i class="fa fa-home"></i>Home</a></li>
                <li>Dashboard</li>
            </ul>
        </div>


        <div class="" style="display: flex; flex-direction: column">
            <c:if test="${not empty participantList}">

                <div class="col-12">
                    <div class="heading-bx left">
                        <h2 class="title-head">Participant</h2>
                    </div>

                    <div style="display: flex; flex-direction: row; justify-content: space-between;">

                        <form action="/course/detail/participant/${course.id}/search" method="get" id="searchForm" class="form-group col-12" style="display: flex; flex-direction: row; justify-content: space-between;">
                            <div class="d-flex flex-row align-items-center">
                                <div class="mr-2">
                                    <input name="name" type="text" class="form-control" placeholder="Enter user name" value="${sessionScope.name}">
                                </div>
                                    <div class="mr-2">
                                        <select name="role" class="form-control" onchange="submitForm()">
                                            <option value="">All</option>
                                            <c:forEach items="${roleList}" var="role">
                                                <option value="${role.name}" <c:if test="${role.name == param.role}">selected</c:if>>${role.name}</option>
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
                            </div>
                            <c:if test="${user.role.name != 'STUDENT' && user.role.speciality == 'User Role'}">
                                <div class="upload-button">
                                    <button type="button" class="btn btn-secondary" data-toggle="modal" data-target="#uploadModal">Upload</button>
                                </div>
                            </c:if>

                        </form>


                    </div>

                    <table class="table table-striped col-12">
                        <thead>
                        <tr>
                            <th>ID</th>
                            <th>Full Name</th>
                            <th>Email</th>
                            <th>Role</th>
                            <th>Status</th>
                            <th>Action</th>
                        </tr>
                        </thead>
                        <tbody>
                        <!-- Each row in tbody represents a user -->

                        <c:forEach var="enrolled" items="${participantList}">
                            <tr>
                                <td>${enrolled.user.id}</td>
                                <td>${enrolled.user.fullName}</td>
                                <td>${enrolled.user.email}</td>
                                <td>${enrolled.user.role.name}</td>
                                <td>
                                    <c:if test="${enrolled.user.status == true}">
                                        Active
                                    </c:if>
                                    <c:if test="${enrolled.user.status == false}">
                                        Inactive
                                    </c:if>
                                </td>
                                <td>
                                    <form method="get" action="/profile/${enrolled.user.id}">
                                        <button type="submit" class="btn btn-secondary">View</button>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>

                        <!-- Add more rows as needed -->
                        </tbody>
                    </table>
                    <div aria-label="Page navigation">
                        <ul class="pagination">
                            <c:if test="${currentPage > 0}">
                                <li class="page-item">
                                    <a class="page-link" href="${pageContext.request.contextPath}/course/detail/participant/${course.id}/search?name=${param.name}&role=${param.role}&status=${param.status}&page=${currentPage - 1}&size=${9}">Previous</a>
                                </li>
                            </c:if>
                            <c:if test="${totalPages > 0}">
                                <c:forEach var="i" begin="0" end="${totalPages - 1}">
                                    <li class="page-item ${i == currentPage ? 'active' : ''}">
                                        <a class="page-link" href="${pageContext.request.contextPath}/course/detail/participant/${course.id}/search?name=${param.name}&role=${param.role}&status=${param.status}&page=${i}&size=${9}">${i + 1}</a>
                                    </li>
                                </c:forEach>
                            </c:if>
                            <c:if test="${currentPage < totalPages - 1}">
                                <li class="page-item">
                                    <a class="page-link" href="${pageContext.request.contextPath}/course/detail/participant/${course.id}/search?name=${param.name}&role=${param.role}&status=${param.status}&page=${currentPage + 1}&size=${9}">Next</a>
                                </li>
                            </c:if>
                        </ul>
                    </div>
                </div>
            </c:if>
            <c:if test="${ empty participantList}">
                <div class="alert alert-warning" role="alert">
                    No participant found
                </div>
            </c:if>

        </div>

        <!-- Modal HTML -->
        <div class="modal fade" id="uploadModal" tabindex="-1" role="dialog" aria-labelledby="uploadModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="uploadModalLabel">Upload Excel</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <!-- Display success message if present -->
                        <div id="successMessage" class="alert alert-success" style="display:none;">
                            <p></p>
                        </div>

                        <!-- Display error message if present -->
                        <div id="errorMessage" class="alert alert-danger" style="display:none;">
                            <p></p>
                        </div>

                        <form id="uploadForm" action="${pageContext.request.contextPath}/uploadExcel/course/${course.id}" method="post" enctype="multipart/form-data">
                            <input type="file" name="file" class="form-control mb-3" required>
                            <button type="submit" class="btn btn-primary">Upload</button>
                        </form>
                    </div>
                </div>
            </div>
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
    function submitForm() {
        document.getElementById('searchForm').submit();
    }
</script>
<script>
    // apply function filterXSS in the same way
    var html = filterXSS('<script>alert("xss");</scr' + "ipt>");
</script>

<script>
    document.addEventListener("DOMContentLoaded", function() {
        document.getElementById("uploadForm").addEventListener("submit", function(event) {
            event.preventDefault();
            var form = event.target;
            var formData = new FormData(form);

            fetch(form.action, {
                method: form.method,
                body: formData,
                headers: {
                    'Accept': 'application/json'
                }
            })
                .then(response => response.json().then(data => ({ status: response.status, body: data })))
                .then(data => {
                    if (data.status !== 200) {
                        console.error('Error response:', data);
                        document.getElementById("errorMessage").style.display = 'block';
                        document.getElementById("errorMessage").querySelector('p').textContent = data.body.error || 'An unknown error occurred.';
                        document.getElementById("successMessage").style.display = 'none';
                    } else {
                        if (data.body.error) {
                            document.getElementById("errorMessage").style.display = 'block';
                            document.getElementById("errorMessage").querySelector('p').textContent = data.body.error;
                            document.getElementById("successMessage").style.display = 'none';
                        } else if (data.body.success) {
                            document.getElementById("successMessage").style.display = 'block';
                            document.getElementById("successMessage").querySelector('p').textContent = data.body.success;
                            document.getElementById("errorMessage").style.display = 'none';

                            // Redirect to home page
                            if (data.body.redirect) {
                                window.location.href = data.body.redirect;
                            }
                        }
                    }
                })
                .catch(error => {
                    console.error('Fetch error:', error);
                    document.getElementById("errorMessage").style.display = 'block';
                    document.getElementById("errorMessage").querySelector('p').textContent = 'An error occurred while uploading the file.';
                    document.getElementById("successMessage").style.display = 'none';
                });
        });
    });
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