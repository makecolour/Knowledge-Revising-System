<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
    <link rel="icon" href="assets/images/favicon.ico" type="image/x-icon" />
    <link rel="shortcut icon" type="image/x-icon" href="assets/images/favicon.png" />

    <!-- PAGE TITLE HERE ============================================= -->
    <title>EduChamp : Education HTML Template </title>

    <!-- MOBILE SPECIFIC ============================================= -->
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!--[if lt IE 9]>
    <script src="assets/js/html5shiv.min.js"></script>
    <script src="assets/js/respond.min.js"></script>
    <![endif]-->

    <!-- All PLUGINS CSS ============================================= -->
    <link rel="stylesheet" type="text/css" href="assets/css/assets.css">

    <!-- TYPOGRAPHY ============================================= -->
    <link rel="stylesheet" type="text/css" href="assets/css/typography.css">

    <!-- SHORTCODES ============================================= -->
    <link rel="stylesheet" type="text/css" href="assets/css/shortcodes/shortcodes.css">

    <!-- STYLESHEETS ============================================= -->
    <link rel="stylesheet" type="text/css" href="assets/css/style.css">
    <link class="skin" rel="stylesheet" type="text/css" href="assets/css/color/color-1.css">
</head>
<body id="bg">
<div class="page-wraper">
    <div id="loading-icon-bx"></div>
    <div class="account-form">
        <div class="account-head" style="background-image:url(assets/images/background/bg2.jpg);">
            <a href="/"><img src="assets/images/logo-white-2.png" alt=""></a>
        </div>
        <div class="account-form-inner">
            <div class="account-container">
                <div class="heading-bx left">
                    <h2 class="title-head">Sign Up <span>Now</span></h2>
                    <p>Login Your Account <a href="/login">Click here</a></p>
                </div>
                <form method="post" action="/register" class="contact-bx">
                    <div class="row placeani">
                        <div class="col-lg-12">
                            <div class="form-group">
                                <div class="input-group">
                                    <label>Your Name</label>
                                    <input name="name" type="text" required="" class="form-control">
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-12">
                            <div class="form-group">
                                <div class="input-group">
                                    <label>Your Email Address</label>
                                    <input name="email" type="email" required="" class="form-control">
                                </div>
                                <c:if test="${errorMessage != null}">
                                    <p style="color: red;font-size: 12px">${errorMessage}</p>
                                </c:if>
                            </div>
                        </div>
                        <div class="col-lg-12">
                            <div class="form-group">
                                <div class="input-group">
                                    <label>Your Password</label>
                                    <div style="display: flex; flex-direction: row; margin-bottom: 2vh;">
                                        <input id="password" name="password" type="password" required="" class="form-control">
                                        <button id="togglePassword" type="button" class="btn btn-secondary">Show</button>
                                    </div>
                                    <p id="passwordError" style="color: red;font-size: 12px"></p>
                                    <div class="progress">
                                        <div id="passwordProgressBar" class="progress-bar" role="progressbar" style="width: 0%;" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100"></div>
                                    </div>
                                </div>
                            </div>

                                <div class="form-group">
                                <div class="input-group">
                                    <label>Re-type Password</label>
                                    <div style="display: flex; flex-direction: row;">
                                        <input id="retypePassword" type="password" required="" class="form-control">
                                        <button id="toggleRetypePassword" type="button" class="btn btn-secondary">Show</button>
                                    </div>
                                    <p id="retypeError" style="color: red;font-size: 12px"></p>
                                </div>
                                </div>
                            </div>
                        <div class="col-lg-12">
                            <div class="form-group">
                                <label for="role">You are:</label>
                                <select id="role" name="role" required>
                                    <option style="color: black" value="STUDENT">Student</option>
                                    <option style="color: black" value="TEACHER">Teacher</option>
                                </select>

                            </div>
                        </div>
                        </div>
                        <div class="col-lg-12 m-b30">
                            <button id="submit" name="submit" type="submit" value="Submit" class="btn button-md"  style="width: 100%;">Sign Up</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
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
<script src="assets/js/functions.js"></script>
<script src="assets/js/contact.js"></script>
<script src='assets/vendors/switcher/switcher.js'></script>
<script type="text/javascript" src="assets/dist/purify.min.js">
    const clean = DOMPurify.sanitize(dirty);
</script>
<script type="text/javascript" src="assets/dist/xss.js"></script>
<script>
    // apply function filterXSS in the same way
    var html = filterXSS('<script>alert("xss");</scr' + "ipt>");
</script>
<script src="/assets/js/password.js"></script>
<script>
    var password = document.getElementById('password');
    var retypePassword = document.getElementById('retypePassword');
    var togglePassword = document.getElementById('togglePassword');
    var toggleRetypePassword = document.getElementById('toggleRetypePassword');
    var submitButton = document.getElementById('submit');
    var passwordMessage = document.getElementById('passwordError');
    var errorMessage = document.getElementById('retypeError');
    var passwordProgressBar = document.getElementById('passwordProgressBar');

    togglePassword.addEventListener('click', function() {
        toggle(password);
    });
    toggleRetypePassword.addEventListener('click', function() {
        toggle(retypePassword)
    });

    retypePassword.addEventListener('input', function() {
        checkRetype(retypePassword, password, submitButton, errorMessage);
    });
    password.addEventListener('input', function() {
        checkRetype(retypePassword, password, submitButton, errorMessage);
    });
    password.addEventListener('input', () => {
        assessPassword(password, passwordMessage, passwordProgressBar, submitButton);
    });

</script>
</body>
</html>
