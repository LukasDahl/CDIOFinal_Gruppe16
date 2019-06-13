function createProductBatch(){

    var $form = $("#newProdBatch");
    var data = getFormData($form);
    var datajson = JSON.stringify(data);

    console.log(datajson)
    $.ajax({
        url: 'rest/productBatch',
        method: 'POST',

        contentType: "application/json", // det vi sender er json
        data: datajson,
        success: function (datajson) {
            $("#bodytest").load("productBatchList.html");
        },
        error: function (jqXHR){
            alert(jqXHR.responseText);
        }
    });

}

function loadProductBatches() {
    $.get('rest/productBatch', function (data, textStatus, req) {
        $("#prodBatchtablebody").empty();
        $.each(data, function (i, elt) {
            $('#prodBatchtablebody').append(generateProdBatchHTML(elt));
        });
    });
}
function generateProdBatchHTML(prod) {
    return '<tr><td>' + prod.id + '</td>' +
        '<td>' + prod.name + '</td>';
}

function getRecipeNames() {
    $.get('rest/recipe', function (data, textStatus, req) {
        var $dropdown = $("#recipeDropdown");
        $dropdown.empty();
        $dropdown.append($('<option value="0">V&aelig;lg</option>'));
        $.each(data, function (i, elt) {
            $dropdown.append($("<option />").val(this.id).text(this.id + " " + this.product));
        });
    });
}