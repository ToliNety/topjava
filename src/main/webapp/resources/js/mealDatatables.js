var ajaxUrl = 'ajax/meals/';
var datatableApi;
var dates;
var times;
// $(document).ready(function () {
$(function () {
    datatableApi = $('#datatable').DataTable({
        "paging": false,
        "info": true,
        "columns": [
            {
                "data": "dateTime"
            },
            {
                "data": "description"
            },
            {
                "data": "calories"
            },
            {
                "defaultContent": "Edit",
                "orderable": false
            },
            {
                "defaultContent": "Delete",
                "orderable": false
            }
        ],
        "order": [
            [
                0,
                "asc"
            ]
        ]
    });
    dates= $('#startDate, #endDate').datetimepicker({
        timepicker:false,
        format:'Y-m-d'
    });
    times= $('#startTime, #endTime').datetimepicker({
        datepicker:false,
        format:'H:i'
    });
    makeEditable();
});