function checkStrength(value = "") {
    var strength = 0;
    strength+= checkCharacterType(value);
    strength+= value.length / 2;
    return parseInt(strength);
}

function checkCharacterType(value=""){
    var strength = 0;
    if (value.length > 8) {
        strength += 1;
    }
    if (/[A-Z]/.test(value)) {
        strength += 1;
    }
    if (/[a-z]/.test(value)) {
        strength += 1;
    }
    if (/[0-9]/.test(value)) {
        strength += 1;
    }
    if (/[!@#$%^&*(),.?":{}|<>]/.test(value)) {
        strength += 1;
    }
    return strength;
}
function toggle(password){
    if (password.type == 'password') {
        password.type = 'text';
    } else {
        password.type = 'password';
    }
}
function assessPassword(password, passwordMessage, passwordProgressBar, submitButton) {
    var value = password.value;
    var strength = 0;
    var strengthLabel = '';

    if(checkCharacterType(value)<2){
        passwordMessage.textContent = 'Password should contain at least 2 character types including digit, special character, uppercase and lowercase letter';
        submitButton.disabled = true;
    }else{
        passwordMessage.textContent = '';
    }

    if (checkStrength(value) >= 12) {
        strength = 100;
        strengthLabel = 'Very Strong';
    } else if (checkStrength(value) >= 8) {
        strength = 75;
        strengthLabel = 'Strong';
    } else if (checkStrength(value) >= 4) {
        strength = 50;
        strengthLabel = 'Medium';
    } else if (value.length > 0) {
        strength = 25;
        strengthLabel = 'Weak';
    }

    passwordProgressBar.style.width = strength + '%';
    passwordProgressBar.textContent = strengthLabel;

    if (strength === 0) {
        passwordProgressBar.className = 'progress-bar';
        submitButton.disabled = true;
    } else if (strength <= 25) {
        passwordProgressBar.className = 'progress-bar bg-danger';
    } else if (strength <= 50) {
        passwordProgressBar.className = 'progress-bar bg-warning';
    } else if (strength <= 75) {
        passwordProgressBar.className = 'progress-bar bg-info';
    } else {
        passwordProgressBar.className = 'progress-bar bg-success';
    }
}

function checkRetype(retypePassword, password, submitButton, errorMessage) {
    if (retypePassword.value !== password.value) {
        submitButton.disabled = true;
        errorMessage.textContent = 'Passwords do not match';
    } else {
        submitButton.disabled = false;
        errorMessage.textContent = '';
    }
}