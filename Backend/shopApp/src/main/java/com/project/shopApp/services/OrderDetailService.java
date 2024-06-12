package com.project.shopApp.services;

import com.project.shopApp.dtos.OrderDTO;
import com.project.shopApp.dtos.OrderDetailDTO;
import com.project.shopApp.exeptions.DataNotFoundException;
import com.project.shopApp.models.Order;
import com.project.shopApp.models.OrderDetail;
import com.project.shopApp.models.Product;
import com.project.shopApp.repositories.OrderDetailRepository;
import com.project.shopApp.repositories.OrderRepository;
import com.project.shopApp.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class OrderDetailService implements IOrderDetailService{
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final OrderDetailRepository orderDetailRepository;
    @Override
    public OrderDetail createOrderDetail(OrderDetailDTO newOrderDetail) throws Exception {
        Order order = orderRepository.findById(newOrderDetail.getOrderId()).orElseThrow(()-> new DataNotFoundException("cannot find order"));
        Product product = productRepository.findById(newOrderDetail.getProductId()).orElseThrow(()-> new DataNotFoundException("cannot find product"));
        OrderDetail orderDetail = OrderDetail.builder()
                .order(order)
                .product(product)
                .price(newOrderDetail.getPrice())
                .numberOfProducts(newOrderDetail.getNumberOfProduct())
                .totalMoney(newOrderDetail.getTotalMoney())
                .color(newOrderDetail.getColor())
                .build();
        orderDetailRepository.save(orderDetail);
        return orderDetail;
    }

    @Override
    public OrderDetail getOrderDetail(Long id) throws DataNotFoundException {
        return orderDetailRepository.findById(id).orElseThrow(()-> new DataNotFoundException("Cannot find order detail"));
    }

    @Override
    public OrderDetail updateOrderDetail(Long id, OrderDetailDTO newOrderDetailData) throws DataNotFoundException {
        OrderDetail orderDetail = getOrderDetail(id);
        Order order = orderRepository.findById(newOrderDetailData.getOrderId()).orElseThrow(()-> new DataNotFoundException("cannot find order"));
        Product product = productRepository.findById(newOrderDetailData.getProductId()).orElseThrow(()-> new DataNotFoundException("cannot find product"));
        modelMapper.typeMap(OrderDetailDTO.class,OrderDetail.class).addMappings(mapper -> mapper.skip(OrderDetail::setId));
        modelMapper.map(newOrderDetailData,orderDetail);
        orderDetail.setOrder(order);
        orderDetail.setProduct(product);
        orderDetailRepository.save(orderDetail);
        return orderDetail;
    }

    @Override
    public void deleteById(Long id) throws DataNotFoundException {
        OrderDetail orderDetail = getOrderDetail(id);
        orderDetailRepository.delete(orderDetail);
    }

    @Override
    public List<OrderDetail> findByOrderId(Long orderId) throws DataNotFoundException {
        orderRepository.findById(orderId).orElseThrow(()-> new DataNotFoundException("cannoot find order"));
        return null;
    }
}
