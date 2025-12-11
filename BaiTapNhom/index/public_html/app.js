const API = "http://localhost:8081/products";

async function loadProducts() {
    let res = await fetch(API);
    let data = await res.json();

    let list = document.getElementById("product-grid");
    let html = "";

    data.forEach(p => {
        html += `
        <div class="product-card">
            <h3>${p.name}</h3>
            <p>${p.price.toLocaleString()} đ</p>
            <div class="add-btn">Thêm vào giỏ</div>
        </div>`;
    });

    list.innerHTML = html;
}

loadProducts();
