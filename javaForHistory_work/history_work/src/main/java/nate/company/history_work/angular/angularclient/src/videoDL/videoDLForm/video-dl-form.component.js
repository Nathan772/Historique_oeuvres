
/*
from : https://www.tutorjoes.in/JS_tutorial/dynamic_circular_progress_bar_in_javascript
*/


/*window.onload = function() {
                var container = document.getElementById('container');
};*/
//wait for container
window.addEventListener('DOMContentLoaded', () => {
    document.querySelector('#container').addEventListener('click', () => {
    console.log('You clicked me!')
    });


                var container = document.getElementById('container');
                //alert(container);
                //console.log("élément récupéré : "+container)
                //console.log("début du chargement !");

                /* C'est color qui gère la couleur du cercle de chargement de vidéo
                percent indique le moment d'arrêt
                course, lorsqu'il est associé à la bonne balise dans le html, indique
                le pourcentage d'avancement (mais il ne sera
                pas utilisé ici car on ne le connait pas)
                */
                const courses = [
                  { course: "preparationVideo", percent: 100, color: "#DC143C" },

                ];

                let isLoading = true;
                const progressGroups = document.querySelectorAll(".progress-group");

                progressGroups.forEach((progress, index) => {


                          //console.log("passage dans chargement ...")



                          let progressStartEnd = courses[index].percent;
                          let progressStartValue = 0;
                          /*
                          period of time betweeen two calls, even though the previous is not finished yet.
                          */
                          let repeat = 10;


                            //peut être qu'un progressTimer n'est pas réutilisable

                            //for(let compteur= 0;compteur<10;compteur++){
                            //set interval enables to repeat an
                            //action every "X" times (time is in millisecond)
                              let progressTimer = setInterval(
                              /*lambda definition */
                              () => {


                                  //console.log("on entre dans progresse timer");
                                  /*for(let i = 0;i<50;i++){
                                    ;
                                    progressStartValue++;*/
                                    progressStartValue++;
                                    //console.log("la valeur de progressStart est : "+progressStartValue);
                                    if (progressStartValue == progressStartEnd) {
                                      // this part allow to stop definitly the lambda call (nop??)
                                      clearInterval(progressTimer)
                                      //console.log("la valeur de progress value : "+progressStartValue)
                                      //come back to the beginning
                                      //until everything is loaded (here everything is loaded is represented
                                      //by the compteur
                                      //progressStartValue = 0;

                                    }
                                    //console.log("la valeur du compteur :"+compteur)
                                    progress.querySelector(".circular-progress").style.background = `
                                    conic-gradient(${courses[index].color} ${3.6 * progressStartValue}deg, #fff 0deg)`;


                                    //console.log("la valeur de i :"+i)
                                    progress.querySelector(".circular-progress").innerHTML = progressStartValue + "%";

                                    //progress.querySelector(".circular-progress").style.background = `
                                                                  //conic-gradient("#FFFFFF" ${3.6 * progressStartValue}deg, #fff 0deg)`;
                                    /*
                                    bloque l'affichage de la barre à une faible valeur*/
                                    if(progressStartValue >= 80){
                                      progressStartValue=0;
                                    }
                                  //} //linked to for (deprecated

                                     //}
                                    }
                                   /* end of lambda */
                              , repeat);
                            //one more turn for loading
                            //compteur++;
                            //}



                        //kill definitly the loading after 5 minutes, even if it's not finished
                        /*
                        setTimeout(() => { clearInterval(progressTimer); alert('stop')}, 30000);
                        */

                        });



                        console.log("on sort de l'affichage de la barre de chargement ");


});

