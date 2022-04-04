const getCompanyData = () => {
    jQuery.ajax({
        url: `api/company`,
        method: 'GET',
        dataType: 'json',
        success: function(res) {
            companyData = res

            setTableCompany(companyData)
            setSelectCompany(companyData)
        },
    })
}

const setTableCompany = data => {
    $('#table-company').DataTable().destroy()

    if (data.length > 0) {
        $('#table-company').find('tbody').html(data.map((v, i) => {
            return `
                <tr>
                    <td>${i + 1}</td>
                    <td>${v.code}</td>
                    <td>${v.name}</td>
                    <td>${v.phone}</td>
                    <td>${v.email}</td>
                    <td>
                        <div class="text-center">
                            <button class="col btn p-2 btn-secondary btn-edit-company" data-id="${v.id}"><i data-feather="edit" class="d-flex"></i></button>
                            <button class="col btn p-2 btn-danger btn-destroy-company" data-id="${v.id}"><i data-feather="trash" class="d-flex"></i></button>
                        </div>
                    </td>
                </tr>
            `
        }))
    } else {
        $('#table-company').find('tbody').html('')
    }

    $('#table-company').DataTable(dataTableSetting).draw()
    feather.replace()
}

const resetFormCompany = () => {
    $('#company-code').val('')
    $('#company-name').val('')
    $('#company-phone').val('')
    $('#company-email').val('')
    $('#company-address').val('')
}

const setSelectCompany = data => {
    $('#transaction-company-name').html(data.map(v => {
        return `<option value="${v.id}">${v.name}</option>`
    }))
}

$(document).on('click', '.btn-edit-company', function() {
    const companyId = $(this).attr('data-id')
    const company = companyData.find(v => v.id == companyId)

    $('#company-code').val(company.code)
    $('#company-name').val(company.name)
    $('#company-phone').val(company.phone)
    $('#company-email').val(company.email)
    $('#company-address').val(company.address)

    $('#btn-store-company').attr('data-status', 'update').attr('data-id', companyId).html('Update')
})

$(document).on('click', '.btn-destroy-company', function() {
    const companyId = $(this).attr('data-id')
      
    jQuery.ajax({
        url: `api/company/${companyId}`,
        method: 'DELETE'
    }).done(function() {
        companyData = companyData.filter(v => v.id != companyId)

        setTableCompany(companyData)
        setSelectCompany(companyData)
    });
})

$(document).on('click', '#btn-store-company', function() {
    const status = $(this).attr('data-status')
    const companyId = $(this).attr('data-id')

    const code = $('#company-code').val()
    const name = $('#company-name').val()
    const phone = $('#company-phone').val()
    const email = $('#company-email').val()
    const address = $('#company-address').val()

    if (status === 'update') {
        $(this).attr('data-status', 'store').removeAttr('data-id').html('Store')

        jQuery.ajax({
            url: `api/company/${companyId}`,
            method: `PUT`,
            data: JSON.stringify({ code, name, phone, email, address}),
            dataType: 'json',
            contentType: 'application/json',
            success: function(res) {
                companyData = companyData.map(v => {
                    if (v.id == companyId) {
                        return res
                    }

                    return v
                })
    
                resetFormCompany()
                setTableCompany(companyData)
                setSelectCompany(companyData)
            },
        })
    } else {
        jQuery.ajax({
            url: `api/company`,
            method: `POST`,
            data: JSON.stringify({ code, name, phone, email, address}),
            dataType: 'json',
            contentType: 'application/json',
            success: function(res) {
                companyData.push(res)
    
                resetFormCompany()
                setTableCompany(companyData)
                setSelectCompany(companyData)
            },
        })
    }
})

$(document).ready(function() {
    $('#table-company').DataTable(dataTableSetting)

    getCompanyData()
})