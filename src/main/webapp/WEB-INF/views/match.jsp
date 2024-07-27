<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">

<!-- Mirrored from educhamp.themetrades.com/demo/admin/index.html by HTTrack Website Copier/3.x [XR&CO'2014], Fri, 22 Feb 2019 13:08:15 GMT -->
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
    <link rel="icon" href="../error-404.html" type="image/x-icon" />
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
            <h4 class="breadcrumb-title">Dashboard</h4>
            <ul class="db-breadcrumb-list">
                <li><a href="#"><i class="fa fa-home"></i>Home</a></li>
                <li>Dashboard</li>
            </ul>
        </div>
        <div class="" style="display: flex; flex-direction: column; width: 100%">

            <div style="display: flex; flex-direction: row; margin-bottom: 2em">
                <button type="button" class="btn btn-success" onclick="window.location.href='/flash-cards/${id}'">Back</button>
                <button type="button" class="btn btn-success" onclick="openSettings()">Open Settings</button>
            </div>

            <div id="playArea">
            </div>

            <button disabled type="button" id="nextButton" class="btn btn-success" onclick="nextQuestion()">Next</button>


        </div>
    </div>

    <!-- Modal -->
    <div id="settingsModal" class="modal">
        <div class="modal-content">
            <span class="close">&times;</span>
            <h2>Quiz Settings</h2>
            <label for="quizSize">Quiz Size (Terms per Match):</label>
            <input type="number" id="quizSize" name="quizSize" min="1" value="3">
            <label for="tries">Number of Tries:</label>
            <input type="number" id="tries" name="tries" min="1" value="3">

            <button style="margin-top: 2em" type="button" class="btn btn-success" onclick="applySettings()">Apply</button>
            <button type="button" class="btn btn-warning" onclick="resetSettings()">Reset</button>
        </div>
    </div>
</main>
<div class="ttr-overlay"></div>

<!-- External JavaScripts -->
<script>
    let attempt = 3;
    let size = 3;

    function nextQuestion() {
        document.getElementById('nextButton').disabled = true;
        attempt -= 1;
        refreshBoard();

        if (attempt <= 0) {
            document.getElementById('nextButton').disabled = true;
            askUser();
        }
    }

    function askUser() {
        var userResponse = confirm("Do you want to continue?");
        if (userResponse) {
            // User clicked OK
            // Implement the action to continue
            console.log("User chose to continue");
            // For example, redirect to another page
            attempt = 3;
            refreshBoard();
        } else {
            // User clicked Cancel
            // Implement the action to go back
            console.log("User chose to go back");
            // For example, go back to the previous page
            window.history.back();
        }
    }

    const allTermItem = [
        <c:forEach var="i" items="${list}" varStatus="loop">
        {text: '${i.term}', tag: ${loop.count}}<c:if test="${!loop.last}">,</c:if>
        </c:forEach>
    ]

    const allAnswerItem = [
        <c:forEach var="a" items="${list}" varStatus="loop">
        {text: '${a.definition}', tag: ${loop.count}}<c:if test="${!loop.last}">,</c:if>
        </c:forEach>
    ]

    function shuffleArray(array) {
        for (let i = array.length - 1; i > 0; i--) {
            const j = Math.floor(Math.random() * (i + 1));
            [array[i], array[j]] = [array[j], array[i]]; // Swap elements
        }
    }

    function refreshBoard() {
        // Clear the existing board
        const area = document.querySelector("#playArea");
        area.innerHTML = ''; // This removes all child elements

        // Assuming allTermItem and allAnswerItem are already defined and populated
        shuffleArray(allTermItem);
        const threeRandomTerms = allTermItem.slice(0, size);
        const threeRandomAnswers = threeRandomTerms.map(term => {
            return allAnswerItem.find(answer => answer.tag === term.tag);
        });

        // Combine and shuffle the selected items
        const combinedItems = [...threeRandomTerms, ...threeRandomAnswers];
        shuffleArray(combinedItems);

        // Create new blocks and append them to the play area
        combinedItems.forEach(item => {
            const block = document.createElement("div");
            block.classList.add("grid-item");
            block.textContent = item.text; // Assuming item.text contains the text you want to display
            block.dataset.tag = item.tag; // Use data-tag to store the tag for matching logic
            block.addEventListener("click", handleBlockClick);
            area.appendChild(block);
        });
    }

    // Call refreshBoard() whenever you need to clear the existing board and create a new one
    document.addEventListener("DOMContentLoaded", () => refreshBoard());

    let firstSelection;
    let isChecking = false; // Flag to prevent selections during mismatch handling

    function handleBlockClick(event) {
        if (isChecking) return; // Exit if currently handling a mismatch

        const block = event.currentTarget;
        if (block.classList.contains("match") || block.classList.contains("selected")) {
            return; // Ignore if already matched or currently selected
        }

        block.classList.add("selected");
        if (!firstSelection) {
            firstSelection = block;
        } else {
            if (block.dataset.tag === firstSelection.dataset.tag) {
                block.classList.replace("selected", "match");
                firstSelection.classList.replace("selected", "match");
                firstSelection = null; // Reset for next selection

                checkAllCorrectAndShowNext()
            } else {
                isChecking = true; // Prevent further selections
                block.classList.add("non-match");
                firstSelection.classList.add("non-match");
                setTimeout(() => {
                    block.classList.remove("selected", "non-match");
                    firstSelection.classList.remove("selected", "non-match");
                    firstSelection = null; // Reset for next selection
                    isChecking = false; // Allow selections again
                }, 1000); // Adjust time as needed
            }
        }
    }

    function checkAllCorrectAndShowNext() {
        const blocks = document.querySelectorAll('.grid-item');
        let allCorrect = true;

        blocks.forEach(block => {
            if (!block.classList.contains('match')) {
                allCorrect = false;
            }
        });

        if (allCorrect) {
            document.getElementById('nextButton').disabled = false;
        }
    }

    // Get modal element
    var modal = document.getElementById("settingsModal");
    // Get <span> element that closes the modal
    var span = document.getElementsByClassName("close")[0];

    // Function to open the modal
    function openSettings() {
        modal.style.display = "block";
    }

    // When the user clicks on <span> (x), close the modal
    span.onclick = function() {
        modal.style.display = "none";
    }

    // When the user clicks anywhere outside of the modal, close it
    window.onclick = function(event) {
        if (event.target == modal) {
            modal.style.display = "none";
        }
    }

    // JavaScript function to reset settings
    function resetSettings() {
        // Set default values
        const defaultQuizSize = 3;
        const defaultTries = 3;

        // Update the input fields
        document.getElementById("quizSize").value = defaultQuizSize;
        document.getElementById("tries").value = defaultTries;

        // Apply these default settings to your quiz logic
        console.log(`Reset to default: Quiz Size: $1, Tries: $2`, defaultQuizSize, defaultTries);
        attempt = defaultTries;
        size = defaultQuizSize;
        refreshBoard();

        // Optionally, close the modal if needed
        modal.style.display = "none";
    }

    // Function to apply settings
    function applySettings() {
        const quizSize = document.getElementById("quizSize").value;
        const tries = document.getElementById("tries").value;

        // Apply these settings to your quiz logic
        // For example, setting global variables or updating UI components
        console.log(`Quiz Size: $1, Tries: $2`, quizSize, tries);
        attempt = tries;
        size = quizSize;
        refreshBoard();

        modal.style.display = "none";
    }
</script>

<style>
    :root {
        --min-box-width: 150px; /* Minimum width for each grid item */
    }

    #playArea {
        width: 65%; /* Adjusted to use full container width */
        margin: 0 auto; /* Center align */
        padding: 20px;
        background-color: #f0f0f0;
        display: grid;
        grid-template-columns: repeat(auto-fit, minmax(var(--min-box-width), 1fr)); /* Adjust minmax for desired item size */
        gap: 10px;
        box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        border-radius: 10px;
        margin-bottom: 2em;
    }

    .grid-item {
        border: 1px solid #ddd;
        padding: 20px;
        margin: 10px;
        display: flex; /* Changed to flex to better manage content */
        justify-content: center; /* Center content horizontally */
        align-items: center; /* Center content vertically */
        cursor: pointer;
        background-color: #f9f9f9;
        transition: background-color 0.3s ease;
        min-width: var(--min-box-width); /* Ensure grid items respect the minimum width */
        height: auto /* Set a fixed height for uniformity */
    }
    .selected {
        background-color: #a0d2eb; /* Light blue background for selected items */
    }
    .match {
        background-color: #98e2c6; /* Green background for matched items */
        cursor: default;
    }
    .non-match {
        background-color: #ffcccc; /* Light red background for non-matching items */
    }
</style>

<style>
    /* The Modal (background) */
    .modal {
        display: none; /* Hidden by default */
        position: fixed; /* Stay in place */
        z-index: 1; /* Sit on top */
        left: 0;
        top: 0;
        width: 100%; /* Full width */
        height: 100%; /* Full height */
        overflow: auto; /* Enable scroll if needed */
        background-color: rgb(0,0,0); /* Fallback color */
        background-color: rgba(0,0,0,0.4); /* Black w/ opacity */
    }

    /* Modal Content */
    .modal-content {
        background-color: #fefefe;
        margin: 15% auto; /* 15% from the top and centered */
        padding: 20px;
        border: 1px solid #888;
        width: 50%; /* Could be more or less, depending on screen size */
    }

    /* The Close Button */
    .close {
        color: #aaa;
        float: right;
        font-size: 28px;
        font-weight: bold;
    }

    .close:hover,
    .close:focus {
        color: black;
        text-decoration: none;
        cursor: pointer;
    }
</style>

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
<script src='/assets/vendors/calendar/moment.min.js'></script>
<script src='/assets/vendors/calendar/fullcalendar.js'></script>
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
    $(document).ready(function() {

        $('#calendar').fullCalendar({
            header: {
                left: 'prev,next today',
                center: 'title',
                right: 'month,agendaWeek,agendaDay,listWeek'
            },
            defaultDate: '2019-03-12',
            navLinks: true, // can click day/week names to navigate views

            weekNumbers: true,
            weekNumbersWithinDays: true,
            weekNumberCalculation: 'ISO',

            editable: true,
            eventLimit: true, // allow "more" link when too many events
            events: [
                {
                    title: 'All Day Event',
                    start: '2019-03-01'
                },
                {
                    title: 'Long Event',
                    start: '2019-03-07',
                    end: '2019-03-10'
                },
                {
                    id: 999,
                    title: 'Repeating Event',
                    start: '2019-03-09T16:00:00'
                },
                {
                    id: 999,
                    title: 'Repeating Event',
                    start: '2019-03-16T16:00:00'
                },
                {
                    title: 'Conference',
                    start: '2019-03-11',
                    end: '2019-03-13'
                },
                {
                    title: 'Meeting',
                    start: '2019-03-12T10:30:00',
                    end: '2019-03-12T12:30:00'
                },
                {
                    title: 'Lunch',
                    start: '2019-03-12T12:00:00'
                },
                {
                    title: 'Meeting',
                    start: '2019-03-12T14:30:00'
                },
                {
                    title: 'Happy Hour',
                    start: '2019-03-12T17:30:00'
                },
                {
                    title: 'Dinner',
                    start: '2019-03-12T20:00:00'
                },
                {
                    title: 'Birthday Party',
                    start: '2019-03-13T07:00:00'
                },
                {
                    title: 'Click for Google',
                    url: 'http://google.com/',
                    start: '2019-03-28'
                }
            ]
        });

    });

</script>
</body>

<!-- Mirrored from educhamp.themetrades.com/demo/admin/index.html by HTTrack Website Copier/3.x [XR&CO'2014], Fri, 22 Feb 2019 13:09:05 GMT -->
</html>