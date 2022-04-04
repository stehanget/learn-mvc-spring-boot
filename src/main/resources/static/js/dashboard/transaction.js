const getTransactionData = () => {
    jQuery.ajax({
        url: `api/transaction`,
        method: 'GET',
        dataType: 'json',
        success: function(res) {
            transactionData = res

            setTableTransaction(transactionData)
        },
    })
}

const setTableTransaction = data => {
    $('#table-transaction').DataTable().destroy()

    if (data.length > 0) {
        $('#table-transaction').find('tbody').html(data.map((v, i) => {
            return `
                <tr>
                    <td>${i + 1}</td>
                    <td>${v.company}</td>
                    <td>${v.item}</td>
                    <td>${v.unit}</td>
                    <td>${v.price}</td>
                    <td>${v.total}</td>
                    <td>${v.remainingStock}</td>
                    <td>${moment(v.createdAt).utcOffset(0).format('DD MMMM YYYY, HH:mm')}</td>
                    <td>
                        <div class="text-center">
                            <button class="col btn p-2 btn-secondary btn-edit-transaction" data-id="${v.id}"><i data-feather="edit" class="d-flex"></i></button>
                            <button class="col btn p-2 btn-danger btn-destroy-transaction" data-id="${v.id}"><i data-feather="trash" class="d-flex"></i></button>
                        </div>
                    </td>
                </tr>
            `
        }))
    } else {
        $('#table-transaction').find('tbody').html('')
    }

    $('#table-transaction').DataTable(dataTableSetting).draw()
    feather.replace()
}

const resetFormTransaction = () => {
    $('#transaction-item-unit').val('')
}

$(document).on('click', '.btn-edit-transaction', function() {
    const transactionId = $(this).attr('data-id')
    const transaction = transactionData.find(v => v.id == transactionId)

    $(`#transaction-company-name`).val(transaction.companyId).change()
    $(`#transaction-item-name`).val(transaction.itemId).change()
    $('#transaction-item-unit').val(transaction.unit)

    $('#btn-store-transaction').attr('data-status', 'update').attr('data-id', transactionId).html('Update')
})

$(document).on('click', '.btn-destroy-transaction', function() {
    const transactionId = $(this).attr('data-id')
      
    jQuery.ajax({
        url: `api/transaction/${transactionId}`,
        method: 'DELETE'
    }).done(function() {
        transactionData = transactionData.filter(v => v.id != transactionId)

        setTableTransaction(transactionData)
    });
})

$(document).on('click', '#btn-store-transaction', function() {
    const status = $(this).attr('data-status')
    const transactionId = $(this).attr('data-id')

    const companyId = $('#transaction-company-name').val()
    const itemId = $('#transaction-item-name').val()
    const unit = $('#transaction-item-unit').val()

    if (status === 'update') {
        $(this).attr('data-status', 'store').removeAttr('data-id').html('Store')

        jQuery.ajax({
            url: `api/transaction/${transactionId}`,
            method: `PUT`,
            data: JSON.stringify({companyId, itemId, unit}),
            dataType: 'json',
            contentType: 'application/json',
            success: function(res) {
                transactionData = transactionData.map(v => {
                    if (v.id == transactionId) {
                        return res
                    }

                    return v
                })
    
                resetFormTransaction()
                setTableTransaction(transactionData)
                getItemData()
            },
        })
    } else {
        jQuery.ajax({
            url: `api/transaction`,
            method: `POST`,
            data: JSON.stringify({companyId, itemId, unit}),
            dataType: 'json',
            contentType: 'application/json',
            success: function(res) {
                transactionData.push(res)
    
                resetFormTransaction()
                setTableTransaction(transactionData)
                getItemData()
            },
        })
    }
})

$(document).ready(function() {
    $('#table-transaction').DataTable(dataTableSetting)

    getTransactionData()
})