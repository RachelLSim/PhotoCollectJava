const cookieArr = document.cookie.split('=')
const userId = cookieArr[1]

const submitForm = document.getElementById("post-form")
const postContainer = document.getElementById("post-container")

//modal elements
let postUrl = document.getElementById(`post-input-url`)
let postCaption = document.getElementById(`post-input-caption`)
let updatePostBtn = document.getElementById('update-post-button')

const headers = {
    'Content-Type': 'application/json'
}

const baseUrl = "http://localhost:8090/api/v1/posts"

function handleLogout(){
    let c = document.cookie.split(";")
    for(let i in c){
        document.cookie = /^[^=]+/.exec(c[i])[0]+"=;expires=Thu, 01 Jan 1970 00:00:00 GMT"
    }
}

const handleSubmit = async (e) => {
    e.preventDefault()
    let bodyObj = {
        url: document.getElementById('post-input-url').value,
        caption: document.getElementById('post-input-caption').value
    }
    await addPost(bodyObj);
    document.getElementById("post-input").value = ""
}

async function addPost(obj) {
    const response = await fetch(`${baseUrl}/user/${userId}`, {
        method: "POST",
        body: JSON.stringify(obj),
        headers: headers
    })
        .catch(err => console.error(err.message))
    if(response.status == 200) {
        return getPosts(userId)
    }
}

async function getPosts(userId){
    await fetch(`${baseUrl}/user/${userId}`, {
        method: "GET",
        headers: headers
    })
        .then(response => response.json())
        .then(data => createPostCards(data))
        .catch(err => console.error(err))
}

async function getPostById(postId){
    await fetch(baseUrl + postId, {
        method: "GET",
        headers: headers
    })
        .then(res => res.json())
        .then(data => populateModal(data))
        .catch(err => console.error(err.message))
}

async function handlePostEdit(postId){
    let bodyObj = {
        id:  postId,
        url: postUrl.value,
        caption: postCaption.value
    }

    await fetch(baseUrl, {
        method: "PUT",
        body: JSON.stringify(bodyObj),
        headers: headers
    })
        .catch(err => console.error(err))
    return getPosts(userId)
}

async function handleDelete(postId){
    await fetch(baseUrl + postId, {
        method: "DELETE",
        headers: headers
    })
        .catch(err => console.error(err))

    return getPosts(userId)
}

const createPostCards = (array) => {
    postContainer.innerHTML = ''
    array.forEach(obj => {
        let postCard = document.createElement("div")
        postCard.classList.add("m-2")
        postCard.innerHTML = `
        <div class="card d-flex" style="width: 18rem; height: 18rem">
            <div class="card-body d-flex flex-column justify-content-between" style="height: available">
                <p class="card-image">${obj.url}</p>
                <p class="card-caption">${obj.caption}</p>
                <div class="d-flex justify-content-between">
                    <button class="btn btn-danger" onclick="handleDelete(${obj.id})">Delete</button>
                    <button onclick="getPostById(${obj.id})" type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#post-edit-modal">
                    Edit
                    </button>
                </div>
            </div>
        </div>
        `
      postContainer.append(postCard)
    })
}

const populateModal = (obj) =>{
    postUrl.innerText = ''
    postCaption.innerText = ''
    postUrl.innerText = obj.url
    postCaption.innerText = obj.caption
    updatePostBtn.setAttribute('data-post-id', obj.id)
}

getPosts(userId)

submitForm.addEventListener("submit", handleSubmit)
updatePostBtn.addEventListener("click", (e)=>{
    let postId = e.target.getAttribute("data-post-id")
    handlePostEdit(postId);
})

