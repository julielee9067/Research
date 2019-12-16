<!DOCTYPE html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js"><!--<![endif]-->

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title><g:layoutTitle default="Grails"/></title>
    %{--<meta name="viewport" content="width=device-width, initial-scale=1.0">Iwona--}%
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="shortcut icon" href="${assetPath(src: 'favicon.ico')}" type="image/x-icon">
    <link rel="apple-touch-icon" href="${assetPath(src: 'apple-touch-icon.png')}">
    <link rel="apple-touch-icon" sizes="114x114" href="${assetPath(src: 'apple-touch-icon-retina.png')}">
    <asset:stylesheet src="application.css"/>
    <asset:stylesheet src="jquery-ui.css"/>
    <asset:javascript src="application.js"/>
    <asset:javascript src="bootbox.min.js"/>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.5/js/select2.full.min.js"></script>
    <script src="https://code.jquery.com/jquery-3.3.1.js" integrity="sha256-2Kok7MbOyxpgUVvAk/HJ2jigOSYS2auK4Pfzbm7uH60=" crossorigin="anonymous"></script>
     <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
   %{--BOOTSTRAP 4.0--}%
   <asset:javascript src="js/util.js"/>
   <asset:stylesheet src="css/bootstrap.min.css"/>
   <asset:javascript src="js/bootstrap.min.js"/>
   <asset:javascript src="jquery.are-you-sure.js"/>
    <asset:javascript src="tableHeadFixer.js"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.5/css/select2.min.css">

    <g:layoutHead/>
    <style type="text/css" media="screen">
    #sessionEmail {
        width: 250px;
    }

    .pagination .currentStep {
        background: #337ab7;
        color: white;
        !important;
    }

    .modal-open .modal {
        display: flex !important;
        align-items: center;
        justify-content: center;
    }
    .table-hover tbody tr:hover td, .table-hover tbody tr:hover th {
        background-color: lightyellow;
    }

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ extra: Start of Research-portal.css   ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
    /*  It is added here because application.css overwrites Research-portal, it should not do that */
    /* banner */
    .profilePicMini {
        border-radius: 50%;
        width: 50px;
        height:50px;

        vertical-align: middle;
    }

    .profileSection {
        width:300px;
        min-width: 300px;
        display:inline-block;
        border-style:solid;
        border-color:lightgrey;
        border-width:thin;
        color:white;
        background-color: #337ab7;
        vertical-align: middle;
        list-style-type: none;
        padding: 2px;
        padding-left: 20px;
    }

    .clickProfile {
        color:white;
    }

    .profileSection .dropdown {
        padding-top:10%;
    }

    .custom-dropdown {
        width: 300px;
        margin-top: 16px;
        margin-left: -21px;
    }

    #navbarBanner {
        margin-left: auto;
        max-width: 72px !important;
        padding-right: 10px;
    }

    @media (max-width: 952px) {
        .custom-dropdown {
            margin-top: 2px;
            margin-left: -71px;
        }
    }

    @media (max-width: 950px) {

        .navbar {
            min-height: 100px;
        }


        .profileSection {
            width: 300px;
            display:inline-flex;
        }

    }


    /* Home Page*/

    .leftHome {
        float:left;
        margin: 0 12px;
        width:calc(70% - 34px);
    }

    .search, .mySubmissions {
        width:100%;
        margin:1.7% 0;
    }

    .search {
        margin-top: 16px;
    }

    #searchInput {
        width: calc(100% - 158px);
        vertical-align: bottom;
    }

    #loadInterests{

        width: 25px;
        height: 25px;
    }

    #advancedSearch {
        padding-top: 20px;
    }

    .custom-dropdown-homepage {
        display:inline-flex;
        justify-content: left;
        width: 100%;


    }

    .custom-dropdown-homepage h4 {
        margin-left: 0;
        font-size: 20px;
        font-weight: 700;

    }

    .leftContent {
        width:100%;
    }

    .rightSide {
        float:right;
        margin-right: 10px;
        width:30%;
    }

    .actionWindow, .peopleWindow {
        width: 100%;
    }

    .peopleWindow h4 {
        text-align:center;
    }

    .actionBtn {
        width:100%;
        margin: 1% 0;
    }

    #tag+span {
        width:calc(100% - 94px) !important;
    }

    #typeSelectBar+span {
        width: calc(100% - 48px) !important;
        margin-top: 15px;
    }

    #searchUsersDisplayBtn, #recommendedUsersDisplayBtn {
        display: none;
    }

    #searchUsersWindow {
        padding-top: 10px;
    }

    @media (max-width: 850px) {

        .custom-dropdown-homepage h4 {
            margin-left: auto;

        }

        .custom-dropdown-homepage {
            width: 100%;
            text-align:center;

        }

        #searchUsersDisplayBtn, #recommendedUsersDisplayBtn {
            margin-left: auto;
            max-height: 36px;
            vertical-align: middle;
            display: block;
        }

        .rightSide, .leftHome {
            width:100%;
            margin-left: 0;
            margin-right: 0;
        }
    }


    /* submissions and projects display */
    .viewCheck button {
        border-radius: 10px;
        border-style: none;
        width:100%;
        height: 55px;
    }

    .submissionContent {
        width: 100%;
        /*border-style:solid;*/
        /*border-width: thin;*/
        margin-bottom:2%;
        background-color: white;
        text-align: left;
    }

    .section {
        padding: 0 1%;
        display:inline-block;
    }

    .titleSection {
        width:88%;
    }

    .ownerSection, .submissionTypeSection {
        width:35%;
    }

    .dateSection {
        width:30%;
    }

    .ownerSection, .submissionTypeSection, .dateSection {
        float:left;
    }

    .descriptionSection, .tagSection {
        width:100%;
    }

    .tagSection {
        border-top-style: solid;
        border-top-width: thin;
        border-top-color: inherit;
        margin-top: 10px;
    }

    .submissionTypeSection {
        text-align:right;
    }

    .linkSection {
        float:right;
        width:12%;
        text-align:right;
        padding-right: 5px;
    }
    /*body{*/
    /*    background-color: #ac2925;*/
    /*}*/
    /* people viewing */

    .personBox {
        width: 100%;
        background-color: white;
        margin: 4% 0;
        border-color: lightgrey;
        border-style: solid;
        border-width: thin;
        padding: 3%;
    }

    .personBox div {
        display:inline-block;
        text-align:left;
    }

    .tagBox{
        cursor: default;
        float: left;
        margin-right: 2px;
        margin-top: 5px;
        padding: 2px 2px;
        width:fit-content!important;
        display:inline-block;
    }

    .personSection {
        display:grid;
        height:100%;
    }

    .similarPersonProfilePhoto {
        width:20%;
        vertical-align: top;
    }

    .similarPersonName {
        padding-left: 1%;
        width:48%;
        font-size: 20px;
    }

    .similarPersonLink {
        width: 24%;
        font-size: 14px;
        float:right;
        vertical-align:top;
        text-align:right !important;
    }

    .similarPersonInterests {
        width:100%;
        display: inline-block;
        text-align: center;
    }

    .namePositionNarrow {
        display: none !important;
    }

    .namePositionWide {
        display: inline-block !important;
    }

    @media (max-width: 1000px) {
        .viewCheck div {
            max-width: 100% !important;
        }

        .viewCheck button {
            height: 30px;
        }
    }
    @media (max-width:850px) {

        .namePositionNarrow {
            display: inline-block !important;
        }

        .namePositionWide {
            display: none !important;
        }

        .similarNameInterests {
            width:calc(85% - 154px);
        }

        .similarPersonProfilePhoto {
            height: 120px;
            width: 120px;
        }

        .similarPersonLink {
            width: 18%;
        }
        .similarPersonName {
            width: 100%;
            overflow-y: hidden;
            height:30px;

        }
        .similarPersonInterests {
            width: 100%;

        }

        .personSection > .profilePicMini {
            width: 100px;
            height: 100px;
        }

        .personBox {
            text-align:left;
        }
    }

    @media (max-width: 1070px) and (min-width: 850px) {
        .similarPersonName {
            padding-left: 7%;
        }
        .similarPersonProfilePhoto {
            padding: 1% 0;
        }

        .similarPersonProfilePhoto, .similarPersonLink, .similarPersonName {
            width: 100%;
        }
        .personBox div {
            text-align:center;
        }

    }

    #userSearch {
        width:calc(100% - 124px);
    }

    /* Pagination */
    .loader {
        border: 8px solid #f3f3f3; /* Light grey */
        border-top: 8px solid #3498db; /* Blue */
        border-radius: 50%;
        width: 50px;
        height: 50px;
        animation: spin 2s linear infinite;
        margin: auto;
    }

    .paginateButtons > a {
        display:none;
    }

    @keyframes spin {
        0% { transform: rotate(0deg); }
        100% { transform: rotate(360deg); }
    }

    .paginationBox {
        padding-top: 5px;
        /*width:100%;*/
        text-align:center;
        justify-content: center;
        margin:auto;
    }
    .pagination {
        display: inline-block;
    }
    .paginationResults {
        text-align:center !important;
    }
    .pagination a, .currentStep, .step, .nextLink, .prevLink, .paginate a {
        font-weight:700;
        font-size:14px;
        color: black;
        float: left;
        margin:0px 0px;
        padding: 2px 8px;
        text-decoration: none;
        border:1px solid #aaaaaa;
    }
    .pagination a.active, .paginate a.active {
        background-color:#009900;
    }
    .pagination a:hover:not(.active), .paginate a:hover:not(.active) {
        background-color: #d4d5d2;
    }

    .currentStep {
        background: #337ab7;
        color: white !important;
    }

    /*Misc */
    .inline-flex {
        display:inline-flex !important;
    }

    #userList {
        display: inline-grid;
    }

    /*Profile Page */

    .profileHeader {

        height: 300px;
        padding: 2%;
        padding-left:3%;
    }

    .profilePicture > img {

        border-radius: 50%;
        width: 200px;
        height:200px;
        margin-bottom: 10px;
        margin-top: 8px;

    }

    .profile {
        margin: 1%;
        display:inline;
        width: 30%;
    }

    .profileHeader {
        background-color: #005293;
        border-style:solid;
        border-color: white;
        border-width: 0;
    }

    .newProfileHearder{
        background-color: #f0f0f0;
        border-style:none;

    }
    .profilePicture, .profileInfo {
        display:grid;
        float:left;
    }

    .profileInfo {

        padding-left: 3%;
        max-height: 961px;
        height: 100%;
        width: calc(100% - 200px);
    }

    .profileInfo h1, .profileInfo h2, .profileInfo h3, .profileInfo h4, .profileInfo p {
        color:white;
        padding:0;
        margin: 0;

    }

    .profileInfo table{
        table-layout: fixed;
        height: inherit;
        width:100%;
    }

    .bio {
        width:80%;
        /*overflow:auto;*/
        color: gray;
        height:110%;
        /*max-height: 130px;*/
        padding:0.5%;
        text-align: justify;
    }

    .textRow {
        height:0;
        overflow: auto;
    }

    .nameCell {
        max-height: 2em;
    }

    .emailCell {
        max-height: 1.5em;
    }

    .interests, .following, .followers {
        width: 100%;
        margin-left: 10px;
        text-align: left;
    }

    .info {

        width: 30%;
        display: inline-block;
        margin-left: 10px;
        text-align: left;

    }

    #followingUsersDisplayBtn, #followersUsersDisplayBtn {
        margin-inline-start:auto !important;
    }

    #followingText, #followersText {
        padding-left:32px;

    }
    #followingText h4, #followersText h4{
        font-size: 1.143rem;
        font-weight: 400;
    }
    .submissionInfo {

        width: calc(70% - 44px);
        margin-left: 10px;

    }

    .profileContent {
        display: inline-flex;
    }


    .recommendedProjects {
        text-align:left !important;
        width: 100%;
    }

    .mySubmissions, .center {
        text-align:left;
    }

    .IN_EDIT-color, .Required-color{
        background-color: #ffc107;
    }

    .SUBMITTED-color{
        background-color: #4CAF50
    }

    .RETURNED-color {
        background-color: red;
    }

    .PUBLISHED-color {
        background-color: #005AA5
    }

    .INITIATED-color {
        background-color: #ce8483;
    }

    .viewCheck label {
        padding-left: 5px;
    }

    .infoRow {
        font-style:italic;
    }


    .viewCheck {
        text-align: center;
        width:100%;
        display: inline-block;
    }

    .submissionStatus {
        width:100%;
        text-align: center;

        color: white;
    }
    .tab {
        display:inline-block;
        /*border-style:solid solid none;*/
        padding: 10px;
        z-index:2;
        background-color: #005AA5;
        color: white;
    }

    .activeTab {
        background-color:#f8f9fa;
        color: black;
    }

    .tab:hover {
        cursor: pointer;
    }

    .submissionList,.viewedList {
        width:100%;
        border-style:solid;
        padding:2%;
        background-color:#f0f0f0;
        margin-top:-3px;

    }
    .mySubmissions {
        width:98%;
    }

    .center {
        text-align: center;
        width: 100%;
    }

    @media (max-width:558px) {
        .profilePicture > img {
            height: 150px;
            width: 150px;
        }



        .profileInfo {
            width: calc(100% - 150px);
        }

        #followBtn {
            margin-top: 10px;
            height: 63px;
        }
    }

    @media (max-width:850px) {
        .info, .submissionInfo {
            width:calc(100% - 20px );
            display: block;
            margin-left: 0;
            margin-right: 0;
        }

        .submissionInfo {
            margin-left: 10px;
        }

        .profileContent {
            display: block;
        }
    }


    /* Settings */

    #profileSettingWindow {

        width: 100%;
        margin-top: 10px !important

    }

    .settingsBoxes {
        width:100%;
        display:inline-block;

    }

    .centerSettings {
        display:block;
        text-align:center;

    }

    .leftSettings {
        float:left;
    }

    .rightSettings {
        float:right;
        padding-left: 6% !important;
    }

    .leftSettings, .rightSettings {

        width: 49.9%;
        padding: 1%;
        display: grid;
    }

    .rightSettings > label {
        display:block;
    }

    .rightSettings > input[type=checkbox] {
        margin-right:5%;
    }

    .leftSettings > div {
        width: 100% !important;
    }

    .settingsForm textarea{
        width:100%;
        height: 150px;
        overflow: auto;
    }

    @media (max-width:753px) {
        .leftSettings, .rightSettings {

            width: 100%;

        }

        .rightSettings {
            padding-left: 2% !important;
        }
    }

    .table tr td{
        border: none !important;
        text-align: left;
    }

    .table tr td.tagNames {
        float: right;
    }

    .table tr td.subType{
        font-weight: 700;
        text-transform: uppercase;
        font-size: smaller;
    }

    .table tr td.subTitle a:link{
        font-size: 20px ;
        text-decoration: none;
        color: #0067BD;
    }

    .table tr td.subTitle a:hover{
        font-size: 20px;
        text-decoration: underline;
        color: #0067BD;
    }
    .table tr td.subDescription {
        text-align: justify;
        color: #000000;
        font-size: 14px;
        font-family: 'Open Sans', -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Oxygen, Ubuntu, Cantarell, 'Helvetica Neue', sans-serif;
        line-height: 1.6
    }

    .table tr td.subImage {
        vertical-align: top;
        text-align: center;
        width: 100px;
    }
    .table tr td.subOwner {
        font-weight: 700;
        vertical-align: bottom;
        text-align: right;
        white-space: nowrap;
    }

    .table tr td.subDateDashboard {
        font-size: smaller;
        color: grey;
        text-align: right;
    }

    .table tr td.subDate {
        font-size: smaller;
        color: grey;
    }

    .viewProject {
        color: black;
    }
    .viewProject label{
        font-size: 20px;
        color: #1b5ca3;
    }

    .projectViewSelect {
        -webkit-appearance: none;
        -moz-appearance: none;
        appearance: none;
        padding: 5px;
        border: 1px solid #aaa;
        cursor: default;
        float: left;
        margin-right: 5px;
        margin-top: 5px;
        padding: 7px 15px;
        display:inline-block;"
    }

    .select2-container--default .select2-selection--multiple .select2-selection__choice {
        border-radius: 0px;
        padding: 5px;
    }

    label#lbFileSelector {
        background-color: #1b5ca3;
        border-radius: 0px;
    }
    #fileInputAndSubmit{
        text-align: right !important;
        direction: rtl;
    }

    #upload-file-info {
        background-color: white;
        padding: 4px 10px;
        color: black;
        font-weight: lighter;
    }

    #saveChangeMessage {
        color: red;
    }

    .card-title{
        text-align: left !important;
        text-transform: uppercase;
        font-weight: 700;
    }

    #btnSubmitFile {
        background-color: #1b5ca3;
        border-radius: 0px;
        border: none;
        padding: 7px 10px;
        margin-top: 7px;
    }

    .headerUL {
        list-style: none;
        padding-right: 20px;

    }
    .headerUL li {
        display: inline-block;
        vertical-align: middle;
        margin-left: 20px;
        font-size: 14px;
        margin-top: auto;
        margin-bottom: auto;
    }

    .headerUL > li > a {
        color: white !important;
        text-decoration: none;
        padding-left: 0px;
        padding-right: 0px;
        width: fit-content !important;
        margin-right: 10px;
        margin-left: 10px;
    }
    .headerUL li.active{
        border-bottom: none !important;
    }
    .headerUL li.active a {
        border-bottom: 2px white solid;
        background-color: #005AA5 !important;
    }
    .headerSearchBox {
        height: 100px !important;
    }


    #typeSelectBarMKT+span {
        width: 100% !important;
        height: 100% !important;
        margin-top: 0px;
        margin-bottom: 0px;
    }


    #tagMKT+span, #tag+span{
        width:100% !important;
        height: 100% !important;
    }

    div.dropbox span.select2-selection.select2-selection--multiple,
    div.dropbox span.select2-selection.select2-selection--multiple
    {
        border-width: 0px !important;
        border-right: 1px black solid !important;
        border-radius: 0px;
        height: 100% !important;
    }

    .btn {
        border-radius: 0px; !important;
    }

    /*~~~~~~~~~~~~~~~~~~~~~~~    End of Research-portal.css   ~~~~~~~~~~~~~~~~~~~~~~~~ */
    </style>
    <asset:stylesheet src="research-portal.css"/>
</head>
<body>
    <header>
        <g:render template='/layouts/banner'/>
    </header>
    <main style="min-height: 700px;">
                <g:layoutBody/>
    </main>
    <footer>
        <g:render template='/layouts/footer'/>
    </footer>
</body>
</html>
