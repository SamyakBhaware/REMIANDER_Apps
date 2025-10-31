document.getElementById('loginForm').addEventListener('submit', function(event) {
    event.preventDefault(); // Default form submission roko

    // 2. User Input
    const email = document.getElementById('email').value.trim();
    const password = document.getElementById('password').value.trim();
    const messageDiv = document.getElementById('message');

    // Reset message
    messageDiv.textContent = '';
    messageDiv.style.backgroundColor = 'transparent';


    const userData = {
        email: email,
        password: password
    };


    // 2. FETCH API: Data ko Server (API) pe bhejna
    fetch('http://localhost:8080/api/login', {
        method: 'POST', // Hum server pe data save kar rahe hain, isliye POST
        headers: {
            'Content-Type': 'application/json' // Server ko bata rahe hain ki data JSON hai
        },
        body: JSON.stringify(userData)
        // JavaScript object ko JSON string mein badalna
    })
        .then(responseText => {
            // 1. New response check: User already exists
            if (responseText.startsWith('exists:')) {
                // 'exists:' prefix ko hatakar actual message nikal lo
                const existsMessage = responseText.substring(7);

                messageDiv.textContent = '⚠️ ' + existsMessage;
                messageDiv.style.backgroundColor = '#fff3cd'; // Yellow for warning
                messageDiv.style.color = '#856404';

            } else if (responseText === 'success') {
                messageDiv.textContent = 'Login Successful!';
                messageDiv.style.backgroundColor = '#d4edda';
                messageDiv.style.color = '#155724';
                this.reset();

            } else if (responseText.startsWith('error:')) {
                // ... (Existing error handling)
                messageDiv.textContent = `Server Error: ${responseText}`;
                messageDiv.style.backgroundColor = '#f8d7da';
                messageDiv.style.color = '#721c24';

            } else {
                // ... (Existing unexpected response handling)
                messageDiv.textContent = 'Unexpected server response. Please try again.';
                messageDiv.style.backgroundColor = '#f8d7da';
                messageDiv.style.color = '#721c24';
            }
        })
        .catch(error => {
            // Step 5: ERROR! Network error ya server side error
            console.error('Login failed:', error);
            messageDiv.textContent = 'Login Failed! ' + error.message;
            messageDiv.style.backgroundColor = '#f8d7da'; // Light red
            messageDiv.style.color = '#721c24';
        });

});