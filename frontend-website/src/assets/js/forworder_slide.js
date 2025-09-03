window.onload = function () {
  const track = document.querySelector(".content-2"); // The scroll container
  const cards = document.querySelectorAll(".forwarder-card");
  const prevBtn = document.querySelector(".arrow.prev");
  const nextBtn = document.querySelector(".arrow.next");

  if (!track || cards.length === 0) return;

  let index = 0;

  function updateScroll() {
    const cardWidth = cards[0].offsetWidth + 24; // 24px gap
    track.scrollTo({
      left: index * cardWidth,
      behavior: "smooth",
    });

    // Enable/disable buttons visually
    if (index <= 0) {
      prevBtn.classList.add("disabled");
      prevBtn.disabled = true;
    } else {
      prevBtn.classList.remove("disabled");
      prevBtn.disabled = false;
    }

    if (index >= cards.length - 3) {
      // show 3 cards, so stop before end
      nextBtn.classList.add("disabled");
      nextBtn.disabled = true;
    } else {
      nextBtn.classList.remove("disabled");
      nextBtn.disabled = false;
    }
  }

  nextBtn.addEventListener("click", () => {
    if (index < cards.length - 1) {
      index++;
      updateScroll();
    }
  });

  prevBtn.addEventListener("click", () => {
    if (index > 0) {
      index--;
      updateScroll();
    }
  });

  // Optional: Reset index on resize if needed
  window.addEventListener("resize", updateScroll);
};
