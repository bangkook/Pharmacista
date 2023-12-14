package com.example.pharmacysystem.controller;
import com.example.pharmacysystem.model.CartItem;
import com.example.pharmacysystem.model.Order;
import com.example.pharmacysystem.model.OrderDetail;
import com.example.pharmacysystem.model.Product;
import com.example.pharmacysystem.service.CartItemService;
import com.example.pharmacysystem.service.OrderDetailService;
import com.example.pharmacysystem.service.OrderService;
import com.example.pharmacysystem.service.ProductService;
import com.example.pharmacysystem.utils.CartItemId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.pharmacysystem.utils.Constants.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000") // Replace with your frontend URL
public class CartController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderDetailService orderDetailService;

    @GetMapping("get-max-amount")
    @ResponseBody
    public ResponseEntity<Integer> getProductQuantity(@RequestParam("serialNumber") String serialNumber) {
        Product product = productService.getProductBySerialNumberCartItem(serialNumber);
        if (product != null && product.getQuantity() > 0) {
            return new ResponseEntity<>(product.getQuantity(), HttpStatus.OK);
        } else
            return new ResponseEntity<>(0, HttpStatus.OK);
    }


    @GetMapping("Product-From-Cart")
    @ResponseBody
    public List<Map<String, Object>> getProductsAndQuantities(@RequestParam("userId") int userId) {
        List<Map<String, Object>> result = new ArrayList<>();

        List<CartItem> cartItems = cartItemService.getCartItemsByUserId(userId);

        for (CartItem cartItem : cartItems) {
            Map<String, Object> productInfo = new HashMap<>();
            productInfo.put(PRODUCT_SN, cartItem.getProductSN());
            productInfo.put(QUANTITY, cartItem.getQuantity());

            Product product = productService.getProductBySerialNumberCartItem(cartItem.getProductSN());

            if (product != null && product.getQuantity() > 0) {
                productInfo.put(PRODUCT_NAME, product.getName());
                productInfo.put(PRICE, product.getPrice());
                productInfo.put(PHOTO, product.getPhoto());
                productInfo.put(AMOUNT, product.getQuantity());
                result.add(productInfo);
                // Add more product information as needed
            }else if (product == null){ //The product is deleted from the inventory
                cartItemService.deleteCartItemByproductSN(cartItem.getProductSN());
            }

        }
        System.out.println("result: "+ result);
        return result;
    }
    @PutMapping("Update-Quantity")
    @ResponseBody
    public void updateQuantity(@RequestBody Map<String, Object> payload) {
        int userId = (int) payload.get(USER_ID);
        String productSN = (String) payload.get(PRODUCT_SN);
        int quantity = (int) payload.get(QUANTITY);
        cartItemService.updateQuantityByUserNameAndSerialNumber(userId, productSN, quantity);
        System.out.println("quantity: " + quantity);
    }

    @DeleteMapping("/removeCartItem")
    @ResponseBody
    public ResponseEntity<String> deleteCartItem(@RequestParam int userId, @RequestParam String productSN) {
        CartItemId cartItemId = new CartItemId(userId,productSN);
        if (cartItemService.deleteCartItem(cartItemId)) {
            return new ResponseEntity<>("CartItem deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("CartItem not found or deletion failed", HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/createOrder")
    @ResponseBody
    public ResponseEntity<Integer> createOrder(@RequestBody Order order) {
        System.out.println("order: "+order);
        if (orderService.createOrder(order)) {
            return new ResponseEntity<>(order.getId(), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/createOrderDetails")
    @ResponseBody
    public ResponseEntity<String> createOrderDetails(@RequestBody OrderDetail Orderdetail) {
        if (orderDetailService.createOrderDetails(Orderdetail)) {
            return new ResponseEntity<>("order created successfully", HttpStatus.OK);
        }else {
            return new ResponseEntity<>("order failed to create", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("UpdateOrderdProducts")
    @ResponseBody
    public ResponseEntity<String> UpdateOrderdProducts(@RequestBody Map<String, Object> request) {
        String serialNumber = (String) request.get(SERIAL_NUMBER);
        int quantity = (int) request.get(QUANTITY);
        String response = productService.updateQuantityBySerialNumber(serialNumber, quantity);
        if (response == SUCCESS_PURCHASE) {
            return new ResponseEntity<>("Successfully ordered", HttpStatus.OK);
        } else if(response == ZERO_QUANTITY){
            return new ResponseEntity<>("empty", HttpStatus.OK);
        }else{
            return new ResponseEntity<>("outOfStock", HttpStatus.OK);
        }
    }

}
