<!--
Forfatter: Lukas
Ansvar: Filen indeholder funktioner som anvendes til at oprette og indlæse ingredienshistorikken på websiden.
-->

function loadIngredientHistory() {
    $.get('rest/ingredientHistory/' + updateid, function (data, textStatus, req) {
        $("#inghistablebody").empty();
        $.each(data, function (i, elt) {
            $('#inghistablebody').append(generateIngHisHTML(elt));
        });
        $("#inghistablehead").empty();
        $("#inghistablehead").append('<tr class="list">' +
            '        <th class="list">Ingrediens ID</th>' +
            '        <th class="list">Ingrediens navn</th>' +
            '        <th class="list">Bruger</th>' +
            '        <th class="list">Dato</th>' +
            '    </tr>');
        document.getElementById('update').setAttribute("onclick", 'getIngredientUpdate('+updateid+')');


    });


}

function generateIngHisHTML(ing) {
    return '<tr class="list">' +
        '<td class="list">' + ing.id + '</td>' +
        '<td class="list">' + ing.name + '</td>' +
        '<td class="list">' + ing.userID + '</td>' +
        '<td class="list">' + ing.date + '</td>';
}

function getIngredientUpdate(i) {

    if (userpriv > 4) {
        $("#bodytest").load("update/updateIngredient.html");
        updateid = i;
    }

}