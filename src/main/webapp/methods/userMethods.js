var updateuser;
$(document).ready(function () {
    loadUsers();
});

function createUser(){

    var $form = $("#newbruh");
    var data = getFormData($form);
    var datajson = JSON.stringify(data);

    $.ajax({
        url: 'rest/user',
        method: 'POST',

        contentType: "application/json", // det vi sender er json
        data: datajson,
        success: function (datajson) {
            $("#bodytest").load("list/userList.html");
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
    return '<tr class="list" onclick="getUserUpdate('+ user.id +')"><td class="list">' + user.id + '</td>' +
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
function getUserUpdate(i) {

    if (userpriv % 2 === 1) {
        $("#bodytest").load("update/updateUser.html");
        updateuser = i;
    }

}
function updateUserData() {
    $.get('rest/user/single/' + updateuser, function (data, textStatus, req) {
        document.getElementById('ID').value = data.id;
        document.getElementById('username').value = data.username;
        document.getElementById('ini').value = data.ini;
        document.getElementById('cpr').value = data.cpr;
        if (data.role === "Farmaceut"){
            document.getElementById('pharma').checked = "checked";
        }
        else if (data.role === "Produktionsleder"){
            document.getElementById('pleader').checked = "checked";
        }
        else {
            document.getElementById('labo').checked = "checked";
        }
        if (data.admin === "Ja"){
            document.getElementById('adminyes').checked = "checked";
        }
        else {
            document.getElementById('adminno').checked = "checked";
        }
        updateuser = 0;
    });
}

function updateUser() {
    var $form = $("#updatebruh");
    var data = getFormData($form);
    var datajson = JSON.stringify(data);

    $.ajax({
        url: 'rest/user/update',
        method: 'POST',

        contentType: "application/json", // det vi sender er json
        data: datajson,
        success: function (datajson) {
            $("#bodytest").load("list/userList.html");
        },
        error: function (jqXHR){
            alert(jqXHR.responseText);
        }
    });
}