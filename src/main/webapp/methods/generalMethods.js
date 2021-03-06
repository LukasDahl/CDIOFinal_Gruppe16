// Forfatter: Theis
// Ansvar: Filen indeholder generelle funktioner som anvendes på websiden heriblandt styrringen af login navigationsbaren.


var userpriv = "0";
function colorChosenUser() {
    document.getElementById("userMenu").className = "chosen";
    document.getElementById("ingMenu").className = "dropbtn";
    document.getElementById("recipeMenu").className = "dropbtn";
    document.getElementById("materialMenu").className = "dropbtn";
    document.getElementById("prodMenu").className = "dropbtn";
    document.getElementById("prodBatchMenu").className = "dropbtn";
}
function colorChosenIng() {
    document.getElementById("userMenu").className = "dropbtn";
    document.getElementById("ingMenu").className = "chosen";
    document.getElementById("recipeMenu").className = "dropbtn";
    document.getElementById("materialMenu").className = "dropbtn";
    document.getElementById("prodMenu").className = "dropbtn";
    document.getElementById("prodBatchMenu").className = "dropbtn";
}
function colorChosenRecipe() {
    document.getElementById("userMenu").className = "dropbtn";
    document.getElementById("ingMenu").className = "dropbtn";
    document.getElementById("recipeMenu").className = "chosen";
    document.getElementById("materialMenu").className = "dropbtn";
    document.getElementById("prodMenu").className = "dropbtn";
    document.getElementById("prodBatchMenu").className = "dropbtn";
}
function colorChosenMaterial() {
    document.getElementById("userMenu").className = "dropbtn";
    document.getElementById("ingMenu").className = "dropbtn";
    document.getElementById("recipeMenu").className = "dropbtn";
    document.getElementById("materialMenu").className = "chosen";
    document.getElementById("prodMenu").className = "dropbtn";
    document.getElementById("prodBatchMenu").className = "dropbtn";
}
function colorChosenProd() {
    document.getElementById("userMenu").className = "dropbtn";
    document.getElementById("ingMenu").className = "dropbtn";
    document.getElementById("recipeMenu").className = "dropbtn";
    document.getElementById("materialMenu").className = "dropbtn";
    document.getElementById("prodMenu").className = "chosen";
    document.getElementById("prodBatchMenu").className = "dropbtn";
}
function colorChosenProdBatch() {
    document.getElementById("userMenu").className = "dropbtn";
    document.getElementById("ingMenu").className = "dropbtn";
    document.getElementById("recipeMenu").className = "dropbtn";
    document.getElementById("materialMenu").className = "dropbtn";
    document.getElementById("prodMenu").className = "dropbtn";
    document.getElementById("prodBatchMenu").className = "chosen";
}
function colorChosenHome() {
    document.getElementById("userMenu").className = "dropbtn";
    document.getElementById("ingMenu").className = "dropbtn";
    document.getElementById("recipeMenu").className = "dropbtn";
    document.getElementById("materialMenu").className = "dropbtn";
    document.getElementById("prodMenu").className = "dropbtn";
    document.getElementById("prodBatchMenu").className = "dropbtn";
}

function loginfunc() {

    var $form = $("#loginform");
    var data = getFormData($form);
    var datajson = JSON.stringify(data);
    $.ajax({
        url: 'rest/user/login',
        method: 'POST',
        contentType: "application/json", // det vi sender er json
        data: datajson,
        success: function (datajson) {
            $("#front").load("navbar.html");
            userpriv = datajson
        },
        error: function (jqXHR){
            alert(jqXHR.responseText);
        }
    });
}

function navsetup() {
    switch(userpriv){
        case "1":
            laboAdmin();
            break;
        case "2":
            labo();
            break;
        case "3":
            pleaderAdmin();
            break;
        case "4":
            pleader();
            break;
        case "5":
            pharmaAdmin();
            break;
        case "6":
            pharma();
            break;
        default:
            break;
    }
    loadCurrentUser();
    homepage();
}

function homepage() {
    $("#bodytest").empty().append(homepageHTML());
}

function homepageHTML() {
    return '<br><br><img src="medicine-logo.png" alt="Medicine" width="700px" height="900px" class="center-image">'
}

function loadCurrentUser() {
    $("#currentuser").append(userpriv)
    $.get('rest/user/single', function (data, textStatus, req) {
        $("#currentuser").empty();
        $('#currentuser').append(currentUserHTML(data));

    });

}

function currentUserHTML(user) {
    return $('#currentuser').append('' + user.id + ' - ' + user.ini + ' - ' + user.role);
}

function laboAdmin() {
    $("#ddUser").empty();
    $("#ddUser").append('<a id="useradminpage">Brugerliste</a>');
    $("#ddUser").append('<a id="newuserpage">Ny bruger</a>');
    $("#ddIng").empty();
    $("#ddIng").append('<a id="ingpage">Ingrediensliste</a>');
    $("#ddRecipe").empty();
    $("#ddRecipe").append('<a id="recipepage">Opskriftsliste</a>');
    $("#ddMaterial").empty();
    $("#ddMaterial").append('<a id="materialpage">Batchliste</a>');
    $("#ddProd").empty();
    $("#ddProd").append('<a id="productpage">Produktliste</a>');
    $("#ddProdBatch").empty();
    $("#ddProdBatch").append('<a id="productbatchpage">Batchliste</a>');
}

function labo() {
    $("#ddUser").empty();
    $("#ddUser").append('<a id="useradminpage">Brugerliste</a>');
    $("#ddIng").empty();
    $("#ddIng").append('<a id="ingpage">Ingrediensliste</a>');
    $("#ddRecipe").empty();
    $("#ddRecipe").append('<a id="recipepage">Opskriftsliste</a>');
    $("#ddMaterial").empty();
    $("#ddMaterial").append('<a id="materialpage">Batchliste</a>');
    $("#ddProd").empty();
    $("#ddProd").append('<a id="productpage">Produktliste</a>');
    $("#ddProdBatch").empty();
    $("#ddProdBatch").append('<a id="productbatchpage">Batchliste</a>');
}

function pleaderAdmin() {
    $("#ddUser").empty();
    $("#ddUser").append('<a id="useradminpage">Brugerliste</a>');
    $("#ddUser").append('<a id="newuserpage">Ny bruger</a>');
    $("#ddIng").empty();
    $("#ddIng").append('<a id="ingpage">Ingrediensliste</a>');
    $("#ddRecipe").empty();
    $("#ddRecipe").append('<a id="recipepage">Opskriftsliste</a>');
    $("#ddMaterial").empty();
    $("#ddMaterial").append('<a id="materialpage">Batchliste</a>');
    $("#ddMaterial").append('<a id="newmaterialpage">Ny batch</a>');
    $("#ddProd").empty();
    $("#ddProd").append('<a id="productpage">Produktliste</a>');
    $("#ddProd").append('<a id="newproductpage">Nyt produkt</a>');
    $("#ddProdBatch").empty();
    $("#ddProdBatch").append('<a id="productbatchpage">Batchliste</a>');
    $("#ddProdBatch").append('<a id="newproductbatchpage">Ny batch</a>');
}

function pleader() {
    $("#ddUser").empty();
    $("#ddUser").append('<a id="useradminpage">Brugerliste</a>');
    $("#ddIng").empty();
    $("#ddIng").append('<a id="ingpage">Ingrediensliste</a>');
    $("#ddRecipe").empty();
    $("#ddRecipe").append('<a id="recipepage">Opskriftsliste</a>');
    $("#ddMaterial").empty();
    $("#ddMaterial").append('<a id="materialpage">Batchliste</a>');
    $("#ddMaterial").append('<a id="newmaterialpage">Ny batch</a>');
    $("#ddProd").empty();
    $("#ddProd").append('<a id="productpage">Produktliste</a>');
    $("#ddProd").append('<a id="newproductpage">Nyt produkt</a>');
    $("#ddProdBatch").empty();
    $("#ddProdBatch").append('<a id="productbatchpage">Batchliste</a>');
    $("#ddProdBatch").append('<a id="newproductbatchpage">Ny batch</a>');
}

function pharmaAdmin() {
    $("#ddUser").empty();
    $("#ddUser").append('<a id="useradminpage">Brugerliste</a>');
    $("#ddUser").append('<a id="newuserpage">Ny bruger</a>');
    $("#ddIng").empty();
    $("#ddIng").append('<a id="ingpage">Ingrediensliste</a>');
    $("#ddIng").append('<a id="newingpage">Ny ingrediens</a>');
    $("#ddRecipe").empty();
    $("#ddRecipe").append('<a id="recipepage">Opskriftsliste</a>');
    $("#ddRecipe").append('<a id="newrecipepage">Ny opskrift</a>');
    $("#ddMaterial").empty();
    $("#ddMaterial").append('<a id="materialpage">Batchliste</a>');
    $("#ddMaterial").append('<a id="newmaterialpage">Ny batch</a>');
    $("#ddProd").empty();
    $("#ddProd").append('<a id="productpage">Produktliste</a>');
    $("#ddProd").append('<a id="newproductpage">Nyt produkt</a>');
    $("#ddProdBatch").empty();
    $("#ddProdBatch").append('<a id="productbatchpage">Batchliste</a>');
    $("#ddProdBatch").append('<a id="newproductbatchpage">Ny batch</a>');
}

function pharma() {
    $("#ddUser").empty();
    $("#ddUser").append('<a id="useradminpage">Brugerliste</a>');
    $("#ddIng").empty();
    $("#ddIng").append('<a id="ingpage">Ingrediensliste</a>');
    $("#ddIng").append('<a id="newingpage">Ny ingrediens</a>');
    $("#ddRecipe").empty();
    $("#ddRecipe").append('<a id="recipepage">Opskriftsliste</a>');
    $("#ddRecipe").append('<a id="newrecipepage">Ny opskrift</a>');
    $("#ddMaterial").empty();
    $("#ddMaterial").append('<a id="materialpage">Batchliste</a>');
    $("#ddMaterial").append('<a id="newmaterialpage">Ny batch</a>');
    $("#ddProd").empty();
    $("#ddProd").append('<a id="productpage">Produktliste</a>');
    $("#ddProd").append('<a id="newproductpage">Nyt produkt</a>');
    $("#ddProdBatch").empty();
    $("#ddProdBatch").append('<a id="productbatchpage">Batchliste</a>');
    $("#ddProdBatch").append('<a id="newproductbatchpage">Ny batch</a>');
}