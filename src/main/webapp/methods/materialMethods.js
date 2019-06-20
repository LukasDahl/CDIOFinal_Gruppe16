<!--
Forfatter: Lukas
Ansvar: Filen indeholder funktioner som anvendes til at oprette, opdatere, indhente og indlæse listen over råvarebatches på websiden.
-->

function createMaterial(){

    var $form = $("#newMaterial");
    var data = getFormData($form);
    var datajson = JSON.stringify(data);

    console.log(datajson)
    $.ajax({
        url: 'rest/material',
        method: 'POST',

        contentType: "application/json", // det vi sender er json
        data: datajson,
        success: function (datajson) {
            $("#bodytest").load("list/materialBatchList.html");
        },
        error: function (jqXHR){
            alert(jqXHR.responseText);
        }
    });

}

function loadMaterials() {
    $.get('rest/material', function (data, textStatus, req) {
        $("#materialtablebody").empty();
        $.each(data, function (i, elt) {
            $('#materialtablebody').append(generateMaterialHTML(elt));
        });
        $("#materialtablehead").empty();
        $("#materialtablehead").append('<tr class="list">' +
            '        <th class="list">Batch ID</th>' +
            '        <th class="list">Ingrediens</th>' +
            '        <th class="list">Mængde</th>' +
            '        <th class="list">Leverandør</th>' +
            '        <th class="list">Dato</th>' +
            '    </tr>');
    });

}
function generateMaterialHTML(material) {
    return '<tr class="list" onclick="getMaterialUpdate('+ material.id +')"><td class="list">' + material.id + '</td>' +
        '<td class="list">' + material.ingredientid + " - " + material.ingredientname + '</td>' +
        '<td class="list">' + material.amount + ' kg</td>' +
        '<td class="list">' + material.supplier + '</td>' +
        '<td class="list">' + material.date +'</td>';
}

function getMaterialUpdate(i) {

    if (userpriv > 2) {
        $("#bodytest").load("update/updateMaterialBatch.html");
        updateid = i;
    }

}

function updateMaterialData() {
    $.get('rest/material/single/' + updateid, function (data, textStatus, req) {
        document.getElementById('ID').value = data.id;
        document.getElementById('dropdown0').value = data.ingredientid;
        document.getElementById('amount').value = data.amount;
        document.getElementById('supplier').value = data.supplier;
        updateid = 0;
    });
}

function updateMaterial() {

    var $form = $("#updateMaterial");
    var data = getFormData($form);
    var datajson = JSON.stringify(data);

    $.ajax({
        url: 'rest/material/update',
        method: 'POST',

        contentType: "application/json", // det vi sender er json
        data: datajson,
        success: function (datajson) {
            $("#bodytest").load("list/materialBatchList.html");
        },
        error: function (jqXHR){
            alert(jqXHR.responseText);
        }
    });

}

