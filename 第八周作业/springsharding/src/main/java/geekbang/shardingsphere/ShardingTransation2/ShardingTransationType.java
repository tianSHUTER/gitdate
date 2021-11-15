package geekbang.shardingsphere.ShardingTransation2;


import org.apache.shardingsphere.transaction.core.TransactionType;
import org.springframework.transaction.TransactionStatus;

import java.lang.annotation.*;


@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ShardingTransationType {
    TransactionType value() default TransactionType.LOCAL;
}
