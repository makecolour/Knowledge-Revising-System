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
                    <%--//PASSWORD CHANGER--%>
                    <form class="edit-profile" method="POST" action="/profile/${user.id}/password">
                        <div class="">
                            <div class="form-group row">
                                <div class="col-sm-10 ml-auto">
                                    <h3>Change Password</h3>
                                </div>
                            </div>
                            <div class="form-group row">
                                <label class="col-sm-2 col-form-label">Current Password</label>
                                <div class="col-sm-7" style="display: flex; flex-direction: row;">
                                    <input class="form-control" type="password" id="oldPassword" name="oldPassword">
                                    <button id="toggleOldPassword" type="button" class="btn btn-secondary">Show
                                    </button>
                                </div>
                            </div>
                            <div class="form-group row">
                                <label class="col-sm-2 col-form-label">New Password</label>
                                <div class="col-sm-7">
                                    <div style="display: flex; flex-direction: row;">
                                        <input class="form-control" type="password" id="password" name="password">
                                        <button id="togglePassword" type="button" class="btn btn-secondary">Show
                                        </button>
                                    </div>
                                    <div style="display: flex; flex-direction: column;">
                                        <p id="passwordError"
                                           style="color: red;font-size: 12px; margin-bottom: 0; line-height: 20px;"></p>
                                        <div class="progress">
                                            <div id="passwordProgressBar" class="progress-bar" role="progressbar"
                                                 style="width: 0%;" aria-valuenow="0" aria-valuemin="0"
                                                 aria-valuemax="100"></div>
                                        </div>
                                    </div>
                                    <c:if test="${passwordError != null}">
                                        <p style="color: red;font-size: 12px">${passwordError }</p>
                                    </c:if>

                                    <c:if test="${passwordSuccess  != null}">
                                        <p style="color: green;font-size: 12px">${passwordSuccess}</p>
                                    </c:if>
                                </div>

                            </div>
                            <div class="form-group row">
                                <label class="col-sm-2 col-form-label">Re-type Password</label>
                                <div class="col-sm-7">
                                    <div style="display: flex; flex-direction: row;">
                                        <input class="form-control" type="password" id="retypePassword" name="retypePassword">
                                        <button id="toggleRetypePassword" type="button" class="btn btn-secondary">
                                            Show
                                        </button>
                                    </div>
                                    <p id="retypeError" style="color: red;font-size: 12px"></p>
                                </div>
                            </div>
                            <div class="form-group row">
                                <label class="col-sm-2 col-form-label">Account Email</label>
                                <div class="col-sm-7" style="display: flex; flex-direction: row;">
                                    <input class="form-control" type="text" id="email" name="email">
                                </div>

                                <p id="mailError" style="color: red"></p>
                            </div>
                            <div class="form-group row">
                                <label class="col-sm-2 col-form-label">Verification Code</label>
                                <div class="col-sm-7" style="display: flex; flex-direction: row;">
                                    <input name="verification" type="number" min="100000" max="999999" required="" class="form-control">
                                    <button id="getCode" type="button" class="btn btn-secondary" onclick="getEmailCode()">Get Verification Code
                                    </button>
                                </div>

                                <p id="codeError" style="color: red"></p>
                            </div>
                        </div>
                        <c:if test="${currentUser.id == user.id}">
                        <div class="row">
                            <div class="col-sm-2">
                            </div>
                            <div class="col-sm-7">
                                <input name="submit" type="submit" value="Save changes" class="btn" id="savePassword"></input>
                                <button type="button" class="btn-secondry" href="/home">Cancel</button>
                            </div>
                        </div>
                        </c:if>
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
<script>
    function getEmailCode() {
        var email = document.getElementById('email').value; // Get the email value
        var getCodeBtn = document.getElementById('getCode'); // Get the button element
        var messageElement = document.getElementById('mailError'); // Assuming an element with id 'codeError' exists for displaying messages

        // Clear previous messages
        messageElement.textContent = '';

        // Disable the button to prevent multiple clicks
        getCodeBtn.disabled = true;

        // Perform a GET request to the server
        fetch('/get-verify-code?email=' + encodeURIComponent(email), {
            method: 'GET'
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.text(); // or response.json() if your server responds with JSON
            })
            .then(data => {
                console.log('Verification code sent:', data);
                // Display success message
                messageElement.textContent = 'Verification code sent successfully.';
                messageElement.style.color = 'green'; // Optional: Change text color for success messages
            })
            .catch((error) => {
                console.error('Error:', error);
                // Display error message
                messageElement.textContent = 'Failed to send verification code. Please try again.';
                messageElement.style.color = 'red'; // Optional: Change text color for error messages
            })
            .finally(() => {
                // Re-enable the button after the request is complete
                getCodeBtn.disabled = false;
            });
    }
</script>
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

    var oldPassword = document.getElementById('oldPassword');
    var password = document.getElementById('password');
    var retypePassword = document.getElementById('retypePassword');
    var toggleOldPassword = document.getElementById('toggleOldPassword');
    var togglePassword = document.getElementById('togglePassword');
    var toggleRetypePassword = document.getElementById('toggleRetypePassword');
    var submitButton = document.getElementById('savePassword');
    var passwordMessage = document.getElementById('passwordError');
    var errorMessage = document.getElementById('retypeError');
    var passwordProgressBar = document.getElementById('passwordProgressBar');

    toggleOldPassword.addEventListener('click', () => {
        toggle(oldPassword);
    });
    toggleRetypePassword.addEventListener('click', () => {
        toggle(retypePassword);
    });
    togglePassword.addEventListener('click', () => {
        toggle(password);
    });

    retypePassword.addEventListener('input', function () {
        checkRetype(retypePassword, password, submitButton, errorMessage);
        if (oldPassword.value === '' || password.value === '' || retypePassword.value === '') {
            submitButton.disabled = true;
        }
    });

    password.addEventListener('input', () => {
        checkRetype(retypePassword, password, submitButton, errorMessage);
        assessPassword(password, passwordMessage, passwordProgressBar, submitButton);
        if (oldPassword.value === '' || password.value === '' || retypePassword.value === '') {
            submitButton.disabled = true;
        }
    });

    oldPassword.addEventListener('input', () => {
        if (oldPassword.value === '' || password.value === '' || retypePassword.value === '') {
            submitButton.disabled = true;
        }
    });

</script>
</body>

<!-- Mirrored from educhamp.themetrades.com/demo/admin/user-profile.html by HTTrack Website Copier/3.x [XR&CO'2014], Fri, 22 Feb 2019 13:11:35 GMT -->
</html>