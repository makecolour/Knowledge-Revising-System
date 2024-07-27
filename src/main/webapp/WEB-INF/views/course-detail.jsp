<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

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
    <link rel="icon" href="/assets/images/favicon.ico" type="image/x-icon" />
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
        <div class="page-banner ovbl-dark" style="background-image:url(assets/images/banner/banner2.jpg);">
            <div class="container">
                <div class="page-banner-entry">
                    <h1 class="text-white">Courses Details</h1>
                </div>
            </div>
        </div>
        <!-- Breadcrumb row -->
        <div class="breadcrumb-row">
            <div class="container">
                <ul class="list-inline">
                    <li><a href="#">Home</a></li>
                    <li>Courses Details</li>
                </ul>
            </div>
        </div>
        <!-- Breadcrumb row END -->
        <!-- inner page banner END -->
        <div class="content-block">
            <!-- About Us -->
            <div class="section-area section-sp1">
                <div class="container">
                    <div class="row d-flex flex-row-reverse">
                        <div class="col-lg-3 col-md-4 col-sm-12 m-b30">
                            <div class="course-detail-bx">
                                <div class="course-price">
                                    <h4 class="price">${course.subject.code}</h4>
                                </div>
                                <div class="course-buy-now text-center">
                                    <form action="/course/detail/${course.id}/enrollment" method="post" style="display: inline;">
                                        <button type="submit" class="btn radius-xl text-uppercase" on>
                                            ${enroll ? "Marked" : "Mark to My Courses"}
                                        </button>
                                    </form>
                                </div>
                                <div class="teacher-bx">
                                    <div class="teacher-info">
                                        <div class="teacher-thumb">
                                            <img src="/assets/images/testimonials/pic1.jpg" alt=""/>
                                        </div>
                                        <div class="teacher-name">
                                            <a href="/profile/${course.teacher.id}">${course.teacher.fullName}</a>
                                            <br>
                                            <span>${course.teacher.email}</span>
                                        </div>
                                    </div>
                                </div>
                                <div class="cours-more-info">
                                    <div class="review">
                                        <span>3 Review</span>
                                        <ul class="cours-star">
                                            <li class="active"><i class="fa fa-star"></i></li>
                                            <li class="active"><i class="fa fa-star"></i></li>
                                            <li class="active"><i class="fa fa-star"></i></li>
                                            <li><i class="fa fa-star"></i></li>
                                            <li><i class="fa fa-star"></i></li>
                                        </ul>
                                    </div>
                                    <div class="price categories">
                                        <span>Categories</span>
                                        <h5 class="text-primary">Frontend</h5>
                                    </div>
                                </div>
                                <div class="course-info-list scroll-page">
                                    <ul class="navbar">
                                        <li><a class="nav-link" href="#overview"><i class="ti-zip"></i>Overview</a></li>
                                        <li><a class="nav-link" href="#curriculum"><i class="ti-bookmark-alt"></i>Curriculum</a></li>
                                        <li><a class="nav-link" href="#instructor"><i class="ti-user"></i>Instructor</a></li>
                                        <li><a class="nav-link" href="#reviews"><i class="ti-comments"></i>Reviews</a></li>
                                    </ul>
                                </div>
                            </div>
                        </div>

                        <div class="col-lg-9 col-md-8 col-sm-12">
                            <div class="courses-post">
                                <div class="ttr-post-media media-effect">
                                    <a href="#"><img src="/assets/images/blog/default/thum1.jpg" alt=""></a>
                                </div>
                                <div class="ttr-post-info">
                                    <div class="ttr-post-title ">
                                        <h2 class="post-title">${course.courseName}</h2>
                                    </div>
                                    <div class="ttr-post-text">
                                        <p>${course.subject.description}</p>
                                    </div>
                                </div>
                            </div>
                            <div class="courese-overview" id="overview">
                                <h4>Overview</h4>
                                <div class="row">
                                    <div class="col-md-12 col-lg-4">
                                        <ul class="course-features">
                                            <li><i class="ti-book"></i> <span class="label">Lectures</span> <span class="value">${lessonList.size()}</span></li>
                                            <li><i class="ti-help-alt"></i> <span class="label">Quizzes</span> <span class="value">1</span></li>
                                            <li><i class="ti-smallcap"></i> <span class="label">Language</span> <span class="value">English</span></li>
                                            <li><i class="ti-user"></i> <span class="label"><a href="participant/${course.id}">Participant</a></span> <span class="value">${enrollmentList.size()}</span></li>
                                        </ul>
                                    </div>

                                    <div class="col-md-12 col-lg-8">
                                        <h5 class="m-b5">Course Description</h5>
                                        <p>${course.description}</p>
                                    </div>
                                </div>
                            </div>



                            <div class="m-b30" id="curriculum">
                                <h4>Curriculum</h4>
                                <ul class="curriculum-list">
                                    <c:forEach items="${lessonList}" var="lesson">
                                        <li>
                                            <div style="display: flex; flex-direction: row; justify-content: space-between">
                                                <h5>${lesson.name}</h5>
                                                <div style="display: flex; flex-direction: row;">

                                                    <button type="submit" class="btn btn-primary" onclick="window.location.href='/flash-cards/${lesson.id}'">View Study Sets</button>
                                                </div>
                                            </div>
                                        </li>
                                    </c:forEach>
                                </ul>
                            </div>

                            <div>
                                <h4>Exams</h4>
                                <div style="display: flex; flex-direction: row; justify-content: space-between">
                                    <form action="${pageContext.request.contextPath}/course/detail/${course.id}" method="get"
                                          class="form-group col-8">
                                        <div class="d-flex flex-row align-items-center">
                                            <div class="mr-2">
                                                <input name="name" type="text" class="form-control" placeholder="Enter setting name"
                                                       value="${sessionScope.name}"></div>
                                            <div class="col-auto">
                                                <button type="submit" class="btn btn-primary">Search</button>
                                            </div>
                                        </div>
                                    </form>
                                </div>
                                <div class="accordion">
                                <c:forEach items="${examList}" var="item" varStatus="status">
                                    <div class="card">
                                        <div class="card-header" id="heading${status.index}">
                                            <h5 class="mb-0">
                                                <button class="btn btn-link" data-toggle="collapse" data-target="#collapse${status.index}" aria-expanded="true" aria-controls="collapse${status.index}">
                                                        ${item.name}
                                                </button>
                                            </h5>
                                        </div>

                                        <div id="collapse${status.index}" class="collapse ${status.index == 0 ? 'show' : ''}" aria-labelledby="heading${status.index}" data-parent="#accordion">
                                            <div class="card-body">
                                                <div style="display: flex; flex-direction: row">
                                                    <h5>Description:</h5>
                                                    <p style="margin:0;margin-left: 5px;">${item.description}</p>
                                                </div>
                                                <div style="display: flex; flex-direction: row">
                                                    <h5>Length:</h5>
                                                    <p style="margin:0;margin-left: 5px;">${item.length}</p>
                                                </div>
                                                <div style="display: flex; flex-direction: row">
                                                    <h5>Number of Questions:</h5>
                                                    <p style="margin:0;margin-left: 5px;">${item.numberOfQuestions}</p>
                                                </div>
                                                <div style="display: flex; flex-direction: row">
                                                    <h5>Status:</h5>
                                                    <p style="margin:0;margin-left: 5px;">${item.status}</p>
                                                </div>
                                                <div style="display: flex; flex-direction: row">
                                                    <button type="submit" class="btn btn-primary" onclick="window.location.href='/exam=${item.id}'">View Exam</button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>
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


                            <div class="" id="instructor">
                                <h4>Instructor</h4>
                                <div class="instructor-bx">
                                    <div class="instructor-author">
                                        <img src="/assets/images/testimonials/pic1.jpg" alt="">
                                    </div>
                                    <div class="instructor-info">
                                        <h6><a href="/profile/${course.teacher.id}">${course.teacher.fullName}</a></h6>
                                        <span>Professor</span>
                                        <p class="m-b0"> ${course.teacher.note}</p>
                                    </div>
                                </div>
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
                            <p class="text-capitalize m-b20">Weekly Breaking news analysis and cutting edge advices on job searching.</p>
                            <div class="subscribe-form m-b20">
                                <form class="subscription-form" action="http://educhamp.themetrades.com/demo/assets/script/mailchamp.php" method="post">
                                    <div class="ajax-message"></div>
                                    <div class="input-group">
                                        <input name="email" required="required"  class="form-control" placeholder="Your Email Address" type="email">
                                        <span class="input-group-btn">
											<button name="submit" value="Submit" type="submit" class="btn"><i class="fa fa-arrow-right"></i></button>
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
                                        <li><a href="http://educhamp.themetrades.com/admin/index.html">Dashboard</a></li>
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
                                <li><a href="/assets/images/gallery/pic1.jpg" class="magnific-anchor"><img src="/assets/images/gallery/pic1.jpg" alt=""></a></li>
                                <li><a href="/assets/images/gallery/pic2.jpg" class="magnific-anchor"><img src="/assets/images/gallery/pic2.jpg" alt=""></a></li>
                                <li><a href="/assets/images/gallery/pic3.jpg" class="magnific-anchor"><img src="/assets/images/gallery/pic3.jpg" alt=""></a></li>
                                <li><a href="/assets/images/gallery/pic4.jpg" class="magnific-anchor"><img src="/assets/images/gallery/pic4.jpg" alt=""></a></li>
                                <li><a href="/assets/images/gallery/pic5.jpg" class="magnific-anchor"><img src="/assets/images/gallery/pic5.jpg" alt=""></a></li>
                                <li><a href="/assets/images/gallery/pic6.jpg" class="magnific-anchor"><img src="/assets/images/gallery/pic6.jpg" alt=""></a></li>
                                <li><a href="/assets/images/gallery/pic7.jpg" class="magnific-anchor"><img src="/assets/images/gallery/pic7.jpg" alt=""></a></li>
                                <li><a href="/assets/images/gallery/pic8.jpg" class="magnific-anchor"><img src="/assets/images/gallery/pic8.jpg" alt=""></a></li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="footer-bottom">
            <div class="container">
                <div class="row">
                    <div class="col-lg-12 col-md-12 col-sm-12 text-center"><a target="_blank" href="https://www.templateshub.net">Templates Hub</a></div>
                </div>
            </div>
        </div>
    </footer>
    <!-- Footer END ==== -->
    <button class="back-to-top fa fa-chevron-up" ></button>
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
<script src="/assets/js/jquery.scroller.js"></script>
<script src="/assets/js/functions.js"></script>
<script src="/assets/js/contact.js"></script>
<script src="/assets/vendors/switcher/switcher.js"></script>
</body>

</html>
