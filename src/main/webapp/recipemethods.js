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
            '    <input type="text" name="mængde'+ i +'" size="6"/>' +
            '</td>' +
            '<td>' +
            '   <input type="text" name="tolerence'+ i +'" size="6">' +
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
