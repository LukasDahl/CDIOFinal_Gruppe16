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
            $("#bodytest").load("materialBatchList.html");
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
            '        <th class="list">RåvareID</th>' +
            '        <th class="list">Navn</th>' +
            '        <th class="list">Mængde</th>' +
            '        <th class="list">Leverandør</th>' +
            '        <th class="list">Dato</th>' +
            '    </tr>');
    });

}
function generateMaterialHTML(material) {
    return '<tr class="list"><td class="list">' + material.id + '</td>' +
        '<td class="list">' + material.ingredientid + " " + material.ingredientname + '</td>' +
        '<td class="list">' + material.amount + '</td>' +
        '<td class="list">' + material.supplier + '</td>' +
        '<td class="list">' + material.date +'</td>';
}