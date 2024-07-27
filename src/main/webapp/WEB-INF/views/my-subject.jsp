<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <title>EduChamp : Education HTML Template </title>

    <!-- MOBILE SPECIFIC ============================================= -->
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!--[if lt IE 9]>
    <script src="/assets/js/html5shiv.min.js"></script>
    <script src="/assets/js/respond.min.js"></script>
    <![endif]-->

    <!-- All PLUGINS CSS ============================================= -->
    <link rel="stylesheet" type="text/css" href="/assets/css/assets.css">

    <!-- TYPOGRAPHY ============================================= -->
    <link rel="stylesheet" type="text/css" href="/assets/css/typography.css">

    <!-- SHORTCODES ============================================= -->
    <link rel="stylesheet" type="text/css" href="/assets/css/shortcodes/shortcodes.css">

    <!-- STYLESHEETS ============================================= -->
    <link rel="stylesheet" type="text/css" href="/assets/css/style.css">
    <link class="skin" rel="stylesheet" type="text/css" href="/assets/css/color/color-1.css">

</head>
<body id="bg">
<div class="page-wraper">
    <div id="loading-icon-bx"></div>

    <!-- Header Top ==== -->
    <jsp:include page="header.jsp"></jsp:include>
    <!-- header END ==== -->
    <!-- Content -->
    <div class="page-content bg-white">
        <!-- inner page banner -->
        <div class="page-banner ovbl-dark" style="background-image:url(assets/images/banner/banner3.jpg);">
            <div class="container">
                <div class="page-banner-entry">
                    <h1 class="text-white">My Courses</h1>
                </div>
            </div>
        </div>
        <!-- Breadcrumb row -->
        <div class="breadcrumb-row">
            <div class="container">
                <ul class="list-inline">
                    <li><a href="#">Home</a></li>
                    <li>My Courses</li>
                </ul>
            </div>
        </div>
        <!-- Breadcrumb row END -->
        <!-- inner page banner END -->
        <div class="content-block">
            <!-- About Us -->
            <div class="section-area section-sp1">
                <div class="container">
                    <div class="row">
                        <div class="col-lg-3 col-md-4 col-sm-12 m-b30">

                            <%--                            search course by name--%>

                            <div class="widget courses-search-bx placeani">
                                <div class="form-group">
                                    <div class="input-group">
                                        <form action="${pageContext.request.contextPath}/my-subject/search" method="get">
                                            <div class="input-group">
                                                <label>Search Courses</label>
                                                <input name="name" type="text" class="form-control"
                                                       value="${sessionScope.name}">
                                            </div>

                                        </form>
                                    </div>
                                </div>
                            </div>

                            <%--                            search course by subject--%>

                            <div class="widget widget_archive">
                                <h5 class="widget-title style-1">All Subject</h5>
                                <ul>
                                    <c:if test="${empty subjectSet}">
                                        <p>No subject.</p>
                                    </c:if>
                                    <c:if test="${not empty subjectSet}">
                                        <li><a href="${pageContext.request.contextPath}/my-subject">General</a></li>
                                        <c:forEach items="${specialitySet}" var="sub">
                                            <li>
                                                <a href="${pageContext.request.contextPath}/my-subject/search?name=${param.name}&speciality=${param.speciality}&status=true> ${sub.speciality}</a>
                                            </li>
                                        </c:forEach>
                                    </c:if>
                                </ul>
                            </div>

                            <div class="widget">
                                <a href="#"><img src="/assets/images/adv/adv.jpg" alt=""/></a>
                            </div>
                        </div>
                        <%--                            show course --%>


                        <div class="col-lg-9 col-md-8 col-sm-12">
                            <div class="row">
                                <c:if test="${empty myCourseList}">
                                    <p>No course available.</p>
                                </c:if>
                                <c:if test="${not empty subjectList}">
                                    <c:forEach items="${subjectList}" var="myCourse">
                                        <div class="col-md-6 col-lg-4 col-sm-6 m-b30">
                                            <div class="cours-bx">
                                                <div class="action-box">
                                                    <img src="/assets/images/courses/pic1.jpg" alt="">
                                                    <a href="${pageContext.request.contextPath}/course/detail/${myCourse.course.id}"
                                                       class="btn">Read More</a>
                                                </div>
                                                <div class="info-bx text-center">
                                                    <h5>
                                                        <a href="course/detail/${myCourse.id}">${myCourse.course.courseName}</a>
                                                    </h5>
                                                    <span>${myCourse.course.subject.code}</span>
                                                </div>
                                                <div class="cours-more-info">
                                                    <div class="review">
                                                        <span>

                                                        </span>
                                                    </div>
                                                    <div class="price">
                                                        <p>Made by
                                                            <a href="${pageContext.request.contextPath}/profile/${myCourse.course.teacher.id}">${myCourse.course.teacher.fullName}</a>
                                                        </p>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </c:forEach>
                                </c:if>


                            </div>
                            <div aria-label="Page navigation">
                                <ul class="pagination">
                                    <c:if test="${currentPage > 0}">
                                        <li class="page-item">
                                            <a class="page-link" href="${pageContext.request.contextPath}/my-course/search?courseSearch=${param.courseSearch}&subjectId=${param.subjectId}&page=${currentPage - 1}&size=${9}">Previous</a>
                                        </li>
                                    </c:if>
                                    <c:if test="${totalPages > 0}">
                                        <c:forEach var="i" begin="0" end="${totalPages - 1}">
                                            <li class="page-item ${i == currentPage ? 'active' : ''}">
                                                <a class="page-link" href="${pageContext.request.contextPath}/my-course/search?courseSearch=${param.courseSearch}&subjectId=${param.subjectId}&page=${i}&size=${9}">${i+1}</a>
                                            </li>
                                        </c:forEach>
                                    </c:if>
                                    <c:if test="${currentPage < totalPages - 1}">
                                        <li class="page-item">
                                            <a class="page-link" href="${pageContext.request.contextPath}/my-course/search?courseSearch=${param.courseSearch}&subjectId=${param.subjectId}&page=${currentPage + 1}&size=${9}">Next</a>
                                        </li>
                                    </c:if>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- contact area END -->

    </div>
    <!-- Content END-->
    <!-- Footer ==== -->
    <footer>
        <div class="footer-top">
            <div class="pt-exebar">
                <div class="container">
                    <div class="d-flex align-items-stretch">
                        <div class="pt-logo mr-auto">
                            <a href="index.html"><img src="/assets/images/logo-white.png" alt=""/></a>
                        </div>
                        <div class="pt-social-link">
                            <ul class="list-inline m-a0">
                                <li><a href="#" class="btn-link"><i class="fa fa-facebook"></i></a></li>
                                <li><a href="#" class="btn-link"><i class="fa fa-twitter"></i></a></li>
                                <li><a href="#" class="btn-link"><i class="fa fa-linkedin"></i></a></li>
                                <li><a href="#" class="btn-link"><i class="fa fa-google-plus"></i></a></li>
                            </ul>
                        </div>
                        <div class="pt-btn-join">
                            <a href="#" class="btn ">Join Now</a>
                        </div>
                    </div>
                </div>
            </div>
            <div class="container">
                <div class="row">
                    <div class="col-lg-4 col-md-12 col-sm-12 footer-col-4">
                        <div class="widget">
                            <h5 class="footer-title">Sign Up For A Newsletter</h5>
                            <p class="text-capitalize m-b20">Weekly Breaking news analysis and cutting edge advices on
                                job searching.</p>
                            <div class="subscribe-form m-b20">
                                <form class="subscription-form"
                                      action="http://educhamp.themetrades.com/demo/assets/script/mailchamp.php"
                                      method="post">
                                    <div class="ajax-message"></div>
                                    <div class="input-group">
                                        <input name="email" required="required" class="form-control"
                                               placeholder="Your Email Address" type="email">
                                        <span class="input-group-btn">
											<button name="submit" value="Submit" type="submit" class="btn"><i
                                                    class="fa fa-arrow-right"></i></button>
										</span>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                    <div class="col-12 col-lg-5 col-md-7 col-sm-12">
                        <div class="row">
                            <div class="col-4 col-lg-4 col-md-4 col-sm-4">
                                <div class="widget footer_widget">
                                    <h5 class="footer-title">Company</h5>
                                    <ul>
                                        <li><a href="index.html">Home</a></li>
                                        <li><a href="about-1.html">About</a></li>
                                        <li><a href="faq-1.html">FAQs</a></li>
                                        <li><a href="contact-1.html">Contact</a></li>
                                    </ul>
                                </div>
                            </div>
                            <div class="col-4 col-lg-4 col-md-4 col-sm-4">
                                <div class="widget footer_widget">
                                    <h5 class="footer-title">Get In Touch</h5>
                                    <ul>
                                        <li><a href="http://educhamp.themetrades.com/admin/index.html">Dashboard</a>
                                        </li>
                                        <li><a href="blog-classic-grid.html">Blog</a></li>
                                        <li><a href="portfolio.html">Portfolio</a></li>
                                        <li><a href="event.html">Event</a></li>
                                    </ul>
                                </div>
                            </div>
                            <div class="col-4 col-lg-4 col-md-4 col-sm-4">
                                <div class="widget footer_widget">
                                    <h5 class="footer-title">Courses</h5>
                                    <ul>
                                        <li><a href="courses.html">Courses</a></li>
                                        <li><a href="courses-details.html">Details</a></li>
                                        <li><a href="membership.html">Membership</a></li>
                                        <li><a href="profile.html">Profile</a></li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-12 col-lg-3 col-md-5 col-sm-12 footer-col-4">
                        <div class="widget widget_gallery gallery-grid-4">
                            <h5 class="footer-title">Our Gallery</h5>
                            <ul class="magnific-image">
                                <li><a href="/assets/images/gallery/pic1.jpg" class="magnific-anchor"><img
                                        src="/assets/images/gallery/pic1.jpg" alt=""></a></li>
                                <li><a href="/assets/images/gallery/pic2.jpg" class="magnific-anchor"><img
                                        src="/assets/images/gallery/pic2.jpg" alt=""></a></li>
                                <li><a href="/assets/images/gallery/pic3.jpg" class="magnific-anchor"><img
                                        src="/assets/images/gallery/pic3.jpg" alt=""></a></li>
                                <li><a href="/assets/images/gallery/pic4.jpg" class="magnific-anchor"><img
                                        src="/assets/images/gallery/pic4.jpg" alt=""></a></li>
                                <li><a href="/assets/images/gallery/pic5.jpg" class="magnific-anchor"><img
                                        src="/assets/images/gallery/pic5.jpg" alt=""></a></li>
                                <li><a href="/assets/images/gallery/pic6.jpg" class="magnific-anchor"><img
                                        src="/assets/images/gallery/pic6.jpg" alt=""></a></li>
                                <li><a href="/assets/images/gallery/pic7.jpg" class="magnific-anchor"><img
                                        src="/assets/images/gallery/pic7.jpg" alt=""></a></li>
                                <li><a href="/assets/images/gallery/pic8.jpg" class="magnific-anchor"><img
                                        src="/assets/images/gallery/pic8.jpg" alt=""></a></li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="footer-bottom">
            <div class="container">
                <div class="row">
                    <div class="col-lg-12 col-md-12 col-sm-12 text-center"><a target="_blank"
                                                                              href="https://www.templateshub.net">Templates
                        Hub</a></div>
                </div>
            </div>
        </div>
    </footer>
    <!-- Footer END ==== -->
    <button class="back-to-top fa fa-chevron-up"></button>
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
<script src='assets/vendors/switcher/switcher.js'></script>
</body>

</html>
