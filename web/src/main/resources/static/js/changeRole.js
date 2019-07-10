function disp(form) {
    if (form.style.display == "none") {
        form.style.display = "inline-grid";
    } else {
        form.style.display = "none";
        // таким оброзом через ajax передается к скрипту который отправляет через метод
        //  $.ajax({
        //      'type':'POST',
        //     'url':'skript.php'
        //  });
    }
}

function dispF(form1, form2) {
    if (form1.style.display == "none") {
        form1.style.display = "inline-block";
        form2.style.display = "none";
    } else {
        form1.style.display = "none";
        form2.style.display = "inline-block";
    }
}