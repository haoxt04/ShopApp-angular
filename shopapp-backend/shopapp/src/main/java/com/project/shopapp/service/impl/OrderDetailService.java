package com.project.shopapp.service.impl;
import com.project.shopapp.dto.request.OrderDetailDTO;
import com.project.shopapp.exception.ResourceNotFoundException;
import com.project.shopapp.model.Order;
import com.project.shopapp.model.OrderDetail;
import com.project.shopapp.model.Product;
import com.project.shopapp.repository.OrderDetailRepository;
import com.project.shopapp.repository.OrderRepository;
import com.project.shopapp.repository.ProductRepository;
import com.project.shopapp.service.IOrderDetailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderDetailService implements IOrderDetailService {
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final ProductRepository productRepository;

    @Override
    public OrderDetail createOrderDetail(OrderDetailDTO orderDetailDTO) {
        // tìm orderId có tồn tại không
        Order order = getOrderByOrderDetailId(orderDetailDTO);
        // tìm product theo id
        Product product = getProductByOrderDetailId(orderDetailDTO);

        OrderDetail orderDetail = OrderDetail.builder()
                .order(order)
                .product(product)
                .numberOfProducts(orderDetailDTO.getNumberOfProducts())
                .price(orderDetailDTO.getPrice())
                .totalMoney(orderDetailDTO.getTotalMoney())
                .color(orderDetailDTO.getColor())
                .build();
        return orderDetailRepository.save(orderDetail);
    }

    @Override
    public OrderDetail getOrderDetail(Long id) {
        return orderDetailRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("orderDetail not found"));
    }

    @Override
    public OrderDetail updateOrderDetail(Long id, OrderDetailDTO orderDetailDTO) {
        // kiểm tra order detail có tồn tại
        OrderDetail existsOrderDetail = getOrderDetail(id);
        // kiểm tra order có tồn tại
        Order existsOrder = getOrderByOrderDetailId(orderDetailDTO);
        // kiểm tra product có tồn tại
        Product existsProduct = getProductByOrderDetailId(orderDetailDTO);
        // set các trường còn lại
        existsOrderDetail.setPrice(orderDetailDTO.getPrice());
        existsOrderDetail.setNumberOfProducts(orderDetailDTO.getNumberOfProducts());
        existsOrderDetail.setTotalMoney(orderDetailDTO.getTotalMoney());
        existsOrderDetail.setColor(orderDetailDTO.getColor());
        return orderDetailRepository.save(existsOrderDetail);
    }

    @Override
    public void deleteById(Long id) {
        orderDetailRepository.deleteById(id);
    }

    @Override
    public List<OrderDetail> findByOrderId(Long orderId) {
        return orderDetailRepository.findByOrderId(orderId);
    }

    private Order getOrderByOrderDetailId(OrderDetailDTO orderDetailDTO) {
        return orderRepository.findById(orderDetailDTO.getOrderId()).orElseThrow(()
        -> new ResourceNotFoundException("order by order detail not found"));
    }

    private Product getProductByOrderDetailId(OrderDetailDTO orderDetailDTO) {
        return productRepository.findById(orderDetailDTO.getProductId()).orElseThrow(()
                -> new ResourceNotFoundException("product by order detail not found"));
    }
}
