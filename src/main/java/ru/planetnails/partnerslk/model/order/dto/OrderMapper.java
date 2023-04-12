package ru.planetnails.partnerslk.model.order.dto;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import ru.planetnails.partnerslk.model.contractor.Contractor;
import ru.planetnails.partnerslk.model.item.Item;
import ru.planetnails.partnerslk.model.order.Order;
import ru.planetnails.partnerslk.model.order.OrderStatus;
import ru.planetnails.partnerslk.model.order.OrderVt;
import ru.planetnails.partnerslk.model.order.vtOrderStatuses;
import ru.planetnails.partnerslk.model.partner.Partner;
import ru.planetnails.partnerslk.model.user.User;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class OrderMapper {

    private static Long num = 1L;

    public static Order fromOrderAddDtoOrder(OrderAddDto orderAddDto, Contractor contractor, Partner partner, List<OrderVt> orderVts,
                                             List<vtOrderStatuses> vtOrderStatuses) {
        return new Order(
                num++,
                LocalDateTime.now(),
                orderAddDto.getSumWithoutDiscount(),
                orderAddDto.getSumOfDiscount(),
                orderAddDto.getSumWithDiscount(),
                contractor,
                partner,
                OrderStatus.NEW,
                orderVts,
                vtOrderStatuses
        );
    }

    public static OrderOutDto fromOrderToOrderOutDto(Order order) {
        return new OrderOutDto(
                order.getId(),
                order.getNum(),
                order.getOrderDate(),
                order.getSumWithoutDiscount(),
                order.getSumWithoutDiscount(),
                order.getSumWithDiscount(),
                order.getContractor().getId(),
                order.getPartner().getId(),
                order.getStatus(),
                order.getOrderVts().stream()
                        .map(OrderMapper::fromOderVtToOrderOutDto)
                        .collect(Collectors.toList()),
                order.getVtOrderStatuses().stream()
                        .map(OrderMapper::fromVtOrderStatusesToOrderStatusesOutDto)
                        .collect(Collectors.toList())
        );
    }

    public static OderVtOutDto fromOderVtToOrderOutDto(OrderVt orderVt) {
        return new OderVtOutDto(
                orderVt.getId(),
                orderVt.getN_row(),
                orderVt.getItem().getId(),
                orderVt.getAmount(),
                orderVt.getSale(),
                orderVt.getDiscount(),
                orderVt.getPrice(),
                orderVt.getTotal()
        );
    }

    public static OrderVt fromOrderVtAddDtoToOrderVt(OderVtAddDto oderVtAddDto, Item item) {
        return new OrderVt(
                oderVtAddDto.getN_row(),
                item,
                oderVtAddDto.getAmount(),
                oderVtAddDto.getSale(),
                oderVtAddDto.getDiscount(),
                oderVtAddDto.getPrice(),
                oderVtAddDto.getTotal()
        );
    }

    public static vtOrderStatusesOutDto fromVtOrderStatusesToOrderStatusesOutDto(vtOrderStatuses vtOrderStatuses) {
        return new vtOrderStatusesOutDto(
                vtOrderStatuses.getId(),
                vtOrderStatuses.getOrderStatus(),
                vtOrderStatuses.getUpdated());
    }

    public static vtOrderStatuses AddVtOrderStatuses(User user) {
        return new vtOrderStatuses(
                OrderStatus.NEW,
                LocalDateTime.now(),
                user
        );
    }

    public static vtOrderStatuses UpdateVtOrderStatuses(User user) {
        return new vtOrderStatuses(
                OrderStatus.UPDATED,
                LocalDateTime.now(),
                user
        );
    }

    public static OrderFirstPartOutDto fromOrderToOrderFirstPartOutDto(Order order) {
        return new OrderFirstPartOutDto(
                order.getId(),
                order.getNum(),
                order.getOrderDate(),
                order.getSumWithoutDiscount(),
                order.getSumOfDiscount(),
                order.getSumWithDiscount(),
                order.getContractor().getId(),
                order.getPartner().getId(),
                order.getStatus()
        );
    }

    public static OrderSecondPartOutDto fromOrderSecondPartOutDto(Order order) {
        return new OrderSecondPartOutDto(
                order.getOrderVts().stream()
                        .map(OrderMapper::fromOderVtToOrderOutDto)
                        .collect(Collectors.toList())
        );
    }


    public static Gson getGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter());
        return gsonBuilder.create();
    }

    static class LocalDateTimeAdapter extends TypeAdapter<LocalDateTime> {
        DateTimeFormatter fmt = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

        @Override
        public void write(JsonWriter jsonWriter, LocalDateTime localDateTime) throws IOException {
            if (localDateTime == null) {
                jsonWriter.value("null");
                return;
            }
            jsonWriter.value(localDateTime.format(fmt));
        }

        @Override
        public LocalDateTime read(JsonReader jsonReader) throws IOException {
            final String text = jsonReader.nextString();
            if (text.equals("null")) {
                return null;
            }
            return LocalDateTime.parse(text, fmt);
        }
    }

}
