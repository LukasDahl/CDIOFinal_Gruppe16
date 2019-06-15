function createIngredient(){

    var $form = $("#newing");
    var data = getFormData($form);
    var datajson = JSON.stringify(data);

    console.log(datajson)
    $.ajax({
        url: 'rest/ingredient',
        method: 'POST',

        contentType: "application/json", // det vi sender er json
        data: datajson,
        success: function (datajson) {
            $("#bodytest").load("list/ingredientList.html");
        },
        error: function (jqXHR){
            alert(jqXHR.responseText);
        }
    });

}

function loadIngredients() {
    $.get('rest/ingredient', function (data, textStatus, req) {
        $("#ingtablebody").empty();
        $.each(data, function (i, elt) {
            $('#ingtablebody').append(generateIngHTML(elt));
        });
        $("#ingtablehead").empty();
        $("#ingtablehead").append('<tr class="list">' +
            '        <th class="list">Ingrediens ID</th>' +
            '        <th class="list">Ingrediens navn</th>' +
            '    </tr>');
    });
}
function generateIngHTML(ing) {
    return '<tr class="list"><td class="list">' + ing.id + '</td>' +
        '<td class="list">' + ing.name + '</td>';
}