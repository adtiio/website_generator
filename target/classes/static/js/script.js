// Get elements
const chatbotBtn = document.getElementById('chatbotBtn');
const chatbotModal = document.getElementById('chatbotModal');
const closeChat = document.getElementById('closeChat');
const sendBtn = document.getElementById('sendBtn');
const userInput = document.getElementById('userInput');
const chatBody = document.querySelector('.chat-body');

// Toggle chatbot modal
chatbotBtn.addEventListener('click', () => {
  chatbotModal.style.display = 'flex'; // Show the chatbot modal
});

// Close chatbot modal
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