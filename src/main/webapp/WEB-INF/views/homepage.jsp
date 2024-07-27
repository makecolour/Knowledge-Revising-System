<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
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
    <link rel="icon" href="/assets/images/favicon.ico" type="image/x-icon"/>
    <link rel="shortcut icon" type="image/x-icon" href="/assets/images/favicon.png"/>

    <!-- PAGE TITLE HERE ============================================= -->
    <title>EduChamp : Education HTML Template</title>

    <!-- MOBILE SPECIFIC ============================================= -->
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- All PLUGINS CSS ============================================= -->
    <link rel="stylesheet" type="text/css" href="/assets/css/assets.css">

    <!-- TYPOGRAPHY ============================================= -->
    <link rel="stylesheet" type="text/css" href="/assets/css/typography.css">

    <!-- SHORTCODES ============================================= -->
    <link rel="stylesheet" type="text/css" href="/assets/css/shortcodes/shortcodes.css">

    <!-- STYLESHEETS ============================================= -->
    <link rel="stylesheet" type="text/css" href="/assets/css/style.css">
    <link class="skin" rel="stylesheet" type="text/css" href="/assets/css/color/color-1.css">

    <!-- REVOLUTION SLIDER CSS ============================================= -->
    <link rel="stylesheet" type="text/css" href="/assets/vendors/revolution/css/layers.css">
    <link rel="stylesheet" type="text/css" href="/assets/vendors/revolution/css/settings.css">
    <link rel="stylesheet" type="text/css" href="/assets/vendors/revolution/css/navigation.css">
    <!-- REVOLUTION SLIDER END -->
</head>

<body id="bg">
<div class="page-wraper">
    <div id="loading-icon-bx"></div>

    <!-- Header Top ==== -->
    <jsp:include page="header.jsp"></jsp:include>
    <!-- Header Top END ==== -->

    <!-- Content -->
    <div class="page-content bg-white">
        <!-- Main Slider -->
        <div class="section-area section-sp1 ovpr-dark bg-fix online-cours"
             style="background-image:url(/assets/images/background/bg1.jpg);">
            <div class="container">
                <div class="row">
                    <div class="col-md-12 text-center text-white">
                        <h2>Online Courses To Learn</h2>
                        <h5>Own Your Future Learning New Skills Online</h5>
                        <form action="${pageContext.request.contextPath}/course/search" method="get" class="cours-search" style="display: flex; flex-direction: row">
                            <div class="input-group">
                                <input name="courseSearch" placeholder="What do you want to learn today?" type="text" required class="form-control" value="${sessionScope.courseSearch}">
                            </div>
                            <div class="input-group-append">
                                <button class="btn" type="submit">Search</button>
                            </div>
                        </form>
                    </div>
                </div>
                <div class="mw800 m-auto">
                    <div class="row">
                        <div class="col-md-4 col-sm-6">
                            <div class="cours-search-bx m-b30">
                                <div class="icon-box">
                                    <h3><i class="ti-user"></i><span class="counter">5</span>M</h3>
                                </div>
                                <span class="cours-search-text">Over 5 million students</span>
                            </div>
                        </div>
                        <div class="col-md-4 col-sm-6">
                            <div class="cours-search-bx m-b30">
                                <div class="icon-box">
                                    <h3><i class="ti-book"></i><span class="counter">30</span>K</h3>
                                </div>
                                <span class="cours-search-text">30,000 Courses.</span>
                            </div>
                        </div>
                        <div class="col-md-4 col-sm-12">
                            <div class="cours-search-bx m-b30">
                                <div class="icon-box">
                                    <h3><i class="ti-layout-list-post"></i><span class="counter">20</span>K</h3>
                                </div>
                                <span class="cours-search-text">Learn Anything Online.</span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- Main Slider END -->

        <!-- Content Block -->
        <div class="content-block">
            <!-- Popular Courses -->
            <div class="section-area section-sp2 popular-courses-bx">
                <div class="container">
                    <div class="row">
                        <div class="col-md-12 heading-bx left">
                            <h2 class="title-head">Popular <span>Courses</span></h2>
                            <p>It is a long established fact that a reader will be distracted by the readable content of a page</p>
                        </div>
                    </div>
                    <div style="display: flex; flex-direction: row; justify-content: space-between">
                        <form action="${pageContext.request.contextPath}/course-popular/search" method="get" class="form-group col-8" id="searchForm">
                            <div class="d-flex flex-row align-items-center">
                                <div class="mr-2">
                                    <select name="subjectId" class="form-control" id="subjectSelect" onchange="submitForm()">
                                        <option value="">All</option>
                                        <c:forEach items="${subjectSet}" var="sub">
                                            <option value="${sub.id}" <c:if test="${sub.id == param.subjectId}">selected</c:if>>${sub.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="mr-2">
                                    <select name="semesterId" class="form-control" id="semesterSelect" onchange="submitForm()">
                                        <option value="">All</option>
                                        <c:forEach items="${semesterSet}" var="sub">
                                            <option value="${sub.id}" <c:if test="${sub.id == param.semesterId}">selected</c:if>>${sub.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                        </form>
                    </div>
                    <div class="row">
                        <c:if test="${not empty courseList}">
                            <c:forEach items="${courseList}" var="course" varStatus="status" begin="0" end="7">
                                <div class="col-md-6 col-lg-3 col-sm-6 m-b30">
                                    <div class="cours-bx">
                                        <div class="action-box">
                                            <img src="/assets/images/courses/pic1.jpg" alt="">
                                            <a href="${pageContext.request.contextPath}/course/detail/${course.id}"
                                               class="btn">Read More</a>
                                        </div>
                                        <div class="info-bx text-center">
                                            <h5>
                                                <a href="course/detail/${course.id}">${course.courseName}</a>
                                            </h5>
                                            <span>${course.subject.code}</span>
                                        </div>
                                        <div class="cours-more-info">
                                            <div class="review"></div>
                                            <div class="price">
                                                <p>Made by
                                                    <a href="${pageContext.request.contextPath}/profile/${course.teacher.id}">${course.teacher.fullName}</a>
                                                </p>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <c:if test="${status.index == 3}">
                                    <div class="w-100"></div>
                                </c:if>
                            </c:forEach>
                        </c:if>
                    </div>
                </div>
            </div>
            <!-- Popular Courses END -->
        </div>
    </div>
    <!-- Content END-->

    <!-- Footer ==== -->
    <jsp:include page="footer.jsp"></jsp:include>
    <!-- Footer END ==== -->
</div>

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
<script src="/assets/js/functions.js"></script>
<script src="/assets/js/contact.js"></script>
<script src="/assets/vendors/revolution/js/jquery.themepunch.tools.min.js"></script>
<script src="/assets/vendors/revolution/js/jquery.themepunch.revolution.min.js"></script>
<script src="/assets/vendors/revolution/js/extensions/revolution.extension.actions.min.js"></script>
<script src="/assets/vendors/revolution/js/extensions/revolution.extension.carousel.min.js"></script>
<script src="/assets/vendors/revolution/js/extensions/revolution.extension.kenburn.min.js"></script>
<script src="/assets/vendors/revolution/js/extensions/revolution.extension.layeranimation.min.js"></script>
<script src="/assets/vendors/revolution/js/extensions/revolution.extension.migration.min.js"></script>
<script src="/assets/vendors/revolution/js/extensions/revolution.extension.parallax.min.js"></script>
<script src="/assets/vendors/revolution/js/extensions/revolution.extension.video.min.js"></script>

<script>
    function submitForm() {
        document.getElementById("searchForm").submit();
    }
</script>
</body>
</html>