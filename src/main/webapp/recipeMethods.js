function setIng(x) {
    var i;
    y = parseInt(x)
    $("#ingtablebody").empty();
    for (i = 0; i < y; i++){
        var j = i + 1;
        $("#ingtablebody").append('<tr>' +
            '<td>'+ j +'</td>' +
            '<td>' +
            '    <select id="dropdown'+i+'">' +
            '    <option value="0">V&aelig;lg</option>' +
            '    </select>' +
            '</td>' +
            '<td>' +
            '    <input type="number" id="mængde'+i+'" step="0.0001" min="0.0500" max="20.0000" size="5"/>' +
            '</td>' +
            '<td>' +
            '   <input type="number" id="afvigelse'+i+'" step="0.1" min="0.1" max="10.0" size="5"/>' +
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
            $("#bodytest").load("recipelist.html");
        },
        error: function (jqXHR){
            alert(jqXHR.responseText);
        }
    });
}


