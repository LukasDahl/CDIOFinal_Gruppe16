$(document).ready(function () {
    loadUsers();
});

function createUser(){

    var $form = $("#newbruh");
    var data = getFormData($form);
    var datajson = JSON.stringify(data);

    console.log(datajson)
    $.ajax({
        url: 'rest/user',
        method: 'POST',

        contentType: "application/json", // det vi sender er json
        data: datajson,
        success: function (datajson) {
            $("#bodytest").load("userList.html");
        },
        error: function (jqXHR){
            alert(jqXHR.responseText);
        }
    });

}

function loadUsers() {
    $.get('rest/user', function (data, textStatus, req) {
        $("#usertablebody").empty();
        $.each(data, function (i, elt) {
            $('#usertablebody').append(generateUserHTML(elt));
        });
        $("#usertablehead").empty();
        $("#usertablehead").append('<tr class="list">' +
            '        <th class="list">ID</th>' +
            '        <th class="list">Brugernavn</th>' +
            '        <th class="list">Initialer</th>' +
            '        <th class="list">CPR</th>' +
            '        <th class="list">Roller</th>' +
            '        <th class="list">Admin</th>' +
            '    </tr>');
    });
}

function generateUserHTML(user) {
    return '<tr class="list"><td class="list">' + user.id + '</td>' +
        '<td class="list">' + user.username + '</td>' +
        '<td class="list">' + user.ini + '</td>' +
        '<td class="list">' + user.cpr + '</td>' +
        '<td class="list">' + user.role + '</td>' +
        '<td class="list">' + user.admin + '</td>';
}

function getID(){
    $.get('rest/user/id', function (data){
        document.getElementById('ID').value = data;
    });
}


function getFormData($form){
    var unindexed_array = $form.serializeArray();
    var indexed_array = {};

    $.map(unindexed_array, function(n, i){
        indexed_array[n['name']] = n['value'];
    });

    return indexed_array;
}