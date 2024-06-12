package com.project.shopApp.services;

import com.project.shopApp.dtos.OrderDTO;
import com.project.shopApp.exeptions.DataNotFoundException;
import com.project.shopApp.models.Order;
import com.project.shopApp.models.OrderStatus;
import com.project.shopApp.models.User;
import com.project.shopApp.repositories.OrderRepository;
import com.project.shopApp.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderRervice{
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;
    @Override
    public Order createOrder(OrderDTO orderDTO) throws Exception {
        //tìm xem user'id có tồn tại ko
        User user = userRepository
                .findById(orderDTO.getUserId())
                .orElseThrow(() -> new DataNotFoundException("Cannot find user with id: "+orderDTO.getUserId()));
        //convert orderDTO => Order
        //dùng thư viện Model Mapper
        // Tạo một luồng bảng ánh xạ riêng để kiểm soát việc ánh xạ
        modelMapper.typeMap(OrderDTO.class, Order.class)
                .addMappings(mapper -> mapper.skip(Order::setId));
        // Cập nhật các trường của đơn hàng từ orderDTO
        Order order = new Order();
        modelMapper.map(orderDTO, order);
        order.setUser(user);
        order.setOrderDate(new Date());//lấy thời điểm hiện tại
        order.setStatus(OrderStatus.PENDDING);
        //Kiểm tra shipping date phải >= ngày hôm nay
        LocalDate shippingDate = orderDTO.getShippingDate() == null
                ? LocalDate.now() : orderDTO.getShippingDate();
        if (shippingDate.isBefore(LocalDate.now())) {
            throw new DataNotFoundException("Date must be at least today !");
        }
        order.setShippingDate(shippingDate);
        order.setActive(true);
        orderRepository.save(order);
        return order;
    }

    @Override
    public Order getOrder(Long id) throws DataNotFoundException {
        return orderRepository.findById(id).orElseThrow(()->new DataNotFoundException("canot find order"));
    }

    @Override
    public Order updateOrder(Long id, OrderDTO orderDTO) throws DataNotFoundException {
        Order order = getOrder(id);
        User existingUser = userRepository.findById(orderDTO.getUserId()).orElseThrow(()-> new DateTimeException("cannot find user"));
        modelMapper.typeMap(OrderDTO.class,Order.class)
                .addMappings(mapper->mapper.skip(Order::setId));
            modelMapper.map(orderDTO,order);
            order.setUser(existingUser);
        return orderRepository.save(order);
    }

    @Override
    public void deleteOrder(Long id) throws DataNotFoundException {
        Order order = orderRepository.findById(id).orElseThrow(null);
        // khong nen xoa cung hãy xoa mềm
       if(order != null){
           order.setActive(false);
           orderRepository.save(order);

       }          }

    @Override
    public List<Order> findByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }
}
