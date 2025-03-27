var form = document.getElementById("form-validation");
form.addEventListener("submit", function(event) {
      if ( document.getElementById("validation2").value != document.getElementById("validation3").value ) {
          alert("Password mismatch");
          event.preventDefault();
          event.stopPropagation();
      }
      else if (form.checkValidity() == false) {
          event.preventDefault();
          event.stopPropagation();
      }
      form.classList.add("was-validated");
 }, false);
