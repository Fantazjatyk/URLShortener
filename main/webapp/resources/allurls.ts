$(document).ready(el=>{
    
    $.getJSON(document.location.toString(), (data)=>{
        console.log(data);
    })
});

function drawTable(data:{original, shorten, clicks, creationDate}){
var root = document.createElement("table");

}

function createRow(data:{original, shorten, clicks, creationDate}){
    var row = document.createElement("row");
    var link = document.createElement("a");
    link.innerHTML original;
    var original = createColumn()

}

function createColumn(data){

}