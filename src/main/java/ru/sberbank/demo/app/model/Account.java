package ru.sberbank.demo.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;
import ru.sberbank.demo.app.service.IEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@Table(name = "accounts", uniqueConstraints = {@UniqueConstraint(columnNames = {"id", "client_id"}, name = "unique_user_account_idx")})
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Account implements IEntity {

    @Id
    @GeneratedValue
    @Column(nullable = false)
    @NotNull
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    @NotNull
    private Client client;

    @Column
    @NotNull
    @Range(min = 0)
    private Long balance;

}
