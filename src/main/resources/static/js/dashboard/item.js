const getItemData = () => {
    jQuery.ajax({
        url: `api/item`,
        method: 'GET',
        dataType: 'json',
        success: function(res) {
            itemData = res

            setTableItem(itemData)
            setSelectItem(itemData)
        },
    })
}

const setTableItem = data => {
    $('#table-item').DataTable().destroy()

    if (data.length > 0) {
        $('#table-item').find('tbody').html(data.map((v, i) => {
            return `
                <tr>
                    <td>${i + 1}</td>
                    <td>${v.name}</td>
                    <td>${v.price}</td>
                    <td>${v.stock}</td>
                    <td>
                        <div class="text-center">
                            <button class="col btn p-2 btn-secondary btn-edit-item" data-id="${v.id}"><i data-feather="edit" class="d-flex"></i></button>
                            <button class="col btn p-2 btn-danger btn-destroy-item" data-id="${v.id}"><i data-feather="trash" class="d-flex"></i></button>
                        </div>
                    </td>
                </tr>
            `
        }))
    } else {
        $('#table-item').find('tbody').html('')
    }

    $('#table-item').DataTable(dataTableSetting).draw()
    feather.replace()
}

const resetFormItem = () => {
    $('#item-name').val('')
    $('#item-price').val('')
    $('#item-stock').val('')
    $('#item-desc').val('')
}

const setSelectItem = data => {
    $('#transaction-item-name').html(data.map(v => {
        return `<option value="${v.id}">${v.name}</option>`
    }))
}

$(document).on('click', '.btn-edit-item', function() {
    const itemId = $(this).attr('data-id')
    const item = itemData.find(v => v.id == itemId)

    $('#item-name').val(item.name)
    $('#item-price').val(item.price)
    $('#item-stock').val(item.stock)
    $('#item-desc').val(item.description)

    $('#btn-store-item').attr('data-status', 'update').attr('data-id', itemId).html('Update')
})

$(document).on('click', '.btn-destroy-item', function() {
    const itemId = $(this).attr('data-id')
      
    jQuery.ajax({
        url: `api/item/${itemId}`,
        method: 'DELETE'
    }).done(function() {
        itemData = itemData.filter(v => v.id != itemId)

        setTableItem(itemData)
        setSelectItem(itemData)
    });
})

$(document).on('click', '#btn-store-item', function() {
    const status = $(this).attr('data-status')
    const itemId = $(this).attr('data-id')

    const name = $('#item-name').val()
    const price = $('#item-price').val()
    const stock = $('#item-stock').val()
    const description = $('#item-desc').val()

    if (status === 'update') {
        $(this).attr('data-status', 'store').removeAttr('data-id').html('Store')

        jQuery.ajax({
            url: `api/item/${itemId}`,
            method: `PUT`,
            data: JSON.stringify({name, price, stock, description}),
            dataType: 'json',
            contentType: 'application/json',
            success: function(res) {
                itemData = itemData.map(v => {
                    if (v.id == itemId) {
                        return res
                    }

                    return v
                })
    
                resetFormItem()
                setTableItem(itemData)
                setSelectItem(itemData)
            },
        })
    } else {
        jQuery.ajax({
            url: `api/item`,
            method: `POST`,
            data: JSON.stringify({name, price, stock, description}),
            dataType: 'json',
            contentType: 'application/json',
            success: function(res) {
                itemData.push(res)
    
                resetFormItem()
                setTableItem(itemData)
                setSelectItem(itemData)
            },
        })
    }
})

$(document).ready(function() {
    $('#table-item').DataTable(dataTableSetting)

    getItemData()
})