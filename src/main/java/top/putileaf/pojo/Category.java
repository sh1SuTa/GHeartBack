package top.putileaf.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.groups.Default;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class Category {

    @NotNull(groups = Update.class) private Integer id;//主键ID
    @NotEmpty private String categoryName;//分类名称，添加非空校验
    @NotEmpty private String categoryAlias;//分类别名
    private Integer createUser;//创建人ID
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") private LocalDateTime createTime;//创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") private LocalDateTime updateTime;//更新时间

    //如果校验
    public interface Add extends Default {

    }
    public interface Update extends Default{

    }
}
