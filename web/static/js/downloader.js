var downloaderStart = function () {
    start = false;
    do {

        $("#logo").removeClass("logo");
       setTimeout(dva(),5000);
    
   

    } while (start != false);


};
function dva() {
    $("#logo").addClass("logo");
    
}
var start = false;




