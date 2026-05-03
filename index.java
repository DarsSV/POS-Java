<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Canteen POS System</title>
    <style>
         --- CSS DESIGN --- 
        body {
            font-family: 'Segoe UI', Arial, sans-serif;
            background-color: #f0f2f5;
            display: flex;
            justify-content: center;
            padding: 40px;
        }

        .pos-card {
            background: white;
            width: 420px;
            padding: 25px;
            border-radius: 8px;
            box-shadow: 0 4px 15px rgba(0,0,0,0.1);
            border: 1px solid #ccc;
        }

        h1 { font-size: 22px; margin-top: 0; color: #333; }
        .menu-box {
            background: #fffbe6;
            border: 1px solid #ffe58f;
            padding: 10px;
            margin-bottom: 20px;
            font-size: 14px;
        }

        .control-group { margin-bottom: 15px; }
        label { display: block; font-weight: 600; margin-bottom: 5px; font-size: 14px; }
        
        select, input {
            width: 100%;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 4px;
            margin-bottom: 10px;
        }

        .button-grid {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 10px;
            margin-bottom: 20px;
        }

        button {
            padding: 12px;
            font-weight: bold;
            cursor: pointer;
            border: 1px solid #bbb;
            border-radius: 4px;
            transition: 0.2s;
        }

        .btn-add { background-color: #e6f7ff; color: #0050b3; grid-column: span 2; }
        .btn-remove { background-color: #fff1f0; color: #cf1322; }
        .btn-new { background-color: #f6ffed; color: #389e0d; }
        .btn-receipt { background-color: #f5f5f5; color: #333; grid-column: span 2; margin-top: 10px; }

        button:hover { opacity: 0.8; }

        /* --- Order Summary --- */
        .summary-title { font-weight: bold; margin-bottom: 5px; border-bottom: 1px solid #eee; }
        .order-display {
            background: #f9f9f9;
            border: 1px solid #ddd;
            height: 150px;
            padding: 10px;
            overflow-y: auto;
            font-family: 'Courier New', monospace;
            font-size: 13px;
            white-space: pre-wrap;
        }

        .total-banner {
            font-size: 20px;
            font-weight: bold;
            text-align: right;
            margin-top: 15px;
            color: #1d39c4;
        }
    </style>
</head>
<body>

<div class="pos-card">
    <h1>Canteen POS</h1>
    <div class="menu-box">
        <b>Menu:</b> Burger(60), Fries(50), Fishbol(20), Kikiam(25)
    </div>

    <div class="control-group">
        <label>Select Food</label>
        <select id="itemSelect">
            <option value="Burger" data-price="60">Burger</option>
            <option value="Fries" data-price="50">Fries</option>
            <option value="Fishbol" data-price="20">Fishbol</option>
            <option value="Kikiam" data-price="25">Kikiam</option>
        </select>

        <label>Quantity</label>
        <input type="number" id="itemQty" value="1" min="1">
    </div>

    <div class="button-grid">
        <button class="btn-add" onclick="addItem()">ADD ITEM</button>
        <button class="btn-remove" onclick="removeLast()">REMOVE LAST</button>
        <button class="btn-new" onclick="newOrder()">NEW ORDER</button>
        <button class="btn-receipt" onclick="printReceipt()">PRINT RECEIPT</button>
    </div>

    <div class="summary-title">Current Order:</div>
    <div class="order-display" id="displayArea">Order is empty...</div>
    <div class="total-banner" id="totalPrice">Total: 0 pesos</div>
</div>

<script>
    let cart = [];

    function addItem() {
        const select = document.getElementById('itemSelect');
        const name = select.value;
        const price = parseInt(select.options[select.selectedIndex].getAttribute('data-price'));
        const qty = parseInt(document.getElementById('itemQty').value);

        if (qty > 0) {
            cart.push({ name, qty, total: (price * qty) });
            updateUI();
        }
    }

    function removeLast() {
        if (cart.length > 0) {
            cart.pop();
            updateUI();
        }
    }

    function newOrder() {
        if (confirm("Clear current order and start a new one?")) {
            cart = [];
            updateUI();
        }
    }

    function updateUI() {
        const display = document.getElementById('displayArea');
        const totalText = document.getElementById('totalPrice');
        
        if (cart.length === 0) {
            display.innerText = "Order is empty...";
            totalText.innerText = "Total: 0 pesos";
            return;
        }

        let currentTotal = 0;
        let log = "";
        cart.forEach(item => {
            log += `${item.name.padEnd(10)} x${item.qty} = ${item.total} pesos\n`;
            currentTotal += item.total;
        });

        display.innerText = log;
        totalText.innerText = "Total: " + currentTotal + " pesos";
    }

    function printReceipt() {
        if (cart.length === 0) {
            alert("No items to print!");
            return;
        }

        let total = 0;
        let receiptText = "--- CANTEEN RECEIPT ---\n\n";
        cart.forEach(item => {
            receiptText += `${item.name} x${item.qty} : ${item.total} pesos\n`;
            total += item.total;
        });
        receiptText += "\n-----------------------\n";
        receiptText += "GRAND TOTAL: " + total + " pesos\n";
        receiptText += "Thank you for eating!";

        alert(receiptText);
    }
</script>

</body>
</html>