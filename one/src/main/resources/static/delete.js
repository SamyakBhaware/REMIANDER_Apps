document.getElementById('deleteForm').addEventListener('submit', function(event) {
    event.preventDefault(); // Default form submission roko

    // Variables
    const confirmationInput = document.getElementById('confirmation').value.trim();
    const messageDiv = document.getElementById('message');

    const REQUIRED_WORD = 'DELETE'; // IITian-level confirmation check

    // Reset message
    messageDiv.textContent = '';
    messageDiv.style.backgroundColor = 'transparent';

    // 1. Check for correct confirmation word
    if (confirmationInput === REQUIRED_WORD) {
        // SUCCESS (Dummy Deletion)
        messageDiv.textContent = `✅ Account Deleted Successfully! (This is a front-end simulation only).`;
        messageDiv.style.backgroundColor = '#f8d7da'; // Use a gentle red/pink even for success in deletion context
        messageDiv.style.color = '#721c24'; // Dark red

        // Disable the delete button after successful "deletion"
        document.querySelector('.delete-button').disabled = true;
        document.querySelector('.delete-button').style.opacity = '0.5';

        // Real life mein yahan server ko request jaati hai
        console.log("Dummy Deletion successful!");

    } else {
        // ERROR
        messageDiv.textContent = '❌ Confirmation failed. You must type "DELETE" exactly.';
        messageDiv.style.backgroundColor = '#f0f0f0'; // Light grey
        messageDiv.style.color = '#dc3545'; // Danger Red
    }
});