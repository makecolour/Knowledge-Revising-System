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
    <link rel="shortcut icon" type="image/x-icon" href="assets/images/favicon.png"/>

    <!-- PAGE TITLE HERE ============================================= -->
    <title>EduChamp : Education HTML Template </title>

    <!-- MOBILE SPECIFIC ============================================= -->
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!--[if lt IE 9]>
    <script src="assets/js/html5shiv.min.js"></script>
    <script src="assets/js/respond.min.js"></script>
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


    <script src="https://cdn.jsdelivr.net/npm/select2@4.0.13/dist/js/select2.min.js"></script>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/sweetalert2@10/dist/sweetalert2.min.css">
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@10"></script>
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
                <li>Question List</li>
            </ul>
        </div>
        <div class="content-wrapper" style="display: flex; flex-direction: column;">
            <div class="container">
                <div class="heading-bx left">
                    <h2 class="title-head">List <span>Study Set</span></h2>
                </div>
                <%--                <form method="get" action="/subject">--%>
                <%--                    <div class="form-group">--%>
                <%--                        <input type="text" name="keyword" class="form-control" placeholder="Tìm kiếm môn học"--%>
                <%--                               value="${param.keyword}">--%>
                <%--                    </div>--%>
                <%--                    <button type="submit" class="btn btn-primary">Tìm kiếm</button>--%>
                <%--                </form>--%>
                <div class="text-right">
                    <c:if test="${user.role.name != 'STUDENT'}">
                        <button type="button" class="btn btn-success" onclick="showAddStudySetModal('${lessonsId}')">Add StudySet</button>
                    </c:if>
                </div>

                <div class="text-left">
                    <button type="button" class="btn btn-success" onclick="window.history.back()">Back to List</button>
                    <button type="button" class="btn btn-success" onclick="window.location.href='/study-set/${lessonsId}/flash'">Flash</button>
                    <button type="button" class="btn btn-success" onclick="window.location.href='/study-set/${lessonsId}/test'">Test</button>
                    <button type="button" class="btn btn-success" onclick="window.location.href='/study-set/${lessonsId}/match'">Match</button>
                </div>

                <div class="table-responsive">
                    <table class="table table-striped">
                        <thead>
                        <tr>

                            <th>Term</th>
                            <th>Definition</th>
                            <%--                            <th>Mô Tả</th>--%>
                            <c:if test="${user.role.name != 'STUDENT'}">
                                <th>Status</th>
                                <th></th>
                            </c:if>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="c" items="${list}">
                            <tr>

                                <td>${c.term}</td>
                                <td>${c.definition}</td>

                                <c:if test="${user.role.name != 'STUDENT'}">
                                    <td>${c.status?"Active":"InActive"}</td>
                                    <td>
                                        <button type="button" class="btn btn-danger" onclick="deleteStudySet(${c.id})">Delete</button>
                                        <button type="button" class="btn btn-warning" onclick="showEditStudySetModal('${c.id}', '${c.term}', '${c.definition}', ${c.status})">Update</button>
                                    </td>
                                </c:if>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <nav aria-label="Page navigation">
                        <ul class="pagination">
                            <c:if test="${totalPages > 0}">
                                <c:forEach var="i" begin="0" end="${totalPages - 1}">
                                    <li class="page-item ${i == currentPage ? 'active' : ''}">
                                        <a class="page-link" href="?page=${i}&size=${5}&keyword=${keyword}">${i + 1}</a>
                                    </li>
                                </c:forEach>
                            </c:if>
                        </ul>
                    </nav>

                </div>
            </div>
        </div>
    </div>
</main>
<div class="modal fade" id="addStudySetModal" tabindex="-1" role="dialog" aria-labelledby="addStudySetModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="addStudySetModalLabel">Add StudySet</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form id="addStudySetForm">
                    <div class="form-group">
                        <label for="term">Term</label>
                        <input type="text" class="form-control" name="term" required>
                    </div>
                    <div class="form-group">
                        <label for="definition">Definition</label>
                        <input type="text" class="form-control" name="definition" required>
                    </div>
                    <div class="form-group">
                        <label for="status">Status</label>
                        <select class="form-control" name="status">
                            <option value="true">Active</option>
                            <option value="false">Inactive</option>
                        </select>
                    </div>
                    <input type="hidden" name="lessonId" >
                    <button type="submit" class="btn btn-primary">Save</button>
                </form>
            </div>
        </div>
    </div>
</div>
<!-- Modal for Editing StudySet -->
<div class="modal fade" id="editStudySetModal" tabindex="-1" role="dialog" aria-labelledby="editStudySetModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="editStudySetModalLabel">Edit StudySet</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form id="editStudySetForm">
                    <input type="hidden" name="id">
                    <div class="form-group">
                        <label for="term">Term</label>
                        <input type="text" class="form-control" name="term" required>
                    </div>
                    <div class="form-group">
                        <label for="definition">Definition</label>
                        <input type="text" class="form-control" name="definition" required>
                    </div>
                    <div class="form-group">
                        <label for="status">Status</label>
                        <select class="form-control" name="status">
                            <option value="true">Active</option>
                            <option value="false">Inactive</option>
                        </select>
                    </div>
                    <button type="submit" class="btn btn-primary">Save</button>
                </form>
            </div>
        </div>
    </div>
</div>
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
<script src='assets/vendors/calendar/moment.min.js'></script>
<script src='/assets/vendors/calendar/fullcalendar.js'></script>
<script src='/assets/vendors/switcher/switcher.js'></script>
<script type="text/javascript" src="/assets/dist/purify.min.js">
    const clean = DOMPurify.sanitize(dirty);
</script>
<script type="text/javascript" src="/assets/dist/xss.js"></script>
<script>
    function showAddStudySetModal(lessonId) {
        $('#addStudySetForm').trigger("reset");
        $('#addStudySetForm').find('input[name="lessonId"]').val(lessonId);
        $('#addStudySetModal').modal('show');
    }


    function showEditStudySetModal(id, term, definition, status) {
        $('#editStudySetForm').find('input[name="id"]').val(id);
        $('#editStudySetForm').find('input[name="term"]').val(term);
        $('#editStudySetForm').find('input[name="definition"]').val(definition);
        $('#editStudySetForm').find('select[name="status"]').val(status ? 'true' : 'false');
        $('#editStudySetModal').modal('show');
    }

    function deleteStudySet(id) {
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
                    url: '/study-set/delete',
                    type: 'POST',
                    data: { id: id },
                    success: function(response) {
                        Swal.fire('Deleted!', 'StudySet has been deleted.', 'success').then(() => {
                            location.reload();
                        });
                    },
                    error: function(xhr, status, error) {
                        Swal.fire('Error!', 'Failed to delete StudySet.', 'error');
                    }
                });
            }
        });
    }

    $(document).ready(function() {
        $('#addStudySetForm').on('submit', function(e) {
            e.preventDefault();
            $.ajax({
                url: '/study-set/add',
                type: 'POST',
                data: $(this).serialize(),
                success: function(response) {
                    Swal.fire('Added!', 'StudySet has been added.', 'success').then(() => {
                        location.reload();
                    });
                },
                error: function(xhr, status, error) {
                    Swal.fire('Error!', 'Failed to add StudySet.', 'error');
                }
            });
        });

        $('#editStudySetForm').on('submit', function(e) {
            e.preventDefault();
            $.ajax({
                url: '/study-set/update',
                type: 'POST',
                data: $(this).serialize(),
                success: function(response) {
                    Swal.fire('Updated!', 'StudySet has been updated.', 'success').then(() => {
                        location.reload();
                    });
                },
                error: function(xhr, status, error) {
                    Swal.fire('Error!', 'Failed to update StudySet.', 'error');
                }
            });
        });
    });
</script>



</body>



</html>