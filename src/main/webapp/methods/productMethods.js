function createProduct(){

    var $form = $("#newProd");
    var data = getFormData($form);
    var datajson = JSON.stringify(data);

    console.log(datajson)
    $.ajax({
        url: 'rest/product',
        method: 'POST',

        contentType: "application/json", // det vi sender er json
        data: datajson,
        success: function (datajson) {
            $("#bodytest").load("list/productList.html");
        },
        error: function (jqXHR){
            alert(jqXHR.responseText);
        }
    });

}

function loadProducts() {
    $.get('rest/product', function (data, textStatus, req) {
        $("#prodtablebody").empty();
        $.each(data, function (i, elt) {
            $('#prodtablebody').append(generateProdHTML(elt));
        });
        $("#prodtablehead").empty();
        $("#prodtablehead").append('<tr class="list">' +
            '        <th class="list">Produkt ID</th>' +
            '        <th class="list">Produktnavn</th>' +
            '    </tr>');
    });
}
function generateProdHTML(prod) {
    return '<tr class="list" onclick="getProductUpdate(' + prod.id + ')"><td class="list">' + prod.id + '</td>' +
        '<td class="list">' + prod.name + '</td>';
}

function getProductUpdate(i) {

    if (userpriv > 4) {
        $("#bodytest").load("update/updateProduct.html");
        updateid = i;
    }

}

function updateProductData() {
    $.get('rest/product/single/' + updateid, function (data, textStatus, req) {
        document.getElementById('ID').value = data.id;
        document.getElementById('name').value = data.name;
        updateid = 0;
    });
}

function updateProduct() {
    var $form = $("#updateProduct");
    var data = getFormData($form);
    var datajson = JSON.stringify(data);

    $.ajax({
        url: 'rest/product/update',
        method: 'POST',

        contentType: "application/json", // det vi sender er json
        data: datajson,
        success: function (datajson) {
            $("#bodytest").load("list/productList.html");
        },
        error: function (jqXHR){
            alert(jqXHR.responseText);
        }
    });
}