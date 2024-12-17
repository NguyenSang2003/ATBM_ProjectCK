function addToCart(productId) {
    const sizeId = document.getElementById(`sizeSelect_${productId}`).value;
    const materialId = document.getElementById(`materialSelect_${productId}`).value;

    if (!sizeId || !materialId) {
        alert('Vui lòng chọn kích cỡ và chất liệu.');
        return;
    }

    fetch('./cart', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: `type=odd&idProduct=${productId}&sizeId=${sizeId}&materialId=${materialId}`
    })
        .then(response => response.json())
        .then(data => {
            if (data.status === 200) {
                alert(data.message);
            } else {
                alert('Thêm vào giỏ không thành công');
            }
        })
        .catch(error => {
            console.error('Lỗi:', error);
            alert('Có lỗi xảy ra!');
        });
}