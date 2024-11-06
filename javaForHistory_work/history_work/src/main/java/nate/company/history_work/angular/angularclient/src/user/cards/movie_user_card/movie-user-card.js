/*export.test = function(){
 console.log("hello javascript");
}
*/
//lookout don't use export
//unless you want to export
//the function
//otherwise it causes bug to script recognition
function showHiddenStatus() {
  console.log("hello hidden status");
  document.getElementById("status").display("none");
}
/*
console.log("hello javascript");
*/

window.addEventListener('DOMContentLoaded', () => {
    document.querySelector('#statusButton').addEventListener('click', () => {
                                               console.log('You clicked me!')
                                               })

});


  /*if(el){
    console.log("status button trouvé");
    //el.addEventListener('click',showHiddenStatus, false);
    el.onclick = function(){
      alert("He's clicked ");
    }
  }
  else{
    console.log("statusButton n'a pas été trouvé !");
  }
  console.log("page is fully loaded");
};*/


function greet(){
console.log("hello, it's me, greet");
}
/*
alert("Pop up");*/
