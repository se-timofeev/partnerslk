package ru.planetnails.partnerslk.logger;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "request_logs")
@Data
public class Logger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "remote_addr")
    private String RemoteAddr;
    @Column(name = "remote_port")
    private Integer RemotePort;
    @Column(name = "request_uri")
    private String RequestURI;

    @CreationTimestamp
    @Column(name ="request_date")
    private Date requestDate = convertToDateViaSqlTimestamp(LocalDateTime.now());


    public Date convertToDateViaSqlTimestamp(LocalDateTime dateToConvert) {
        return java.sql.Timestamp.valueOf(dateToConvert);
    }
}
