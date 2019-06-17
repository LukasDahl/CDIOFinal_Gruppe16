var updateid;
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
            '        <th class="list">Aktiv</th>' +
            '    </tr>');
    });
}

function generateUserHTML(user) {
    return '<tr class="list" onclick="getUserUpdate('+ user.id +')"><td class="list">' + user.id + '</td>' +
        '<td class="list">' + user.username + '</td>' +
        '<td class="list">' + user.ini + '</td>' +
        '<td class="list">' + user.cpr + '</td>' +
        '<td class="list">' + user.role + '</td>' +
        '<td class="list">' + user.admin + '</td>' +
        '<td class="list" bgcolor="#' + activeColor(user.aktiv) + '">' + user.aktiv + '</td>';
}

function activeColor(active) {
    if (active === "Aktiv"){
        return "8fbc8f";
    }
    else {
        return "cs5c5c";
    }
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
        updateid = i;
    }

}
function updateUserData() {
    $.get('rest/user/single/' + updateid, function (data, textStatus, req) {
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
        else if (data.role ==="Laborant") {
            document.getElementById('labo').checked = "checked";
        }
        else {
            document.getElementById('inaktiv').checked = "checked";
        }

        if (data.admin === "Ja"){
            document.getElementById('adminyes').checked = "checked";
        }
        else {
            document.getElementById('adminno').checked = "checked";
        }
        updateid = 0;
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

function insert(main_string, ins_string, pos) {
    if(typeof(pos) == "undefined") {
        pos = 0;
    }
    if(typeof(ins_string) == "undefined") {
        ins_string = '';
    }
    return main_string.slice(0, pos) + ins_string + main_string.slice(pos);
}