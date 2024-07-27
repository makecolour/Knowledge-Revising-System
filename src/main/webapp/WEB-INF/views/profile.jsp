<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">

<!-- Mirrored from educhamp.themetrades.com/demo/admin/user-profile.html by HTTrack Website Copier/3.x [XR&CO'2014], Fri, 22 Feb 2019 13:11:35 GMT -->
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
            <h4 class="breadcrumb-title">User Profile</h4>
            <ul class="db-breadcrumb-list">
                <li><a href="#"><i class="fa fa-home"></i>Home</a></li>
                <li>User Profile</li>
            </ul>
        </div>
        <div class="row">
            <!-- Your Profile Views Chart -->
            <div class="col-lg-12 m-b30">
                <div class="widget-box">
                    <div class="wc-title">
                        <h4>User Profile</h4>
                    </div>
                    <div class="widget-inner">

                        <%--//PROFILE CHANGE--%>
                        <form class="edit-profile m-b30" method="post" action="/profile/${user.id}/details">
                            <div class="">
                                <div class="form-group row">
                                    <div class="col-sm-10  ml-auto">
                                        <h3>1. Personal Details</h3>
                                    </div>
                                </div>
                                <div class="form-group row" style="display: flex; align-items: center">
                                    <label class="col-sm-2 col-form-label">Avatar</label>
                                    <span class="ttr-user-avatar" style="width: 150px; height: 150px; margin: 0 15px;">
                                        <!-- Switch between alt and src later -->
                                        <img alt="${user.avatar}" src="/assets/images/testimonials/pic3.jpg" width="150"
                                             height="150">
                                    </span>
                                    <c:if test="${currentUser.id == user.id}">
                                        <div class="col-sm-7 avatarBtn" display="none">
                                            <button class="btn">Change Avatar</button>
                                        </div>
                                    </c:if>
                                </div>
                                <div class="form-group row">
                                    <label class="col-sm-2 col-form-label">Full Name</label>
                                    <div class="col-sm-7">
                                        <input id="name" class="form-control" type="text" readonly name="name"
                                               value="${user.fullName}">
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label class="col-sm-2 col-form-label">Role</label>
                                    <div class="col-sm-7">
                                        <input id="job" class="form-control" type="text" readonly
                                               value=${user.role.name}>
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label class="col-sm-2 col-form-label">Email</label>
                                    <div class="col-sm-7" style="display: flex; flex-direction: row">
                                        <input id="email" class="form-control" type="text" readonly value=${user.email}>
                                        <c:if test="${currentUser.id == user.id}">
                                            <button type="button" class="btn btn-secondary toggleEmail" display="none">Change Email</button>
                                        </c:if>
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label class="col-sm-2 col-form-label">Note</label>
                                    <div class="col-sm-7">
                                        <textarea id="note" class="form-control" type="text" name="note"
                                                  readonly>${user.note}</textarea>
                                    </div>
                                </div>
                            </div>

                            <c:if test="${currentUser.id == user.id}">
                            <div class="">
                                <div class="">
                                    <div class="row">
                                        <div class="col-sm-2">
                                        </div>
                                        <div class="col-sm-7 changesBtn" display="none">
                                            <input type="submit" class="btn">Save changes</input>>
                                            <button type="reset" class="btn-secondry">Cancel</button>
                                        </div>

                                        <div class="col-sm-7 editBtn" display="block">
                                            <button type="reset" class="btn">Edit</button>
                                        </div>
                                    </div>
                                    <c:if test="${detailError != null}">
                                        <p style="color: red;font-size: 12px">${detailError}</p>
                                    </c:if>

                                    <c:if test="${detailSuccess != null}">
                                        <p style="color: green;font-size: 12px">${detailSuccess}</p>
                                    </c:if>
                                </div>
                            </div>
                            </c:if>
                        </form>
                    </div>

                    <%--//TEACHER COURSES--%>
                    <c:if test="${user.role.name == 'TEACHER'}">
                    <div class="widget-inner">
                        <div class="edit-profile m-b30">
                            <div class="">
                                <div class="form-group row">
                                    <div class="col-sm-10  ml-auto">
                                        <h3>2. Teacher Table</h3>
                                    </div>
                                </div>

                                <div class="form-group row">
                                    <div class="col-sm-2 col-form-label">
                                        <div style="width: 100%;"></div>
                                    </div>

                                    <div class="col-sm-7">
                                        <table class="table table-bordered">
                                            <thead>
                                            <tr>
                                                <th>Course Name</th>
                                                <th>Course Description</th>
                                                <th>Course Subject</th>
                                                <th>Course Status</th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <c:forEach var="course" items="${listCourse}">
                                                <tr>
                                                    <td><a href="${pageContext.request.contextPath}/course/detail/${course.id}">${course.courseName}</a></td>
                                                    <td>${course.description}</td>
                                                    <td>${course.subject.name}</td>
                                                    <td>${course.status}</td>
                                                </tr>
                                            </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    </c:if>

                    <c:if test="${currentUser.id == user.id}">
                        <button type="button" class="btn btn-primary" onClick="window.location.href='/change-password'">Change Password</button>
                    </c:if>

                    <c:if test="${currentUser.role.name == 'ADMIN' || currentUser.id == user.id}">
                    <div class="form-group row">
                        <div class="col-sm-3">
                            <form class="edit-profile" method="POST" action="/profile/${user.id}/delete">
                                <button type="submit" class="btn btn-secondry"
                                        style="background-color: red; color: white">Delete Profile
                                </button>
                                <label style="color: red; font-size: 12px">This action is irreversible
                                    and will delete all the data associated with this account</label>
                            </form>
                        </div>
                    </div>
                    </c:if>
                </div>
            </div>
        </div>
        <!-- Your Profile Views Chart END-->
    </div>
    </div>
</main>
<div class="ttr-overlay"></div>


<!-- External JavaScripts -->
<script src="/assets/js/password.js"></script>
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
<script type="text/javascript" src="/assets/dist/purify.min.js">
    const clean = DOMPurify.sanitize(dirty);
</script>
<script type="text/javascript" src="/assets/dist/xss.js"></script>
<script>
    // apply function filterXSS in the same way
    var html = filterXSS('<script>alert("xss");</scr' + "ipt>");
</script>
<script>
    const emailBtn = document.querySelector('.toggleEmail') ? document.querySelector('.toggleEmail') : null;
    const changesBtn = document.querySelector('.changesBtn');
    const editBtn = document.querySelector('.editBtn');
    const avatarBtn = document.querySelector('.avatarBtn');

    document.addEventListener("DOMContentLoaded", () => {
        if(emailBtn !== null) {
            emailBtn.style.display = 'none';
        }
        changesBtn.style.display = 'none';
        avatarBtn.style.display = 'none';
        editBtn.style.display = 'block';
    })

    const name = document.getElementById('name');
    const note = document.getElementById('note');


    editBtn.addEventListener('click', () => {
        name.removeAttribute('readonly');
        note.removeAttribute('readonly');

        if(emailBtn !== null) {
            emailBtn.style.display = 'block';
        }
        changesBtn.style.display = 'block';
        avatarBtn.style.display = 'block';
        editBtn.style.display = 'none';
    });

    changesBtn.addEventListener('click', () => {

        //TODO apply the changes
        name.setAttribute('readonly', true);
        note.setAttribute('readonly', true);

        if(emailBtn !== null) {
            emailBtn.style.display = 'none';
        }
        changesBtn.style.display = 'none';
        avatarBtn.style.display = 'none';
        editBtn.style.display = 'block';
    });


</script>
</body>

<!-- Mirrored from educhamp.themetrades.com/demo/admin/user-profile.html by HTTrack Website Copier/3.x [XR&CO'2014], Fri, 22 Feb 2019 13:11:35 GMT -->
</html>