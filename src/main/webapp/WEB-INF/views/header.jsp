<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<header class="header rs-nav">
    <div class="top-bar">
        <div class="container">
            <div class="row d-flex justify-content-between">
                <div class="topbar-left">
                    <ul>
                        <li><a href="/contact"><i class="fa fa-question-circle"></i>Ask a Question</a></li>
                    </ul>
                </div>
                <div class="topbar-right">
                    <ul>
                        <c:if test="${user != null}">
                            <li>Welcome, <a href="/profile">${user.fullName}</a>!</li>
                            <li><a href="/change-password">Change Password</a></li>
                            <li><a href="/logout">Logout</a></li>
                        </c:if>
                        <c:if test="${user == null}">
                            <li><a href="/login">Login</a></li>
                            <li><a href="/register">Register</a></li>
                        </c:if>
                    </ul>
                </div>
            </div>
        </div>
    </div>
    <div class="sticky-header navbar-expand-lg">
        <div class="menu-bar clearfix">
            <div class="container clearfix">
                <!-- Header Logo ==== -->
                <div class="menu-logo">
                    <a href="/landing"><img src="assets/images/logo.png" alt=""></a>
                </div>
                <!-- Mobile Nav Button ==== -->
                <button class="navbar-toggler collapsed menuicon justify-content-end" type="button"
                        data-toggle="collapse" data-target="#menuDropdown" aria-controls="menuDropdown"
                        aria-expanded="false" aria-label="Toggle navigation">
                    <span></span>
                    <span></span>
                    <span></span>
                </button>
                <!-- Author Nav ==== -->
                <div class="secondary-menu">
                    <div class="secondary-inner menu-links navbar-collapse collapse justify-content-start">
                        <ul class="nav navbar-nav">
                            <li class="add-mega-menu"><a href="javascript:;" style="padding: 0; margin-right: 1em;"><i class="fa fa-plus" style="font-size: medium"></i></a>
                                <ul class="sub-menu add-menu" style="display: flex; flex-direction: column; width: 320px">
                                    <c:if test="${user != null}">
                                        <li style="width: 100%"><a href="#">Create Public Study Sets</a></li>
                                        <li style="width: 100%"><a href="${pageContext.request.contextPath}/exam-table/adding">Create Public Exams</a></li>
                                    </c:if>

                                    <c:if test="${user == null}">
                                        <li style="width: 100%"><a href="/login">Login to Share Knowledge :D</a></li>
                                    </c:if>
                                </ul>
                            </li>
                            <!-- Search Button ==== -->
                            <li class="search-btn">
                                <button id="quik-search-btn" type="button" class="btn-link"><i class="fa fa-search"  style="font-size: medium"></i>
                                </button>
                            </li>
                        </ul>
                    </div>
                </div>
                <!-- Search Box ==== -->
                <div class="nav-search-bar">
                    <form action="/header-search" method="post">
                        <input name="search" value="" type="text" class="form-control" placeholder="Type to search">
                        <span><i class="ti-search"></i></span>
                    </form>
                    <span id="search-remove"><i class="ti-close"></i></span>
                </div>
                <!-- Navigation Menu ==== -->
                <div class="menu-links navbar-collapse collapse justify-content-start" id="menuDropdown">
                    <div class="menu-logo">
                        <a href="index.html"><img src="assets/images/logo.png" alt=""></a>
                    </div>
                    <ul class="nav navbar-nav">
                        <li><a href="${pageContext.request.contextPath}/home">Home</a></li>
                        <li class="add-mega-menu"><a href="javascript:;">Our Courses <i class="fa fa-chevron-down"></i></a>
                            <ul class="sub-menu add-menu">
                                <li class="add-menu-left">
                                    <h5 class="menu-adv-title">Our Courses</h5>
                                    <ul>
                                        <li><a href="${pageContext.request.contextPath}/course">Courses </a></li>
                                        <li><a href="${pageContext.request.contextPath}/my-course">My Courses </a></li>
                                        <c:if test="${user != null && user.role.name == 'STUDENT'}">
                                            <li><a href="/exam-list">My Exam</a></li>
                                        </c:if>
                                    </ul>
                                </li>
                                <li class="add-menu-right">
                                    <img src="assets/images/adv/adv.jpg" alt=""/>
                                </li>
                            </ul>
                        </li>
                        <c:if test="${user != null && user.role.name != 'STUDENT'}">
                        <li><a href="${pageContext.request.contextPath}/dashboard">DashBoard</a></li>
                        </c:if>


                    </ul>
                </div>
                <!-- Navigation Menu END ==== -->
            </div>
        </div>
    </div>
</header>