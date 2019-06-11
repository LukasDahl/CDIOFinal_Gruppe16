function setIng(x) {
    var i;
    y = parseInt(x)
    $("#ingtablebody").empty();
    for (i = 0; i < y; i++){
        var j = i + 1;
        $("#ingtablebody").append('<tr>' +
            '<td>'+ j +'</td>' +
            '<td>' +
            '    <select name="ing"'+ i +'>' +
            '    <option value="0">V&aelig;lg antal:</option>' +
            '    <option value="1">1</option>' +
            '    <option value="2">2</option>' +
            '    <option value="3">3</option>' +
            '    <option value="4">4</option>' +
            '    <option value="5">5</option>' +
            '    <option value="5">6</option>' +
            '    </select>' +
            '</td>' +
            '<td>' +
            '    <input type="text" name="mængde'+ i +'" size="7"/>' +
            '</td>' +
            '<td>' +
            '   <input type="text" name="tolerence'+ i +'" size="7">' +
            '</td>' +
            '</tr>');
    }
}