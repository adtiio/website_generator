window.onload = () => {
    const chatButton = document.getElementById("chat-button");
    const chatPopup = document.getElementById("chat-popup");
    const chatForm = document.getElementById("chat-form");
    const chatInput = document.getElementById("chat-input");
    const closeChat = document.getElementById("close-chat");

    // Ensure all elements exist before adding event listeners
    if (chatButton && chatPopup && chatForm && chatInput && closeChat) {
        chatButton.addEventListener("click", () => {
            chatPopup.classList.toggle("show");
        });

        closeChat.addEventListener("click", () => {
            chatPopup.classList.remove("show");
        });

        chatForm.addEventListener("submit", (e) => {
            e.preventDefault();  // Prevent form from submitting normally

            const prompt = chatInput.value.trim();
            if (prompt) {
                fetch("http://localhost:5454/prompt/", {
                    method: "POST",
                    headers: { "Content-Type": "application/json" },
                    body: JSON.stringify({ prompt: prompt }),
                })
                .then(response => {
                    if (response.ok) {
                        // If response is OK, check if there is a redirect URL
                        const redirectUrl = response.url;
                        if (redirectUrl) {
                            window.location.href = redirectUrl;  // Redirect manually to the new page
                        }
                    } else if (response.status === 302 || response.status === 303) {
                        // Handle 302 or 303 redirects manually if necessary
                        window.location.href = response.headers.get("Location");
                    } else {
                        console.error("Network response was not ok.");
                    }
                    chatInput.value = "";  // Clear the input after submission
                })
                .catch(error => {
                    console.error("Error sending prompt:", error);
                });
            }
        });
    } else {
        console.error("One or more elements were not found.");
    }
};


//-------------------------------------------------------

// Get elements
const chatbotBtn = document.getElementById('chatbotBtn');
const chatbotModal = document.getElementById('chatbotModal');
const closeChat = document.getElementById('closeChat');
const sendBtn = document.getElementById('sendBtn');
const userInput = document.getElementById('userInput');
const chatBody = document.querySelector('.chat-body');

// Initially, the modal should be hidden. If it's not, we explicitly set it to hidden.
chatbotModal.style.display = 'none';

// Toggle chatbot modal visibility when clicking the chatbot button
chatbotBtn.addEventListener('click', () => {
    // Check if the modal is hidden or not before changing its state
    if (chatbotModal.style.display === 'none') {
        chatbotModal.style.display = 'flex'; // Show the chatbot modal
    }
});

// Close chatbot modal when clicking the close button
closeChat.addEventListener('click', () => {
    chatbotModal.style.display = 'none'; // Hide the chatbot modal
});

// Send message on button click
sendBtn.addEventListener('click', () => {
    sendMessage();
});

// Send message on Enter key press
userInput.addEventListener('keydown', (event) => {
    if (event.key === 'Enter') {
        event.preventDefault(); // Prevent new line in input
        sendMessage();
    }
});

function sendMessage() {
    const message = userInput.value.trim();
    if (message) {
        appendMessage('user', message);
        userInput.value = '';
        sendMessageToAPI(message);
    }
}

function appendMessage(sender, message) {
    const messageElement = document.createElement('div');
    messageElement.classList.add('message', sender);
    messageElement.textContent = message;
    chatBody.appendChild(messageElement);
    chatBody.scrollTop = chatBody.scrollHeight;
}

async function sendMessageToAPI(message) {
    try {
        const response = await fetch('http://localhost:5454/ai/chat/simple', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ prompt: message }), // Send message as 'prompt'
        });
        const data = await response.json();
        const botMessage = data.candidates[0].content.parts[0].text; // Extract the text part
        appendMessage('bot', botMessage);
    } catch (error) {
        appendMessage('bot', 'Error: Unable to connect to the server.');
    }
}

//----------------------------------
// Function to call the backend API with product index
function viewProductDetails(index) {
    fetch('http://localhost:5454/nextPage', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: `index=${index}`
    })
    .then(response => {
        if (response.redirected) {
            // Redirect to the new page if the backend returns a redirect
            window.location.href = response.url;
        } else {
            console.error("Failed to retrieve product details.");
        }
    })
    .catch(error => console.error("Error:", error));
}