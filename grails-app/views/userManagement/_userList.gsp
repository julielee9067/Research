<table class="table table-hover table-striped table-bordered mb-0">
    <thead class="text-center">
        <tr>
            <th>Select</th>
            <th>User Id</th>
            <th>First Name</th>
            <th>Last Name</th>
            <th>Email</th>
            <th>Enabled</th>
            <th>Roles</th>
        </tr>
    </thead>
    <tbody>
    </tbody>
</table>

<script>
    $(document).ready(function() {
        hideLoader();
    });

    function maybeHideLoader(offset, total, max) {
        if (!offset || !max){
            offset = 0;
            max = 10;
        }
        if (total < offset + max) {
            hideLoader();
        }
    }

    function showLoader() {
        $('body').css( 'cursor', 'wait' );
    }

    function hideLoader() {
        $('body').css( 'cursor', 'default' );
}
</script>
