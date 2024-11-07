/*
créé avec "ng generate class user"
puis remplit avec le ce qui est écrit sur baeldung...

pb : types incompatibles
lors de la récupération du user depuis la bdd???
*/
export interface User {
  id:number;
  pseudo:string;
  email:string;
  password:string;
  category:string;
}

export interface copyData {
    passwordCopy:string;
}


