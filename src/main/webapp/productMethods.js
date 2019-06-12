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
            $("#bodytest").load("productList.html");
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
    });
}
function generateProdHTML(prod) {
    return '<tr><td>' + prod.id + '</td>' +
        '<td>' + prod.name + '</td>';
}