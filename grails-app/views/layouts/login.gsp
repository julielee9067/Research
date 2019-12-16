<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!-->
<html lang="en" class="no-js"><!--<![endif]-->

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
        <link rel="shortcut icon" href="${resource(dir: 'images', file: 'favicon.ico')}" type="image/x-icon"/>
        <!-- <link rel="stylesheet" href="${resource(dir: 'css', file: 'mobile.css')}" type="text/css" /> -->
        <title><g:layoutTitle default='Applicant Registration'/></title>

        <g:javascript library="jquery-ui"/>
        <asset:javascript src="application.js"/>
        <asset:stylesheet src="application.css"/>
        <asset:stylesheet src="spring-security-ui.css"/>
        <asset:stylesheet src="security-ui.css"/>
        <asset:stylesheet src="backgroundImage.css"/>
        <asset:stylesheet src="css/bootstrap.min.css"/>
        <asset:javascript src="js/bootstrap.min.js"/>
        <g:layoutHead/>
    </head>

    <body>
        <header>
            <g:render template='/layouts/banner'/>
        </header>
        <main>
             <g:layoutBody/>
        </main>
    <footer style =" position: fixed; bottom: 0px; left: 0px; right: 0px; margin-bottom: 0px;">
        <g:render template='/layouts/footer'/>
    </footer>

    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
    </body>
</html>
