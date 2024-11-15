const description = document.getElementById("description");
const listContainer = document.getElementById("list-container")

function addTask() {
    if (description.value === '') {
        alert("You must write something!");
    }
    else {
        let li = document.createElement("li");
        li.innerHTML = description.value;
        listContainer.appendChild(li);
        let span = document.createElement("span");
        span.innerHTML = "\u00d7";
        li.appendChild(span);
    }
    description.value = "";
}



listContainer.addEventListener("click", (e) => {
    if(e.target.tagName === "LI") {
        e.target.classList.toggle("checked");
    }
    else if (e.target.tagName === "SPAN") {
        e.target.parentElement.remove();
    }
}, false);
