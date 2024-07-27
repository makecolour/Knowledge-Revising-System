<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">

<!-- Mirrored from educhamp.themetrades.com/demo/admin/add-listing.html by HTTrack Website Copier/3.x [XR&CO'2014], Fri, 22 Feb 2019 13:09:05 GMT -->
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
    <style>
        .switch {
            position: relative;
            display: inline-block;
            width: 60px;
            height: 34px;
        }

        .switch input {
            opacity: 0;
            width: 0;
            height: 0;
        }

        .slider {
            position: absolute;
            cursor: pointer;
            top: 0;
            left: 0;
            right: 0;
            bottom: 0;
            background-color: #ccc;
            -webkit-transition: .4s;
            transition: .4s;
        }

        .slider:before {
            position: absolute;
            content: "";
            height: 26px;
            width: 26px;
            left: 4px;
            bottom: 4px;
            background-color: white;
            -webkit-transition: .4s;
            transition: .4s;
        }

        input:checked + .slider {
            background-color: #2196F3;
        }

        input:focus + .slider {
            box-shadow: 0 0 1px #2196F3;
        }

        input:checked + .slider:before {
            -webkit-transform: translateX(26px);
            -ms-transform: translateX(26px);
            transform: translateX(26px);
        }

        /* Rounded sliders */
        .slider.round {
            border-radius: 34px;
        }

        .slider.round:before {
            border-radius: 50%;
        }
    </style>
</head>
<body class="ttr-opened-sidebar ttr-pinned-sidebar">

<jsp:include page="dashboard-header.jsp"></jsp:include>

<!--Main container start -->
<main class="ttr-wrapper">
    <div class="container-fluid">
        <div class="db-breadcrumb">
            <h4 class="breadcrumb-title">Add Lesson</h4>
            <ul class="db-breadcrumb-list">
                <li><a href="/home"><i class="fa fa-home"></i>Home</a></li>
                <li>Add Lesson</li>
            </ul>
        </div>
        <div class="row">
            <!-- Your Profile Views Chart -->
            <div class="col-lg-12 m-b30">
                <div class="widget-box">
                    <div class="wc-title">
                        <h4>Add Lesson</h4>
                    </div>

                    <div class="widget-inner">
                        <form class="edit-profile m-b30" method="post"
                              action="${pageContext.request.contextPath}/lesson-adding">

                            <div class="form-group ">

                                <label class="col-form-label">Subject</label>
                                <div>
                                    <select name="subjectId" class="form-control">
                                        <c:forEach items="${subjectSet}" var="role">
                                            <option value="${role.id}"
                                                    <c:if test="${role.id == param.subjectId}">selected</c:if>>
                                                    ${role.id} - ${role.name}
                                            </option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>

                            <div class="form-group">
                                <label>Status</label>
                                <div>
                                    <div class="form-check form-check-inline">
                                        <input class="form-check-input" type="radio" id="status-active" name="status" value="true" ${sessionScope.status == null || sessionScope.status ? 'checked' : ''}>
                                        <label class="form-check-label" for="status-active">Active</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                        <input class="form-check-input" type="radio" id="status-inactive" name="status" value="false" ${sessionScope.status != null && !sessionScope.status ? 'checked' : ''}>
                                        <label class="form-check-label" for="status-inactive">Inactive</label>
                                    </div>
                                </div>
                            </div>
                            <div class="card mb-3">
                                <div class="card-header">Lesson Information</div>
                                <div class="card-body">
                                    <!-- Display error message for new lesson -->
                                    <c:if test="${not empty errorMessage2}">
                                        <div class="alert alert-danger">${errorMessage2}</div>
                                    </c:if>

                                    <!-- Table for adding new lesson -->
                                    <table id="item-add" class="w-100">
                                        <tr class="list-item">
                                            <td>
                                                <div class="form-row align-items-center mb-2">
                                                    <div class="col-8">
                                                        <label for="lessonName" class="col-form-label">Lesson Name</label>
                                                        <input class="form-control" type="text" name="lessonName" id="lessonName">
                                                    </div>
                                                    <div class="col-2">
                                                        <label for="lessonPriority" class="col-form-label">Order</label>
                                                        <input class="form-control" type="number" name="lessonPriority" id="lessonPriority" min="1">
                                                    </div>
                                                    <div class="col-2">
                                                        <label class="col-form-label">&nbsp;</label>
                                                        <div class="form-group">
                                                            <a class="delete" href="#"><i class="fa fa-close"></i></a>
                                                        </div>
                                                    </div>
                                                </div>
                                            </td>
                                        </tr>
                                    </table>
                                    <button type="button" class="btn btn-secondary add-item"><i class="fa fa-fw fa-plus-circle"></i> Add Lesson</button>
                                </div>
                            </div>

                            <div class="form-group">
                                <button type="submit" class="btn btn-primary">Submit</button>
                                <button type="reset" class="btn btn-secondary">Reset</button>
                                <a href="${pageContext.request.contextPath}/lesson" class="btn btn-secondary">Cancel</a>
                            </div>

                        </form>
                    </div>
                </div>
            </div>
            <!-- Your Profile Views Chart END-->
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
<script src='/assets/vendors/switcher/switcher.js'></script>

<script type="text/javascript">
    function addField() {
        var container = document.getElementById("fields");
        var input = document.createElement("input");
        input.type = "text";
        input.name = "lessonName";
        input.className = "form-control mt-2";
        container.appendChild(input);
    }

    function clearFields() {
        var container = document.getElementById("fields");
        container.innerHTML = '<input class="form-control" type="text" name="lessonName"/>'; // Reset to a single input field
    }

    // Function to add a new lesson row
    function newMenuItem() {
        var newElem = $('tr.list-item').first().clone();
        newElem.find('input').val('');
        newElem.appendTo('table#item-add');
    }

    $(document).ready(function () {
        $('.add-item').on('click', function (e) {
            e.preventDefault();
            newMenuItem();
        });

        $(document).on("click", "#item-add .delete", function (e) {
            e.preventDefault();
            if ($('tr.list-item').length > 1) { // Check if there is more than one lesson
                $(this).closest('tr').remove();
            } else {
                alert("There must be at least one lesson.");
            }
        });
    });
</script>
</body>

<!-- Mirrored from educhamp.themetrades.com/demo/admin/add-listing.html by HTTrack Website Copier/3.x [XR&CO'2014], Fri, 22 Feb 2019 13:09:05 GMT -->
</html>