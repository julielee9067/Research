
%{--<asset:stylesheet src="research-portal.css"/>--}%
<script>
    function openAdvanced() {
        if (${tagList?.size() > 0 || typesList?.size() > 0}){
            $('#advancedSearch').removeClass('hidden');
            $('#advancedSearchBtn').val('⇑');
        }
    }

    function populateSearchedTypes() {
        var typeList = '${typesList.toString()}';

        if ('${typesList.toString()}'[0] == '[') {
            typeList = '${typesList.toString()}'.slice(1, -1).split(', ');
        }

        $('#typeSelectBarMKT').val(typeList).trigger('change');
    }
    function populateSearchedTags(){
        var tagList = '${tagList.toString()}';

        if ('${tagList.toString()}'[0] == '[') {
            tagList = '${tagList.toString()}'.slice(1, -1).split(', ');
        }

        $('#tagMKT').val(tagList).trigger('change');
    }

    $(document).ready(function() {
        $("#tagMKT").select2();
        $("#tagInterest").select2({width: '90%'});
        $('#typeSelectBarMKT').select2();

        openAdvanced();
        populateSearchedTags();
        populateSearchedTypes();

        $('#advancedSearchBtn').click( function() {
            if ($('#advancedSearch').hasClass('hidden')){
                $('#advancedSearch').removeClass('hidden');
                $('#advancedSearchBtn').val('⇑');
            }
            else {
                $('#advancedSearch').addClass('hidden');
                $('#advancedSearchBtn').val('⇓');
                $('#tagMKT').val(null).trigger('change');
            }
        });

        $("#clearButton").click(function() {
            $("#searchInput").val('');
            $('#tagMKT').val(null).trigger('change');
            $('#typeSelectBarMKT').val(null).trigger('change');
        });
    });

    function populateInterests(data) {
        $('#tagMKT').val(data.tags).trigger('change');
    }

    function getInterests(appuserId) {
        ${remoteFunction(controller: 'tags', action: 'ajaxInterests', params: '\'id=\' + escape(appuserId)',
                    onSuccess: 'populateInterests(data)')}
    }
</script>
<div class="card-body center" style="width: 80%">
    <g:form controller="homePage" action="index">
        <div class="row" style="min-height: 40px; vertical-align: auto;" >
            <div class="pull-left col-md-11 p-0 mx-0">
                <input type="text" name="searchInput" id="searchInput" value="${searchText}" class=" pl-3 h-100 w-100" placeholder="Search for topics of interest and people">
            </div>
            <div class="pull-left col-md-1 p-0">
                <g:actionSubmit value="Search" controller="homePage" action="index" class="btn btn-sm btn-primary w-100 h-100 rounded-0" name="search" id="search" style="background-color: #005AA5; box-shadow: none;"/>
            </div>
        </div>
    </g:form>
</div>