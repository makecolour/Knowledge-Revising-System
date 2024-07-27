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
    <title>EduChamp : Education HTML Template</title>

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
                <li><a href="/home"><i class="fa fa-home"></i>Home</a></li>
                <li><a href="/dashboard"><i class="fa fa-home"></i>Dashboard</a></li>
                <li>User List</li>
            </ul>
        </div>
        <div class="" style="display: flex; flex-direction: column">
            <div>
                <div class="heading-bx left">
                    <h2 class="title-head">User</h2>
                </div>

                <!-- Filter Bar -->
                <div style="display: flex; flex-direction: row; justify-content: space-between; margin-bottom: 1em">
                    <!-- Filter Bar -->
                    <div style="display: flex; justify-content: space-between; margin-bottom: 0.5em;">
                        <!-- Roles Dropdown -->
                        <select name="role" class="form-control" onchange="">
                            <option value="">Select Role</option>
                            <option value="admin">Admin</option>
                            <option value="manager">Manager</option>
                            <option value="teacher">Teacher</option>
                            <option value="student">Student</option>
                        </select>

                        <!-- Status Dropdown -->
                        <select name="status" class="form-control" onchange="">
                            <option value="">Select Status</option>
                            <option value="active">Active</option>
                            <option value="inactive">Inactive</option>
                        </select>
                    </div>

                    <!-- Search Bar -->
                    <div style="width: 45%">
                        <form method="get" action="/search" style="display: flex; flex-direction: row">
                            <input type="text" name="query" class="form-control" placeholder="Search...">
                            <button type="submit" class="btn btn-primary">Search</button>
                        </form>
                    </div>

                    <!-- Add User Button -->
                    <div>
                        <button type="button" class="btn btn-success" onclick="showAddUserModal()">Add User</button>
                    </div>
                </div>

                <div class="table-responsive">
                    <table class="table table-striped">
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
                        <c:forEach var="user" items="${user}">

                            <tr>
                                <td>${user.id}</td>
                                <td>${user.fullName}</td>
                                <td>${user.email}</td>
                                <td>${user.role.name}</td>
                                <td>
                                    <c:if test="${user.status == true}">
                                        Active
                                    </c:if>
                                    <c:if test="${user.status == false}">
                                        Inactive
                                    </c:if>
                                </td>
                                <td>
                                    <!-- View Button -->
                                    <form method="get" action="/profile/${user.id}" style="display: inline;">
                                        <button type="submit" class="btn" style="background-color: #007bff; border: none; color: black;">
                                            <i class="fa fa-eye" style="font-size: 20px;"></i>
                                        </button>
                                    </form>

                                    <!-- Deactivate Button -->
                                    <form method="post" action="/profile/${user.id}/deactivate" style="display: inline;">
                                        <button type="submit" class="btn" style="background-color: #ffc107; border: none; color: black;">
                                            <i class="fa fa-ban" style="font-size: 20px;"></i>
                                        </button>
                                    </form>

                                    <!-- Delete Button -->
                                    <button type="submit" class="btn" style="background-color: #dc3545; border: none; color: black;" onclick="deleteUser(${user.id})">
                                        <i class="fa fa-trash" style="font-size: 20px;"></i>
                                    </button>
                                </td>
                            </tr>
                        </c:forEach>
                        <!-- Add more rows as needed -->
                        </tbody>
                    </table>
                    <nav aria-label="Page navigation">
                        <ul class="pagination">
                            <c:forEach var="i" begin="0" end="${totalPages - 1}">
                                <li class="page-item ${i == currentPage ? 'active' : ''}">
                                    <a class="page-link" href="?page=${i}&size=${5}&keyword=${param.keyword}">${i + 1}</a>
                                </li>
                            </c:forEach>
                        </ul>
                    </nav>
                </div>

                <div class="modal fade" id="addUserModal" tabindex="-1" role="dialog" aria-labelledby="addUserModalLabel" aria-hidden="true">
                    <div class="modal-dialog" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="addStudySetModalLabel">Add User</h5>
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div class="modal-body">
                                <form id="addUser">
                                    <div class="form-group">
                                        <label for="fname">Full name</label>
                                        <input type="text" class="form-control" name="fname" required>
                                    </div>
                                    <div class="form-group">
                                        <label for="email">Email</label>
                                        <input type="email" class="form-control" name="email" required>
                                    </div>
                                    <div style="display: flex; justify-content: space-between;">
                                        <!-- Status Div -->
                                        <div class="form-group" style="width: 45%">
                                            <label for="status">Status</label>
                                            <select class="form-control" name="status">
                                                <option value="true">Active</option>
                                                <option value="false">Inactive</option>
                                            </select>
                                        </div>

                                        <!-- Roles Div -->
                                        <div class="form-group" style="width: 45%">
                                            <label for="role">Role</label>
                                            <select class="form-control" name="role">
                                                <option value="1">Student</option>
                                                <option value="2">Teacher</option>
                                                <option value="3">Manager</option>
                                                <option value="4">Admin</option>
                                            </select>
                                        </div>
                                    </div>
                                    <button type="submit" class="btn btn-primary">Add</button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
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
<script>
    function showAddUserModal() {
        $('#addUserForm').trigger("reset");
        $('#addUserModal').modal('show');
    }

    function deleteUser(id) {
        Swal.fire({
            title: 'Are you sure you want to delete?',
            text: "This action cannot be undone!",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Delete'
        }).then((result) => {
            if (result.isConfirmed) {
                $.ajax({
                    url: '/profile/${id}/delete',
                    type: 'POST',
                    data: { id: id },
                    success: function(response) {
                        Swal.fire('Deleted!', 'User has been deleted.', 'success').then(() => {
                            location.reload();
                        });
                    },
                    error: function(xhr, status, error) {
                        Swal.fire('Error!', 'Failed to delete User.', 'error');
                    }
                });
            }
        });
    }

    $(document).ready(function() {
        $('#addUserForm').on('submit', function(e) {
            e.preventDefault();
            $.ajax({
                url: '/study-set/add', // TODO - Add the URL of the servlet to add User
                type: 'POST',
                data: $(this).serialize(),
                success: function(response) {
                    Swal.fire('Added!', 'User has been added.', 'success').then(() => {
                        location.reload();
                    });
                },
                error: function(xhr, status, error) {
                    Swal.fire('Error!', 'Failed to add StudySet.', 'error');
                }
            });
        });
    });
</script>
</body>

<!-- Mirrored from educhamp.themetrades.com/demo/admin/index.html by HTTrack Website Copier/3.x [XR&CO'2014], Fri, 22 Feb 2019 13:09:05 GMT -->
</html>