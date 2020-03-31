package ru.sberbank.demo.app.model.transaction;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.sberbank.demo.app.model.IEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "withdraws")
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class WithdrawTransaction extends AbstractTransaction {
}