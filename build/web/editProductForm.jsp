<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit Product</title>
</head>
<body>
    <h1>Edit Product</h1>
    <form action="updateProductServlet" method="post">
        <input type="hidden" name="id" value="${id}" />
        
        <label for="product_name">Product Name: </label>
        <input type="text" name="product_name" value="${product_name}" /><br><br>

        <label for="availability">Availability: </label>
        <input type="checkbox" name="availability" ${availability ? 'checked' : ''} /><br><br>

        <label for="price">Price: </label>
        <input type="text" name="price" value="${price}" /><br><br>

        <input type="submit" value="Update Product" />
    </form>
    <a href="ViewProductsServlet">Back to Products List</a>
</body>
</html>
