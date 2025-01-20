package com.project.teashop.controller;

import com.project.teashop.entity.Cart;

import com.project.teashop.entity.CartItem;
import com.project.teashop.entity.OrderData;
import com.project.teashop.entity.Product;
import com.project.teashop.kafka.Producer;
import com.project.teashop.repository.CartItemRepos;
import com.project.teashop.repository.CartRepos;
import com.project.teashop.repository.ProductRepos;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CartController {

    private final CartRepos cartRepos;
    private final CartItemRepos cartItemRepos;
    private final ProductRepos productRepos;
    private final Producer producer;

    private List<Long> cartProductId = new ArrayList<>();
    private List<Integer> cartProductQty = new ArrayList<>();

    public CartController(CartRepos cartRepos, ProductRepos productRepos,
                          CartItemRepos cartItemRepos, Producer producer) {
        this.cartRepos = cartRepos;
        this.productRepos = productRepos;
        this.cartItemRepos = cartItemRepos;
        this.producer = producer;
    }


    @PostMapping("/create-order")
    public ResponseEntity<String> createOrder(@RequestBody OrderData orderData) {

        orderData.setOrderTime(LocalDateTime.now());
        // В orderData будут все данные о заказе, включая товары

        System.out.println(orderData.toString());
        producer.sendMessage(orderData);
        return ResponseEntity.ok("Заказ успешно оформлен");
    }


    @GetMapping("/cart")
    public String viewCart(Model model, HttpSession session) {
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }

        List<CartItem> cartItems = cart.getCartItems(); // Получаем items из корзины
        System.out.println("--- Cart items: " + cartItems);
        
        model.addAttribute("cart", cart);
        model.addAttribute("cartItems", cartItems);
        List<Product> products = (List<Product>) productRepos.findAll();
        model.addAttribute("products", products);

        return "order";
    }

    @PostMapping("/add-to-cart")
    public @ResponseBody String addToCart(@RequestParam Long productId,
                                          @RequestParam int quantity,
                                          HttpSession session) {
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            cart.setDateOrder(LocalDateTime.now());
            cart.setOrderPrice(0.0); // Изначально цена 0
            session.setAttribute("cart", cart);
        }

        cartProductId.add(productId);
        cartProductQty.add(quantity);
        System.out.println("Cart prod id: " + cartProductId);
        System.out.println("Cart prod qty: " + cartProductQty);

        Product product = productRepos.findById(productId).orElseThrow();
        System.out.println("Product: " + product + " with qty ="+ quantity  +" has ben added!");

        // Создаем CartItem и добавляем его в корзину
        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);
        cartItem.setCart(cart); // Добавляем ссылку на корзину

        // Сохраняем CartItem в БД
//        cartItemRepository.save(cartItem);

        Double priceCartItem = getProductPriceInCartItem(productId, quantity);
        System.out.println("Price CartItem = " + priceCartItem.toString());

        // Обновляем цену заказа
        cart.setOrderPrice(cart.getOrderPrice() + priceCartItem);
        // Добавляем CartItem в корзину
        cart.addItem(cartItem);

        System.out.println("Price order: " + cart.getOrderPrice() + " $");
        // Сохраняем корзину в сессии
        session.setAttribute("cart", cart);

        return "Товар добавлен в корзину";
    }

    public double getProductPriceInCartItem(Long productId, int quantity){
        Product product = productRepos.findById(productId).orElseThrow();
        Double price_product = product.getPrice();
        return  price_product * quantity;
    }
}
