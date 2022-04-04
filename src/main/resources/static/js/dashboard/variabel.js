var companyData = []
var itemData = []
var TransactionData = []

var dataTableSetting = {
    "autoWidth": false,
    "pageLength": 10,
    dom: 'Bfrtip',
    buttons: [
        {
            extend: 'csv',
            exportOptions: {
                columns: ':not(:last-child)'
            }
        },
        {
            extend: 'pdf',
            exportOptions: {
                columns: ':not(:last-child)'
            }
        },
        {
            extend: 'print',
            exportOptions: {
                columns: ':not(:last-child)'
            }
        }
    ]
}