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
            $("#bodytest").load("brugerliste.html");
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
    });
}

function generateUserHTML(user) {
    return '<tr><td>' + user.id + '</td>' +
        '<td>' + user.username + '</td>' +
        '<td>' + user.ini + '</td>' +
        '<td>' + user.cpr + '</td>' +
        '<td>' + user.role + '</td>';
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