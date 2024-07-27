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

</head>
<body class="ttr-opened-sidebar ttr-pinned-sidebar">

<jsp:include page="dashboard-header.jsp"></jsp:include>

<!--Main container start -->
<main class="ttr-wrapper">
    <div class="container-fluid">
        <div class="db-breadcrumb">
            <h4 class="breadcrumb-title">Add Setting</h4>
            <ul class="db-breadcrumb-list">
                <li><a href="#"><i class="fa fa-home"></i>Home</a></li>
                <li>Add Exam</li>
            </ul>
        </div>
        <div class="row">
            <!-- Your Profile Views Chart -->
            <div class="col-lg-12 m-b30">
                <div class="widget-box">
                    <div class="wc-title">
                        <h4>Add Subject</h4>
                    </div>
                    <div class="widget-inner">
                        <form class="edit-profile m-b30" method="post"
                              action="${pageContext.request.contextPath}/subject/adding">
                            <div class="row">
                                <div class="col-12">
                                    <div class="ml-auto">
                                        <h3>1. Basic info</h3>
                                    </div>
                                </div>

                                <div class="form-group col-6">

                                    <c:if test="${not empty errorName}">
                                        <div class="alert alert-danger">${errorName}</div>
                                    </c:if>
                                    <label class="col-form-label">Name</label>
                                    <input class="form-control" type="text" value="" name="name">
                                    <c:if test="${not empty errorCode}">
                                        <div class="alert alert-danger">${errorCode}</div>
                                    </c:if>
                                    <c:if test="${not empty errorMessage}">
                                        <div class="alert alert-danger">${errorMessage}</div>
                                    </c:if>
                                    <label class="col-form-label">Code</label>
                                    <input class="form-control" type="text" value="" name="code">
                                </div>



                                <div class="form-group col-6">

                                    <div class="form-group">
                                        <label class="col-form-label">Manager</label>
                                        <select name="manager" class="form-control">
                                            <c:forEach items="${managerList}" var="manager">
                                                <option value="${manager.id}"
                                                        <c:if test="${manager.id == param.manager}">selected</c:if>>
                                                        ${manager.fullName} - ${manager.id}
                                                </option>
                                            </c:forEach>
                                        </select>
                                    </div>

                                    <div>
                                        <label class="col-form-label">Speciality</label>
                                        <select name="speciality" class="form-control">
                                            <c:forEach items="${specialitySet}" var="role">
                                                <option value="${role.id}"
                                                        <c:if test="${role.id == param.speciality}">selected</c:if>>
                                                        ${role.name}
                                                </option>
                                            </c:forEach>
                                        </select>
                                    </div>

                                </div>

                                <div class="col-12 m-t20">
                                    <div class="ml-auto m-b5">
                                        <h3>2. Description</h3>
                                    </div>
                                </div>
                                <div class="form-group col-12">
                                    <label class="col-form-label">Subject description</label>
                                    <div>
                                        <textarea class="form-control"
                                                  name="description">${sessionScope.description} </textarea>
                                    </div>
                                </div>

                                <div class="form-group col-12">
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

                                <div class="col-12">
                                    <button type="submit" class="btn">Submit</button>
                                    <button type="reset" class="btn">Rest</button>
                                    <a href="${pageContext.request.contextPath}/subject" class="btn btn-danger">Cancel</a>

                                </div>
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
        container.appendChild(input);
    }

    function clearFields() {
        var container = document.getElementById("fields");
        container.innerHTML = '<input type="text" name="strings"/>'; // Reset to a single input field
    }
</script>

<script>
    // Pricing add
    function newMenuItem() {
        var newElem = $('tr.list-item').first().clone();
        newElem.find('input').val('');
        newElem.appendTo('table#item-add');
    }

    if ($("table#item-add").is('*')) {
        $('.add-item').on('click', function (e) {
            e.preventDefault();
            newMenuItem();
        });
        $(document).on("click", "#item-add .delete", function (e) {
            e.preventDefault();
            $(this).parent().parent().parent().parent().remove();
        });
    }

</script>
</body>

<!-- Mirrored from educhamp.themetrades.com/demo/admin/add-listing.html by HTTrack Website Copier/3.x [XR&CO'2014], Fri, 22 Feb 2019 13:09:05 GMT -->
</html>