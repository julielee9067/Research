<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
            integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
    <title>Help Information</title>
</head>
<script type="text/javascript">
    $(function() {
        $(".expand").on( "click", function() {
            $expand = $(this).find(">:first-child");
            if ($expand.text() == "+") {
                $expand.text("-");
            } else {
                $expand.text("+");
            }
        });
    });
</script>
<body>
    <div class=container-fluid">
<div id="header">
    <h1 style="text-align:center;">Frequently Asked Questions
        <span>
            <a id="pdfIcon"></a>
        </span>
    </h1>
</div>
<div class="container">
    <div class="panel-group" id="accordion">
        <div class="panel panel-default">
            <g:each in="${helpContent}" var="content" status="i">
                    <div class="panel-heading">
                        <h4 data-toggle="collapse" data-parent="#accordion" href="#collapse${i}" class="panel-title expand">
                            <div class="right-arrow pull-right">+</div>
                            <a href="#">Q: ${content.topic}</a>
                        </h4>
                    </div>
                    <div id="collapse${i}" class="panel-collapse collapse">
                        <div class="panel-body">
                            ${raw(content.htmlBody)}
                        </div>
                    </div>
            </g:each>
        </div>
    </div>
</div>
</div>
</body>
</html>