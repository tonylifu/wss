document.addEventListener('DOMContentLoaded', function() {
    const dropdownToggle = document.getElementById('dropdownToggle');
    const dropdown = dropdownToggle.parentElement;

    dropdownToggle.addEventListener('click', function() {
        dropdown.classList.toggle('active'); // Toggle the active class on click
    });

    // Optional: Close the dropdown if clicking outside of it
    document.addEventListener('click', function(event) {
        if (!dropdown.contains(event.target)) {
            dropdown.classList.remove('active'); // Close the dropdown if clicked outside
        }
    });
});
