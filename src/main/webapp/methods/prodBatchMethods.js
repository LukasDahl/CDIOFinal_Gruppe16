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
            $("#bodytest").load("list/productBatchList.html");
        },
        error: function (jqXHR){
            alert(jqXHR.responseText);
        }
    });

}

function loadProductBatches() {
    $.get('rest/productBatch', function (data, textStatus, req) {
        $("#prodBatchtablehead").empty();
        $("#prodBatchtablehead").append('<th class="list">Produktbatch ID</th>' +
            '        <th class="list">Opskrift</th>' +
            '        <th class="list">Dato</th>')
        $("#prodBatchtablebody").empty();
        $.each(data, function (i, elt) {
            $('#prodBatchtablebody').append(generateProdBatchHTML(elt));
        });
    });
}
function generateProdBatchHTML(prod) {
    return '<tr class="list"  onclick="loadSingleProdBatch('+ prod.id + ')"> ' +

        '<td class="list">' + prod.id +  '</td>' +
        '<td class="list">' + prod.recipeid + ' - ' + prod.productName + '</td>' +
        '<td class="list">' + prod.date + '</td>';
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

function loadSingleProdBatch(prodBatch) {
    $.get('rest/productBatch/single/'+prodBatch, function (data, textStatus, req) {
        $("#bodytest").empty();
        $('#bodytest').append(generateSingleProdBatchHTML(data));

    });
}
function generateSingleProdBatchHTML(prodBatch) {
    var r = '' +
        '    <h2 class="center">Produktbatch: '+ prodBatch.id + '</h2>';

    r +='<table class="list">' +
        '    <thead>' +
        '         <tr class="list">' +
        '              <th class="list">R&aring;vare</th>' +
        '              <th class="list">M&aelig;ngde</th>' +
        '              <th class="list">Leverand&oslash;r</th>' +
        '              <th class="list">Vejet af</th>' +
        '              <th class="list">Tidspunkt</th>' +
        '        </tr>' +
        '    </thead>' +
        '    <tbody>';

    $.each(prodBatch.materials, function (i, elt) {
        r += '<tr class="list"><td class="list">' + prodBatch.materials[i] + ' - ' + prodBatch.ingnames[i] + '<td class="list">' + prodBatch.amounts[i] + '<td class="list">' + prodBatch.suppliers[i] + '<td class="list">' + prodBatch.labos[i] + '<td class="list">' + prodBatch.dates[i]+ '</tr>';
    });

    r +='    </tbody>' +
        '</table><br>';
    return r;
}