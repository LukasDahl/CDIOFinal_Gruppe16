<!--
Forfatter: Lukas
Ansvar: Filen indeholder funktioner som anvendes til at oprette, opdatere, indhente og indlæse listen over opskrifter på websiden.
-->

function setIng(x) {
    var i;
    y = parseInt(x)
    $("#ingtablebody").empty();
    for (i = 0; i < y; i++){
        var j = i + 1;
        $("#ingtablebody").append('<tr>' +
            '<td width="7%" class="nestedlist">'+ j +'</td>' +
            '<td class="nestedlist" width="35%">' +
            '    <select id="dropdown'+i+'" class="narrow">' +
            '    <option value="0">V&aelig;lg</option>' +
            '    </select>' +
            '</td>' +
            '<td class="nestedlist" width="29%">' +
            '    <input type="number" id="mængde'+i+'">' +
            '</td>' +
            '<td class="nestedlist" width="29%">' +
            '   <input type="number" id="afvigelse'+i+'">' +
            '</td>' +
            '</tr>');
    }
    getIngNames();
}

function getIngNames() {
    $.get('rest/ingredient', function (data, textStatus, req) {
        var j;
        for (j=0; j < 6; j++){
            var $dropdown = $("#dropdown" + j);
            $dropdown.empty();
            $dropdown.append($('<option value="0">V&aelig;lg</option>'));
            $.each(data, function (i, elt) {
                $dropdown.append($("<option />").val(this.id).text(this.name));
            });
        }
    });
}

function getProdNames() {
    $.get('rest/product', function (data, textStatus, req) {
        var $dropdown = $("#productDropdown");
        $dropdown.empty();
        $dropdown.append($('<option value="0">V&aelig;lg</option>'));
        $.each(data, function (i, elt) {
            $dropdown.append($("<option />").val(this.id).text(this.name));
        });
    });
}

function createRecipe() {
    var $form = $("#newrecipe");
    var data = getFormData($form);
    console.log(data);
    var i;
    var ingrediens = [];
    var mængde = [];
    var afvigelse = [];
    for (i = 0; i < data.antal; i++){
        ingrediens.push(document.getElementById("dropdown" + i).value);
        mængde.push(document.getElementById("mængde" + i).value);
        afvigelse.push(document.getElementById("afvigelse" + i).value);
    }
    data.ingrediens=ingrediens;
    data.mængde=mængde;
    data.afvigelse=afvigelse;

    var datajson = JSON.stringify(data);

    console.log(datajson)
    $.ajax({
        url: 'rest/recipe',
        method: 'POST',

        contentType: "application/json", // det vi sender er json
        data: datajson,
        success: function (datajson) {
            $("#bodytest").load("list/recipeList.html");
        },
        error: function (jqXHR){
            alert(jqXHR.responseText);
        }
    });
}

function loadRecipes() {
    $.get('rest/recipe', function (data, textStatus, req) {
        $("#recipetablebody").empty();
        $.each(data, function (i, elt) {
            $('#recipetablebody').append(generateRecipeHTML(elt));
        });
        $("#recipetablehead").empty();
        $("#recipetablehead").append('<tr class="list">' +
            '        <th class="list">Opskrifts-ID</th>' +
            '        <th class="list">Produktnavn</th>' +
            '        <th class="list">Antal af ingredienser</th>' +
            '        <th class="list">Oprettelsesdato</th>' +
            '    </tr>');
    });
}

function generateRecipeHTML(recipe) {
    return '<tr class="list" id="row' + recipe.id + '" onclick="loadSingleRecipe('+ recipe.id +')">' +

        '<td class="list">' + recipe.id + '</td>' +
        '<td class="list">' + recipe.product + '</td>' +
        '<td class="list">' + recipe.antal + '</td>' +
        '<td class="list">' + recipe.dato + '</td>';
}

function loadSingleRecipe(recipe) {
    $.get('rest/recipe/single/'+recipe, function (data, textStatus, req) {
        $("#bodytest").empty();
        $('#bodytest').append(generateSingleRecipeHTML(data));

    });
}
function generateSingleRecipeHTML(recipe) {
    var r = '<div class="border">' +
        '    <h2 class="center">'+ recipe.id + ' - ' + recipe.product + '</h2>' +
        '       <div style="text-align: center;">' +
        '           <div style="display: inline-block; text-align: left;">';

    r +='<table class="nestedlist">' +
        '    <thead>\n' +
        '         <tr class="list">' +
        '              <th class="list">Ingrediens</th>' +
        '              <th class="list">M&aelig;ngde</th>' +
        '              <th class="list">Afvigelse</th>' +
        '        </tr>' +
        '    </thead>' +
        '    <tbody>';

    $.each(recipe.ingrediens, function (i, elt) {
        r += '<tr class="list"><td class="list">' + recipe.ingrediens[i] + '<td class="list">' + recipe.mængde[i] + ' kg<td class="list">' + recipe.afvigelse[i]+ ' &#37;</tr>';
    });

    r +='    </tbody>' +
        '</table><br>' +
        '&nbsp</div>' +
        '</div>' +
        '</div>';
    return r;
}