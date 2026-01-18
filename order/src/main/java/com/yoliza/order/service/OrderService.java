package com.yoliza.order.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.yoliza.order.model.Order;
import com.yoliza.order.repository.OrderRepository;
import com.yoliza.order.vo.Pelanggan;
import com.yoliza.order.vo.Produk;
import com.yoliza.order.vo.ResponseTemplate;

@Service
public class OrderService {
    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RestTemplate restTemplate;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }
    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    public List<ResponseTemplate> getOrderWithProdukById(Long id){
        List<ResponseTemplate> responseList = new ArrayList<>();
        Order order = getOrderById(id);
        ServiceInstance serviceInstance = discoveryClient.getInstances("PRODUK").get(0);
        Produk produk = restTemplate.getForObject(serviceInstance.getUri() + "/api/produk/"
                + order.getProdukId(), Produk.class);
        ServiceInstance serviceInstance1 = discoveryClient.getInstances("PELANGGAN").get(0);
        Pelanggan pelanggan = restTemplate.getForObject(serviceInstance1.getUri() + "/api/pelanggan/"
                + order.getPelangganId(), Pelanggan.class);
        ResponseTemplate vo = new ResponseTemplate();
        vo.setOrder(order);
        vo.setProduk(produk);
        vo.setPelanggan(pelanggan);
        responseList.add(vo);
        return responseList;
    }
}
