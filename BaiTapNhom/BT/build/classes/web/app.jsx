const userId = "user1";

function fetchCart() {
  fetch(`/cart?userId=${userId}`)
    .then(res => res.json())
    .then(data => {
      const ul = document.getElementById("cartItems");
      ul.innerHTML = "";
      for (let key in data) {
        let li = document.createElement("li");
        li.textContent = `${data[key].product.name} - ${data[key].quantity} x ${data[key].product.price}`;
        ul.appendChild(li);
      }
    });
}

function addToCart() {
  const productId = document.getElementById("productId").value;
  const name = document.getElementById("productName").value;
  const price = parseFloat(document.getElementById("productPrice").value);
  const qty = parseInt(document.getElementById("productQty").value);

  fetch("/cart/add", {
    method: "POST",
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ userId, productId, name, price, quantity: qty })
  }).then(() => fetchCart());
}

function clearCart() {
  fetch("/cart/clear", {
    method: "POST",
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ userId })
  }).then(() => fetchCart());
}

function sendChat() {
  const message = document.getElementById("chatMessage").value;
  fetch("/chat/send", {
    method: "POST",
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ message: `${userId}: ${message}` })
  });
  document.getElementById("chatMessage").value = "";
}

fetchCart();
