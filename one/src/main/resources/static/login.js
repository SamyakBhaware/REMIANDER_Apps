document.getElementById('loginForm').addEventListener('submit', function(event) {
    event.preventDefault(); // Default form submission roko

    // 1. Hardcoded Credentials (Backend Simulation)
    const DUMMY_USER = 'iitian2028';
    const DUMMY_PASS = 'jee@cracked';

    // 2. User Input
    const username = document.getElementById('username').value.trim();
    const password = document.getElementById('password').value.trim();
    const messageDiv = document.getElementById('message');

    // Reset message
    messageDiv.textContent = '';
    messageDiv.style.backgroundColor = 'transparent';

    // 3. Authentication Check
    if (username === DUMMY_USER && password === DUMMY_PASS) {
        // SUCCESS
        messageDiv.textContent = ` Welcome Back, ${username}! Login Successful.`;
        messageDiv.style.backgroundColor = '#d4edda'; // Light green
        messageDiv.style.color = '#155724'; // Dark green

        // Real life mein yahan user ko dashboard pe redirect karte hain
        console.log("Login Success: Redirecting...");
        // this.reset();
    } else {
        // ERROR
        messageDiv.textContent = 'Invalid Username or Password. Please check your credentials.';
        messageDiv.style.backgroundColor = '#f8d7da'; // Light red
        messageDiv.style.color = '#721c24'; // Dark red
    }
});