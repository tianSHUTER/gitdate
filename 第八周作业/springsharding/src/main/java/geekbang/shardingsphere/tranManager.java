package geekbang.shardingsphere;

import geekbang.shardingsphere.ShardingTransation2.ShardingTransationType;
import org.apache.shardingsphere.transaction.core.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.transaction.annotation.Transactional;

public class tranManager {
    @Autowired

    JdbcTemplate jdbcTemplate;

    @Transactional
    @ShardingTransationType(TransactionType.XA)
    public  void insert (){
    String SQL="insert into t_order(name,status) values ('ggg',true),('tima',false) ";

    }

 public void select(){
     String SQL="select * from t_order";

     Object execute = jdbcTemplate.execute(SQL, (PreparedStatementCallback<Object>) preparedStatement -> {


         return preparedStatement;

     });
 }

}
