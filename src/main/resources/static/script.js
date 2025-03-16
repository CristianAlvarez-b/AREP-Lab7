const API_URL = "http://localhost:8080";
let replyToPostId = null;

async function loadUsers() {
    try {
        let response = await fetch(`${API_URL}/users`);
        let users = await response.json();

        let options = users.map(user => `<option value="${user.id}">${user.username}</option>`).join("");
        document.getElementById("userSelect").innerHTML = options;
    } catch (error) {
        console.error("Error al cargar usuarios:", error);
    }
}

async function loadPosts() {
    try {
        let response = await fetch(`${API_URL}/posts`);
        let posts = await response.json();

        let postList = posts.map(post => `
            <div class='post'>
                <strong>${post.user.username}:</strong> ${post.content}
                <button onclick="openReplyModal(${post.id}, '${post.user.username}')">Responder</button>
                <div class="replies">
                    ${post.reposts ? post.reposts.map(repost => `<div class='reply'><strong>${repost.user.username}:</strong> ${repost.content}</div>`).join("") : ""}
                </div>
            </div>
        `).join("");

        document.getElementById("posts").innerHTML = postList;
    } catch (error) {
        console.error("Error al cargar posts:", error);
    }
}
function openReplyModal(postId, username) {
    replyToPostId = postId; // Guardamos el ID del post al que vamos a responder
    document.getElementById("replyToUser").innerText = username;
    document.getElementById("replyModal").style.display = "block";
}

function closeReplyModal() {
    replyToPostId = null;
    document.getElementById("replyContent").value = "";
    document.getElementById("replyModal").style.display = "none";
}

async function createRepost() {
    let repost = {
        content: document.getElementById("replyContent").value,
        userId: document.getElementById("userSelect").value, // Usuario que responde
        postId: replyToPostId // ID del post al que responde
    };

    try {
        let response = await fetch(`${API_URL}/streams`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(repost)
        });

        if (!response.ok) throw new Error("Error al crear repost");

        closeReplyModal();
        await loadPosts();
    } catch (error) {
        console.error(error.message);
    }
}
async function updateUserSelectors() {
    try {
        let response = await fetch(`${API_URL}/users`);
        if (!response.ok) throw new Error("Error al obtener usuarios");

        let users = await response.json();
        let options = users.map(user => `<option value="${user.id}">${user.username}</option>`).join("");

        // Asignar opciones a los selectores de respuesta en cada post
        document.querySelectorAll("[id^=replyUserSelect-]").forEach(select => {
            select.innerHTML = options;
        });
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
            headers: { "Content-Type": "application/json" },
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
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(postData)
        });

        if (!response.ok) throw new Error("Error al crear post");

        document.getElementById("postContent").value = "";
        await loadPosts();
    } catch (error) {
        console.error(error.message);
    }
}

function showReplyForm(postId) {
    let replyForm = document.getElementById(`replyForm-${postId}`);
    replyForm.style.display = replyForm.style.display === "none" ? "block" : "none";
}

async function createReply(postId) {
    let userId = document.getElementById(`replyUserSelect-${postId}`).value;
    let content = document.getElementById(`replyContent-${postId}`).value.trim();

    if (!content) {
        alert("El contenido de la respuesta no puede estar vacío.");
        return;
    }

    let replyData = {
        content: content,
        userId: Number(userId),
        postId: postId
    };

    try {
        let response = await fetch(`${API_URL}/posts/${postId}/replies`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(replyData)
        });

        if (!response.ok) throw new Error("Error al crear respuesta");

        document.getElementById(`replyContent-${postId}`).value = "";
        await loadPosts();
    } catch (error) {
        console.error(error.message);
    }
}

document.addEventListener("DOMContentLoaded", () => {
    loadUsers();
    loadPosts();
});
