<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- header start -->
<header class="ttr-header">
    <div class="ttr-header-wrapper">
        <!--sidebar menu toggler start -->
        <div class="ttr-toggle-sidebar ttr-material-button">
            <i class="ti-close ttr-open-icon"></i>
            <i class="ti-menu ttr-close-icon"></i>
        </div>
        <!--sidebar menu toggler end -->
        <!--logo start -->
        <div class="ttr-logo-box">
            <div>
                <a href="/home" class="ttr-logo">
                    <img alt="" class="ttr-logo-mobile" src="/assets/images/logo-mobile.png" width="30" height="30">
                    <img alt="" class="ttr-logo-desktop" src="/assets/images/logo-white.png" width="160" height="27">
                </a>
            </div>
        </div>
        <!--logo end -->
        <div class="ttr-header-menu">
            <!-- header left menu start -->
            <ul class="ttr-header-navigation">
                <li>
                    <a href="../home" class="ttr-material-button ttr-submenu-toggle">HOME</a>
                </li>
                <li>
                    <a href="#" class="ttr-material-button ttr-submenu-toggle">QUICK MENU <i class="fa fa-angle-down"></i></a>
                    <div class="ttr-header-submenu">
                        <ul>
                            <li><a href="../course">Our Courses</a></li>
                        </ul>
                    </div>
                </li>
            </ul>
            <!-- header left menu end -->
        </div>
        <div class="ttr-header-right ttr-with-seperator">
            <!-- header right menu start -->
            <ul class="ttr-header-navigation">
                <li>
                    <a href="#" class="ttr-material-button ttr-search-toggle"><i class="fa fa-search"></i></a>
                </li>
                <li>
                    <a href="#" class="ttr-material-button ttr-submenu-toggle"><i class="fa fa-bell"></i></a>
                    <div class="ttr-header-submenu noti-menu">
                        <div class="ttr-notify-header">
                            <span class="ttr-notify-text-top">9 New</span>
                            <span class="ttr-notify-text">User Notifications</span>
                        </div>
                        <div class="noti-box-list">
                            <ul>
                                <li>
										<span class="notification-icon dashbg-gray">
											<i class="fa fa-check"></i>
										</span>
                                    <span class="notification-text">
											<span>Sneha Jogi</span> sent you a message.
										</span>
                                    <span class="notification-time">
											<a href="#" class="fa fa-close"></a>
											<span> 02:14</span>
										</span>
                                </li>
                                <li>
										<span class="notification-icon dashbg-yellow">
											<i class="fa fa-shopping-cart"></i>
										</span>
                                    <span class="notification-text">
											<a href="#">Your order is placed</a> sent you a message.
										</span>
                                    <span class="notification-time">
											<a href="#" class="fa fa-close"></a>
											<span> 7 Min</span>
										</span>
                                </li>
                                <li>
										<span class="notification-icon dashbg-red">
											<i class="fa fa-bullhorn"></i>
										</span>
                                    <span class="notification-text">
											<span>Your item is shipped</span> sent you a message.
										</span>
                                    <span class="notification-time">
											<a href="#" class="fa fa-close"></a>
											<span> 2 May</span>
										</span>
                                </li>
                                <li>
										<span class="notification-icon dashbg-green">
											<i class="fa fa-comments-o"></i>
										</span>
                                    <span class="notification-text">
											<a href="#">Sneha Jogi</a> sent you a message.
										</span>
                                    <span class="notification-time">
											<a href="#" class="fa fa-close"></a>
											<span> 14 July</span>
										</span>
                                </li>
                                <li>
										<span class="notification-icon dashbg-primary">
											<i class="fa fa-file-word-o"></i>
										</span>
                                    <span class="notification-text">
											<span>Sneha Jogi</span> sent you a message.
										</span>
                                    <span class="notification-time">
											<a href="#" class="fa fa-close"></a>
											<span> 15 Min</span>
										</span>
                                </li>
                            </ul>
                        </div>
                    </div>
                </li>
                <li>
                    <a href="#" class="ttr-material-button ttr-submenu-toggle"><span class="ttr-user-avatar"><img alt="" src="/assets/images/testimonials/pic3.jpg" width="32" height="32"></span></a>
                    <div class="ttr-header-submenu">
                        <ul>
                            <li><a href="/profile">My profile</a></li>
                            <li><a href="list-view-calendar.html">Activity</a></li>
                            <li><a href="mailbox.html">Messages</a></li>
                            <li><a href="/logout">Logout</a></li>
                        </ul>
                    </div>
                </li>
                <li class="ttr-hide-on-mobile">
                    <a href="#" class="ttr-material-button"><i class="ti-layout-grid3-alt"></i></a>
                    <div class="ttr-header-submenu ttr-extra-menu">
                        <a href="#">
                            <i class="fa fa-music"></i>
                            <span>Musics</span>
                        </a>
                        <a href="#">
                            <i class="fa fa-youtube-play"></i>
                            <span>Videos</span>
                        </a>
                        <a href="#">
                            <i class="fa fa-envelope"></i>
                            <span>Emails</span>
                        </a>
                        <a href="#">
                            <i class="fa fa-book"></i>
                            <span>Reports</span>
                        </a>
                        <a href="#">
                            <i class="fa fa-smile-o"></i>
                            <span>Persons</span>
                        </a>
                        <a href="#">
                            <i class="fa fa-picture-o"></i>
                            <span>Pictures</span>
                        </a>
                    </div>
                </li>
            </ul>
            <!-- header right menu end -->
        </div>
        <!--header search panel start -->
        <div class="ttr-search-bar">
            <form class="ttr-search-form">
                <div class="ttr-search-input-wrapper">
                    <input type="text" name="qq" placeholder="search something..." class="ttr-search-input">
                    <button type="submit" name="search" class="ttr-search-submit"><i class="ti-arrow-right"></i></button>
                </div>
                <span class="ttr-search-close ttr-search-toggle">
						<i class="ti-close"></i>
					</span>
            </form>
        </div>
        <!--header search panel end -->
    </div>
</header>
<!-- header end -->
<!-- Left sidebar menu start -->
<div class="ttr-sidebar">
    <div class="ttr-sidebar-wrapper content-scroll">
        <!-- side menu logo start -->
        <div class="ttr-sidebar-logo">
            <a href="#"><img alt="" src="/assets/images/logo.png" width="122" height="27"></a>
            <!-- <div class="ttr-sidebar-pin-button" title="Pin/Unpin Menu">
                <i class="material-icons ttr-fixed-icon">gps_fixed</i>
                <i class="material-icons ttr-not-fixed-icon">gps_not_fixed</i>
            </div> -->
            <div class="ttr-sidebar-toggle-button">
                <i class="ti-arrow-left"></i>
            </div>
        </div>
        <!-- side menu logo end -->
        <!-- sidebar menu start -->
        <nav class="ttr-sidebar-navi">
            <ul>
                <li>
                    <a href="/dashboard" class="ttr-material-button">
                        <span class="ttr-icon"><i class="ti-home"></i></span>
                        <span class="ttr-label">Dashboard</span>
                    </a>
                </li>

                <li>
                    <a href="#" class="ttr-material-button">
                        <span class="ttr-icon"><i class="ti-book"></i></span>
                        <span class="ttr-label">Course</span>
                        <span class="ttr-arrow-icon"><i class="fa fa-angle-down"></i></span>
                    </a>
                    <ul>
                        <li>
                            <a href="${pageContext.request.contextPath}/course-admin" class="ttr-material-button"><span class="ttr-label">Course List</span></a>
                        </li>
                        <li>
                            <a href="${pageContext.request.contextPath}/course-adding" class="ttr-material-button"><span class="ttr-label">Add Course</span></a>
                        </li>
                    </ul>
                </li>
                <li>
                    <a href="#" class="ttr-material-button">
                        <span class="ttr-icon"><i class="ti-layout-accordion-list"></i></span>
                        <span class="ttr-label">Subject</span>
                        <span class="ttr-arrow-icon"><i class="fa fa-angle-down"></i></span>
                    </a>
                    <ul>
                        <li>
                            <a href="${pageContext.request.contextPath}/subject" class="ttr-material-button"><span class="ttr-label">Subjects List</span></a>
                        </li>
                        <li>
                            <a href="${pageContext.request.contextPath}/subject/adding" class="ttr-material-button"><span class="ttr-label">Add Subjects</span></a>
                        </li>
                    </ul>
                </li>
                <li>
                    <a href="#" class="ttr-material-button">
                        <span class="ttr-icon"><i class="ti-direction"></i></span>
                        <span class="ttr-label">Setting</span>
                        <span class="ttr-arrow-icon"><i class="fa fa-angle-down"></i></span>
                    </a>
                    <ul>
                        <li>
                            <a href="${pageContext.request.contextPath}/setting" class="ttr-material-button"><span class="ttr-label">Setting List</span></a>
                        </li>
                        <li>
                            <a href="${pageContext.request.contextPath}/setting/adding" class="ttr-material-button"><span class="ttr-label">Add Setting</span></a>
                        </li>
                    </ul>
                </li>
                <li>
                    <a href="#" class="ttr-material-button">
                        <span class="ttr-icon"><i class="ti-line-dashed"></i></span>
                        <span class="ttr-label">Lesson</span>
                        <span class="ttr-arrow-icon"><i class="fa fa-angle-down"></i></span>
                    </a>
                    <ul>
                        <li>
                            <a href="${pageContext.request.contextPath}/lesson" class="ttr-material-button"><span class="ttr-label">Lesson List</span></a>
                        </li>
                        <li>
                            <a href="${pageContext.request.contextPath}/lesson-adding" class="ttr-material-button"><span class="ttr-label">Add Lesson</span></a>
                        </li>
                    </ul>
                </li>
                <li>
                    <a href="#" class="ttr-material-button">
                        <span class="ttr-icon"><i class="ti-headphone"></i></span>
                        <span class="ttr-label">Exam</span>
                        <span class="ttr-arrow-icon"><i class="fa fa-angle-down"></i></span>
                    </a>
                    <ul>
                        <li>
                            <a href="${pageContext.request.contextPath}/exam-list" class="ttr-material-button"><span class="ttr-label">Exam List</span></a>
                        </li>
                        <li>
                            <a href="${pageContext.request.contextPath}/exam-table/adding" class="ttr-material-button"><span class="ttr-label">Add Exam</span></a>
                        </li>
                    </ul>
                </li>

                <li>
                    <a href="/profile" class="ttr-material-button">
                        <span class="ttr-icon"><i class="ti-user"></i></span>
                        <span class="ttr-label">My Profile</span>
                    </a>
                </li>
                <li class="ttr-seperate"></li>
            </ul>
            <!-- sidebar menu end -->
        </nav>
        <!-- sidebar menu end -->
    </div>
</div>
<!-- Left sidebar menu end -->
