<%@ page import="ca.georgebrown.gbcresearch.Submission; ca.georgebrown.gbcresearch.SubmissionsController; ca.georgebrown.gbcresearch.Tag" %>
<%@ page import="ca.georgebrown.gbcresearch.security.Role; ca.georgebrown.gbcresearch.security.AppuserRole" contentType="text/html;charset=UTF-8" %>

<style>
.health_Green {
    color: white;
    background: #28804d !important;
}

.health_Red {
    color: white;
    background: #ff1605 !important;
}

.health_Yellow {
    background: #ffe508 !important;
}

.healthNotChecked option:not(:checked) {
    background-color: #FFF;
    color: black;
}

.newTag {
    background-color: #fcba03 !important;
}

#tag+span {
    width:100% !important;
    height: 100% !important;
}
</style>
<g:javascript>
    function change_color_inactive() {
        $('#tag~span li.select2-selection__choice').addClass('newTag');
        var activeTags = ("${Tag.findAllByStatus(Tag.STATUS.ACTIVE).id}");
            activeTags.replace('\u002c', ',');
            var activeString = activeTags.substr(1, activeTags.length-2);
            var active = activeString.split(',');

            $.each(active, function(index, tag) {
                var option = $('select option[value=' + tag.trim() + ']');
                $("li[title='" + option.html().toString() + "']").removeClass('newTag');
            });
    }

    var deleteTag = function() {
        if ($(this).parent('li').hasClass('newTag')) {
            var tagName = $(this).parent('li').attr('title');
            deleteNoDisableMsg(tagName);
        }
    };

    function deleteNoDisableMsg(tagName) {
        if ('${AppuserRole.findByAppuserAndRole(appuser, Role.findByAuthority('ROLE_PUBLISHER')).toString()}' != 'null' ) {
            bootbox.confirm({
                    message: "<p style='margin-top: 20px'>Are you sure you want to delete this inactive tag or remove it from the submission?</p>",
                    buttons: {
                        confirm: {
                            label: 'Delete',
                            className: 'btn-danger'
                        },
                        cancel: {
                            label: 'Remove',
                            className: 'btn-default'
                        }
                    },
                    callback: function (result) {
                        if (result) {
                            $("option:contains('"+ tagName + "')").remove();
                            ${remoteFunction(controller: 'tags', action: 'deleteTagPublisher', params: '\'title=\' + tagName')}
                            change_color_inactive();
                        }
                    }
            });
        }
    }

    $(document).ready(function( ) {
        var classname = document.getElementsByClassName("select2-selection__choice__remove");
        for (var i = 0; i < classname.length; i++) {
            classname[i].addEventListener('click', deleteTag, false);
        }
        change_color_inactive();

        $('#tag').click( function () {
            change_color_inactive();
        });

        $('.select2').click(  function() {
            change_color_inactive();
        });

        $('#tag').change( function () {
            change_color_inactive();
            for (var i = 0; i < classname.length; i++) {
            classname[i].addEventListener('click', deleteTag, false);
            }
        });
    });

    function formatHealth (healthObj) {
        if (!healthObj.id) {
            return healthObj.text;
        }
        $("#"+healthObj.id).addClass('health_' + healthObj.text);
        var $healthObj = $('<span class="health_' + healthObj.text+ '">' + healthObj.text + '</span>');
        return $healthObj;
    }

    function  showHelp() {
        bootbox.alert("<p style='margin-top: 15px;'> You can add new tags by typing into the tags box, but new tags will be pending for approval </p>");
    }
</g:javascript>

<div class="col-sm-8">
    <div class="form-group mb-3 viewProject">
        <label class="m-0" for="projTitle">Add a New Submission</label>
        <br>
    </div>
    <td colspan="3" class="subDescription pt-0"> Provide information about the project, research paper, workshop or
    conference you would like to share with your colleagues. Submissions will be moderated and either approved for
    publication or returned to you for review.</td>
    <br>
    <br>
    <div class="form-group my-3 viewProject">
        <g:if test="${status && status!= Submission.SUBMIT_STATUS.INITIATED}">
            <label class="m-0">Status:</label>
            <span class="submissionStatus ${Submission.SUBMIT_STATUS.find { it.value ==  status}.key }-color"
                  style="width: fit-content; padding: 5px 25px; font-size: 12px; margin-bottom: 5px; box-shadow: 2px 2px lightgrey;">
                ${status}
            </span>
        </g:if>
    </div>

    <div class="form-group mb-3 viewProject required">
        <label class="m-0" for="projTitle">Title:</label>
        <textarea type="text" class="form-control requiredInput requiredInput2 editField" id="projTitle" name="submission.title"
                  placeholder="Enter title">${submission?.title}</textarea>
    </div>

    <div class="form-group mb-3 viewProject required">
        <label class="m-0" for="projSummary">Submission Description:</label>
            <textarea class="form-control requiredInput editField" name="submission.description" id="projSummary" onchange=""
                      placeholder="Enter submission description"
                      rows="10" cols="120">${submission?.description}</textarea>
    </div>

    <div class="w-100 pl-0 py-3 mb-3 pull-left" style="background-color: white;">
      <div class="form-group mb-3">
        <label for="tag" class="mr-3 mb-2" style="color: #005AA5">TAGS:
            <span style="font-weight: 700; background-color: #005AA5; border-radius: 10px; color: white;padding-right: 7px; padding-left: 7px;" title="You can add new tags by typing into the following box, but new tags will be pending for approval" onclick="showHelp();">?</span>
        </label> <br/>
        <g:if test="${accessRights[Submission.RIGHT.EDIT]}">
            <g:select from="${Tag.findAllByStatus(Tag.STATUS.ACTIVE)}"
                      optionKey="id"
                      optionValue="name"
                      value="${submission?.tags?.toList()}"
                      multiple="multiple"
                      id="tag"
                      name="tags"
                      class="form-control editField tagSelect requiredInput"
                      aria-describedby="tagHelp">
            </g:select>
        </g:if>
        <g:elseif test="${accessRights[Submission.RIGHT.EDIT]}">
            <g:select
                    from="${Tag.findAllByStatusOrCreatedById(Tag.STATUS.ACTIVE, appuser.id)}"
                    optionKey="id"
                    optionValue="name"
                    value="${submission?.tags?.toList()}"
                    multiple="multiple"
                    id="tag"
                    name="tags"
                    class="form-control editField tagSelect requiredInput"
                    aria-describedby="tagHelp">
            </g:select>
        </g:elseif>
        <g:else>
            <div class="similarPersonInterests">
                <g:each in="${submission?.tags?.toList()}" var="tag">
                    <div class="tagBox">${tag.name}</div>
                </g:each>
            </div>
        </g:else>
      </div>
    </div>
</div>
<div class="col-sm-4">
    <div class="w-100 pl-3 py-3 mb-3 pull-left" style="background-color: white; color: black; min-width: 250px;">
        <div class="form-group mb-3 viewProject">
            <label class="m-3" for="projTitle">Submitted by:</label>
            <g:if test="${submission?.submitter}">
                <span style="color: black"> ${submission.submitter.displayName}</span>
            </g:if>
            <g:else>
                <span style="color: black"> ${appuser.displayName}</span>
            </g:else>
        </div>
        <div class="col-md-12 mb-3 viewProject <g:if test="${accessRights[Submission.RIGHT.EDIT]}">required</g:if> ">
            <label class="m-0 mb-1 mr-3" for="type" style="color: #005AA5">Type:</label>
            <g:if test="${!accessRights[Submission.RIGHT.EDIT]}">
                <span style="color: black"> ${submission?.type?.name} </span>
            </g:if>
            <g:else>
                <g:select
                        from="${types}"
                        optionKey="id"
                        optionValue="name"
                        noSelection="['null': 'Select...']"
                        value="${submission?.type?.id}"
                        name="submission.type"
                        id="type"
                        class="form-control editField departmentSelect requiredInput w-100">
                </g:select>
            </g:else>
        </div>
    </div>
</div>
