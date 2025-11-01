document.getElementById('registrationForm').addEventListener('submit', function(event) {
    event.preventDefault(); // Server ko bhej na de

    // Variables
    const name = document.getElementById('name').value.trim();
    const email = document.getElementById('email').value.trim();
    const password = document.getElementById('password').value.trim();
    const messageDiv = document.getElementById('message');

    let isValid = true;
    let errorMessage = '';

    // Step 1: Password Length Check (IITian-Level Security Basic)
    if (password.length < 8) {
        errorMessage = 'Password must be at least 8 characters long.';
        isValid = false;
    }

    // Step 2: Basic Email Format Check
    else if (!email.includes('@') || !email.includes('.')) {
        errorMessage = ' Please enter a valid email address.';
        isValid = false;
    }

    // Step 3: name Check
    else if (name.length === 0) {
        errorMessage = 'name cannot be empty.';
        isValid = false;
    }

    // --- Result Handling ---
    if (isValid) {

        // Data jo API ko bhejna hai (JSON format mein)
        const userData = {
            name: name,
            email: email,
            password: password
        };

        // Message dikhao ki data bhej rahe hain
        messageDiv.textContent = 'Registering user... Please wait.';
        messageDiv.style.backgroundColor = '#fff3cd'; // Yellow for waiting
        messageDiv.style.color = '#856404';


        // 2. FETCH API: Data ko Server (API) pe bhejna
         fetch('http://localhost:8080/api/register', {
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
                     messageDiv.textContent = 'Registration Successful! You can now log in.';
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
                console.error('Registration failed:', error);
                messageDiv.textContent = 'Registration Failed! ' + error.message;
                messageDiv.style.backgroundColor = '#f8d7da'; // Light red
                messageDiv.style.color = '#721c24';
            });

         console.log("registration request sent")


        // Form reset for a clean look
        this.reset();


    } else {
        // ERROR
        messageDiv.textContent = errorMessage;
        messageDiv.style.backgroundColor = '#f8d7da'; // Light red background
        messageDiv.style.color = '#721c24'; // Dark red text
    }
});