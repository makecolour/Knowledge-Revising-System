<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
        <div class="page-banner ovbl-dark" style="background-image:url(assets/images/banner/banner3.jpg);">
            <div class="container">
                <div class="page-banner-entry">
                    <h1 class="text-white">Contact Us</h1>
				 </div>
            </div>
        </div>
		<!-- Breadcrumb row -->
		<div class="breadcrumb-row">
			<div class="container">
				<ul class="list-inline">
					<li><a href="/home">Home</a></li>
					<li>Contact Us</li>
				</ul>
			</div>
		</div>
		<!-- Breadcrumb row END -->
	
        <!-- inner page banner -->
        <div class="page-banner contact-page section-sp2">
            <div class="container">
                <div class="row">
					<div class="col-lg-5 col-md-5 m-b30">
						<div class="bg-primary text-white contact-info-bx">
							<h2 class="m-b10 title-head">Contact <span>Information</span></h2>
							<p>Lorem Ipsum is simply dummy text of the printing and typesetting industry.</p>
							<div class="widget widget_getintuch">	
								<ul>
									<li><i class="ti-location-pin"></i>75k Newcastle St. Ponte Vedra Beach, FL 309382 New York</li>
									<li><i class="ti-mobile"></i>0800-123456 (24/7 Support Line)</li>
									<li><i class="ti-email"></i>info@example.com</li>
								</ul>
							</div>
							<h5 class="m-t0 m-b20">Follow Us</h5>
							<ul class="list-inline contact-social-bx">
								<li><a href="#" class="btn outline radius-xl"><i class="fa fa-facebook"></i></a></li>
								<li><a href="#" class="btn outline radius-xl"><i class="fa fa-twitter"></i></a></li>
								<li><a href="#" class="btn outline radius-xl"><i class="fa fa-linkedin"></i></a></li>
								<li><a href="#" class="btn outline radius-xl"><i class="fa fa-google-plus"></i></a></li>
							</ul>
						</div>
					</div>
					<div class="col-lg-7 col-md-7">

						<form class="contact-bx ajax-form" action="/contact" method="post">
							<c:if test="${type != null}">
								<div class="ajax-message">
									<c:choose>
										<c:when test="${type == 'success'}">
											<div class="alert alert-success">
												<strong>Success!</strong> ${msg}
											</div>
										</c:when>
										<c:when test="${type == 'error'}">
											<div class="alert alert-danger">
												<strong>Error!</strong> ${msg}
											</div>
										</c:when>
									</c:choose>
								</div>
							</c:if>

							<div class="heading-bx left">
								<h2 class="title-head">Get In <span>Touch</span></h2>
								<p>Send us a message and we will get back to you!</p>
							</div>
							<div class="row placeani">
								<div class="col-lg-6">
									<div class="form-group">
										<div class="input-group">
											<label>Your Name</label>
											<input name="name" type="text" required class="form-control valid-character" <c:if test="${user != null}">value="${user.fullName}"</c:if>>
										</div>
									</div>
								</div>
								<div class="col-lg-6">
									<div class="form-group">
										<div class="input-group">
											<label>Your Email Address</label>
											<input name="email" type="email" class="form-control" required <c:if test="${user != null}">value="${user.email}"</c:if>>
										</div>
									</div>
								</div>
								<div class="col-lg-6">
									<div class="form-group">
										<div class="input-group">
											<label>Your Phone</label>
											<input name="phone" type="text" required class="form-control int-value">
										</div>
									</div>
								</div>
								<div class="col-lg-6">
									<div class="form-group">
										<div class="input-group">
											<label>Subject</label>
											<input name="subject" type="text" required class="form-control">
										</div>
									</div>
								</div>
								<div class="col-lg-12">
									<div class="form-group">
										<div class="input-group">
											<label>Type Message</label>
											<textarea name="message" rows="4" class="form-control" required ></textarea>
										</div>
									</div>
								</div>

								<c:if test="${combo == null}">
									<div class="col-lg-12">
										<div class="form-group">
											<p style="color: red;font-size: 12px">There is no admin available to contact</p>
										</div>
									</div>
								</c:if>

								<c:if test="${combo != null}">
									<div class="col-lg-12">
										<div class="form-group" style="display: flex; flex-direction: row">
											<p>Select an Admin</p>
											<select name="adminList" id="adminList">
												<c:forEach var="admin" items="${combo}">
													<option value="${admin.id}">${admin.fullName}</option>
												</c:forEach>
											</select>
										</div>
									</div>
								</c:if>

								<div class="col-lg-12">
									<div class="form-group">
										<div class="input-group">
											<div class="g-recaptcha" data-sitekey="${siteKey}"></div>
										</div>
									</div>
								</div>

								<div class="col-lg-12">
									<button name="submit" type="submit" value="Submit" class="btn button-md"> Send Message</button>
								</div>
							</div>
						</form>
					</div>
				</div>
            </div>
		</div>
        <!-- inner page banner END -->
    </div>
    <!-- Content END-->
	<jsp:include page="footer.jsp"></jsp:include>
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
<%--<script src="/assets/js/contact.js"></script>--%>
<script src='/assets/vendors/switcher/switcher.js'></script>
<script src="https://www.google.com/recaptcha/api.js" async defer></script>
</body>


</html>
