document.addEventListener('DOMContentLoaded', function() {
  // Create mobile menu button
  const mobileBtn = document.createElement('button');
  mobileBtn.className = 'mobile-menu-btn';
  mobileBtn.innerHTML = '☰';
  mobileBtn.style.display = 'none';
  
  // Insert into navbar (in actions-div)
  const actionsDiv = document.querySelector('.actions-div');
  actionsDiv.parentNode.insertBefore(mobileBtn, actionsDiv);
  
  // Toggle menu
  mobileBtn.addEventListener('click', function() {
    document.querySelector('.navbar').classList.toggle('menu-active');
  });
  
  // Responsive checks
  function updateLayout() {
    const isMobile = window.innerWidth <= 768;
    const mobileBtn = document.querySelector('.mobile-menu-btn');
    const navbarMenu = document.querySelector('.navbar-menu');
    const buttons = document.querySelectorAll('.button-2, .button-3');
    
    mobileBtn.style.display = isMobile ? 'block' : 'none';
    navbarMenu.style.display = isMobile ? 'none' : 'flex';
    
    // Show/hide buttons based on screen size
    buttons.forEach(button => {
      button.style.display = isMobile ? 'none' : 'flex';
    });
    
    // Always show the quote button
    document.querySelector('.button').style.display = 'flex';
  }
  
  // Initial setup
  updateLayout();
  window.addEventListener('resize', updateLayout);
});