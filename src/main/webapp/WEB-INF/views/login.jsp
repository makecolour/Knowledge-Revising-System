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
    <link rel="icon" href="//assets/images/favicon.ico" type="image/x-icon" />
    <link rel="shortcut icon" type="image/x-icon" href="/assets/images/favicon.png" />

    <!-- PAGE TITLE HERE ============================================= -->
    <title>EduChamp : Education HTML Template </title>

    <!-- MOBILE SPECIFIC ============================================= -->
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!--[if lt IE 9]>
    <script src="//assets/js/html5shiv.min.js"></script>
    <script src="//assets/js/respond.min.js"></script>
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
    <link rel="stylesheet" type="text/css" href="/assets/css/externalBtn.css">

</head>
<body id="bg">
<div class="page-wraper">
    <div id="loading-icon-bx"></div>
    <div class="account-form">
        <div class="account-head" style="background-image:url(/assets/images/background/bg2.jpg);">
            <a href="/home"><img src="/assets/images/logo-white-2.png" alt=""></a>
        </div>
        <div class="account-form-inner">
            <div class="account-container">
                <div class="heading-bx left">
                    <h2 class="title-head">Login to your <span>Account</span></h2>
                    <p>Don't have an account? <a href="/register">Create one here</a></p>

                </div>
                <div>
                    <c:if test="${param.success}">
                        <div id="myNav" class="overlay" style="height: 100%;
                        width: 0;
                        position: fixed;
                        z-index: 9999;
                        top: 0;
                        left: 0;
                        background-color: rgb(0,0,0);
                        background-color: rgba(0,0,0, 0.9);
                        overflow-x: hidden;
                        transition: 0.5s;">
                            <div class="overlay-content" style="position: relative;
                            top: 30%;
                            left: 27.5%;
                            width: 45%;
                            height: 40%;
                            text-align: center;
                            background-color: white; border-radius: 15px;">
                                <div style="font-size: 55px; color:black; display: flex; justify-content: center; align-items: center; height: 100%; flex-direction: column;">
                                    <a style="font-weight: bold; margin-bottom: 8%;">Logged in successfully</a>
                                    <button class="closebtn button" onclick="closeNav()" style="font-size: 22px; border-radius: 15px;">
                                        <a style="font-size: 22px; padding: 0 8px;">Close</a>
                                    </button>
                                </div>

                            </div>

                        </div>
                        <script type="application/javascript">
                            function openNav() {
                                document.getElementById("myNav").style.width = "100%";
                            }
                            function closeNav() {
                                document.getElementById("myNav").style.width = "0%";
                                setTimeout(() => {
                                    window.location.replace("/home")
                                }, 500)
                            }
                            document.addEventListener('DOMContentLoaded', (event) => {
                                openNav();
                                setTimeout(() => {
                                    closeNav()
                                }, 2000)
                            })

                        </script>
                    </c:if>
                </div>
                <div>
                    <c:if test="${param.error}">
                    <p style="color: red;">Wrong email or password</p>
                                            </c:if>
                </div>
                <form method="post" action="/login" class="contact-bx">
                    <div class="row placeani">
                        <div class="col-lg-12">
                            <div class="form-group">
                                <div class="input-group">
                                    <label>Your Email</label>
                                    <input name="username" type="text" required="" class="form-control">
                                </div>
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
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-12">
                            <div class="form-group form-forget">
                                <div class="custom-control custom-checkbox">
                                    <input type="checkbox" class="custom-control-input" id="customControlAutosizing">
                                    <label class="custom-control-label" for="customControlAutosizing">Remember me</label>
                                </div>
                                <a href="/forget-password" class="ml-auto">Forgot Password?</a>
                            </div>
                        </div>
                        <div class="col-lg-12 m-b30">
                            <button name="submit" type="submit" value="Submit" class="btn button-md" style="width: 100%;">Login</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
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
<script src='/assets/vendors/switcher/switcher.js'></script>
<script type="text/javascript" src="/assets/dist/purify.min.js">
    const clean = DOMPurify.sanitize(dirty);
</script>
<script type="text/javascript" src="/assets/dist/xss.js"></script>
<script>
    // apply function filterXSS in the same way
    var html = filterXSS('<script>alert("xss");</scr' + "ipt>");
</script>
</body>
<script>
    var password = document.getElementById('password');
    var togglePassword = document.getElementById('togglePassword');

    togglePassword.addEventListener('click', function() {
        if (password.type === 'password') {
            password.type = 'text';
            togglePassword.textContent = 'Hide';
        } else {
            password.type = 'password';
            togglePassword.textContent = 'Show';
        }
    });
</script>
</html>
