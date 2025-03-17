const API_URL = "https://cc95-181-237-183-246.ngrok-free.app";

async function loadUsers() {
    try {
        let response = await fetch(`${API_URL}/users`, {
            headers: { "ngrok-skip-browser-warning": "true" }
        });
        let users = await response.json();

        let options = users.map(user => `<option value="${user.id}">${user.username}</option>`).join("");
        document.getElementById("userSelect").innerHTML = options;
    } catch (error) {
        console.error("Error al cargar usuarios:", error);
    }
}

async function loadPosts() {
    try {
        let response = await fetch(`${API_URL}/posts`, {
            headers: { "ngrok-skip-browser-warning": "true" }
        });
        let posts = await response.json();

        let postList = posts.map(post => {
            let date = new Date(post.timestamp);
            let formattedDate = date.toLocaleString();

            return `
                <div class='post'>
                    <strong>${post.user.username}:</strong> ${post.content} <br>
                    <small>Publicado el: ${formattedDate}</small>
                </div>
            `;
        }).join("");

        document.getElementById("posts").innerHTML = postList;
    } catch (error) {
        console.error("Error al cargar posts:", error);
    }
}

async function updateUserSelectors() {
    try {
        let response = await fetch(`${API_URL}/users`, {
            headers: { "ngrok-skip-browser-warning": "true" }
        });

        if (!response.ok) throw new Error("Error al obtener usuarios");

        let users = await response.json();
        let options = users.map(user => `<option value="${user.id}">${user.username}</option>`).join("");

    } catch (error) {
        console.error("Error al actualizar selectores de usuario:", error);
    }
}

async function createUser() {
    let user = {
        username: document.getElementById("username").value.trim(),
        email: document.getElementById("email").value.trim()
    };

    if (!user.username || !user.email) {
        alert("Por favor, complete todos los campos.");
        return;
    }

    try {
        let response = await fetch(`${API_URL}/users`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "ngrok-skip-browser-warning": "true"
            },
            body: JSON.stringify(user)
        });

        if (!response.ok) throw new Error("Error al crear usuario");

        document.getElementById("username").value = "";
        document.getElementById("email").value = "";
        await loadUsers();
    } catch (error) {
        console.error(error.message);
    }
}

async function createPost() {
    let userId = document.getElementById("userSelect").value;
    let content = document.getElementById("postContent").value.trim();

    if (!content) {
        alert("El contenido no puede estar vacío.");
        return;
    }

    let postData = {
        content: content,
        userId: Number(userId),
    };

    try {
        let response = await fetch(`${API_URL}/posts`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "ngrok-skip-browser-warning": "true"
            },
            body: JSON.stringify(postData)
        });

        if (!response.ok) throw new Error("Error al crear post");

        document.getElementById("postContent").value = "";
        await loadPosts();
    } catch (error) {
        console.error(error.message);
    }
}

document.addEventListener("DOMContentLoaded", () => {
    loadUsers();
    loadPosts();
});
