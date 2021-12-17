package dubbo_hmily.smb_api;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@TableName("admintor")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Admintor {
    @TableId
    private int id ;
    private String admname;
    private String manageList;

}
