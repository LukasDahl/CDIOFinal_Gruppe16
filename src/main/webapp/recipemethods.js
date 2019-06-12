function setIng(x) {
    var i;
    y = parseInt(x)
    $("#ingtablebody").empty();
    for (i = 0; i < y; i++){
        var j = i + 1;
        $("#ingtablebody").append('<tr>' +
            '<td>'+ j +'</td>' +
            '<td>' +
            '    <select id="dropdown'+i+'" name="ing'+ i +'">' +
            '    <option value="0">V&aelig;lg</option>' +
            '    </select>' +
            '</td>' +
            '<td>' +
            '    <input type="number" name="mængde'+i+'" step="0.0001" min="0.0500" max="20.0000" size="5"/>' +
            '</td>' +
            '<td>' +
            '   <input type="number" name="afvigelse'+i+'" step="0.1" min="0.1" max="10.0" size="5"/>' +
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
        $.each(data, function (i, elt) {
            $dropdown.append($("<option />").val(this.id).text(this.name));
        });
        }
    });
}

function createRecipe() {
    var $form = $("#newrecipe");
    var data = getFormData($form);


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


