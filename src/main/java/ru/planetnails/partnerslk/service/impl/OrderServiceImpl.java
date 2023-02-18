package ru.planetnails.partnerslk.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.planetnails.partnerslk.model.item.dto.ItemDtoAddWithAmount;
import ru.planetnails.partnerslk.model.order.Order;
import ru.planetnails.partnerslk.model.order.OrderStatus;
import ru.planetnails.partnerslk.model.order.OrderVt;
import ru.planetnails.partnerslk.model.order.dto.OrderMapper;
import ru.planetnails.partnerslk.model.order.dto.OrderOutDto;
import ru.planetnails.partnerslk.model.order.vtOrderStatuses;
import ru.planetnails.partnerslk.model.user.User;
import ru.planetnails.partnerslk.repository.*;
import ru.planetnails.partnerslk.repository.itemRepository.ItemRepository;
import ru.planetnails.partnerslk.service.OrderService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    private long num = 0L;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final ContractorRepository contractorRepository;
    private final OrderVtRepository orderVtRepository;
    private final vtOrderStatusesRepository vtOrderStatusesRepository;
    private final OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(ItemRepository itemRepository, UserRepository userRepository, ContractorRepository
            contractorRepository, OrderVtRepository orderVtRepository, ru.planetnails.partnerslk.repository.vtOrderStatusesRepository vtOrderStatusesRepository, OrderRepository orderRepository) {
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
        this.contractorRepository = contractorRepository;
        this.orderVtRepository = orderVtRepository;
        this.vtOrderStatusesRepository = vtOrderStatusesRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public OrderOutDto add(List<ItemDtoAddWithAmount> items, String userId) {
        User user = userRepository.getReferenceById(userId);
        long n_row = 0L;
        Order order = new Order();
        double sumWithoutDiscount = 0;
        double sumOfDiscount = 0;
        double sumWithDiscount = 0;

        List<OrderVt> orderVts = new ArrayList<>();
        for (ItemDtoAddWithAmount itemDtoAddWithAmount : items) {
            OrderVt orderVt = new OrderVt();
            orderVt.setN_row(n_row++);
            orderVt.setItem(itemRepository.getReferenceById(itemDtoAddWithAmount.getId()));
            orderVt.setAmount(itemDtoAddWithAmount.getAmount());
            orderVt.setSale(itemDtoAddWithAmount.getSalePrice());
            orderVt.setDiscount(itemDtoAddWithAmount.getDiscount());
            orderVt.setPrice(itemDtoAddWithAmount.getSalePriceBeforeDiscount());
            orderVt.setTotal(itemDtoAddWithAmount.getRetailPrice() * itemDtoAddWithAmount.getAmount());
            orderVtRepository.save(orderVt);
            orderVts.add(orderVt);
            sumWithoutDiscount = sumWithoutDiscount + itemDtoAddWithAmount.getSalePriceBeforeDiscount();
            sumOfDiscount = sumOfDiscount + (itemDtoAddWithAmount.getSalePriceBeforeDiscount() - itemDtoAddWithAmount.getSalePrice());
            sumWithDiscount = sumWithDiscount + itemDtoAddWithAmount.getSalePrice();
            order.setSumWithoutDiscount(sumWithoutDiscount);
            order.setSumOfDiscount(sumOfDiscount);
            order.setSumWithDiscount(sumWithDiscount);
        }

        vtOrderStatuses vtOrderStatuses = new vtOrderStatuses();
        vtOrderStatuses.setOrderStatus(OrderStatus.NEW);
        vtOrderStatuses.setUpdated(LocalDateTime.now());
        vtOrderStatuses.setUser(user);
        vtOrderStatusesRepository.save(vtOrderStatuses);


        order.setNum(num++);
        order.setOrderDate(LocalDateTime.now());
        order.setOrderVts(orderVts);
        order.setContractor(contractorRepository.findByPartnerId(user.getPartner().getId()));
        order.setStatus(OrderStatus.NEW);
        orderRepository.save(order);
        return OrderMapper.fromOrderToOrderOutDto(order);
    }
}
