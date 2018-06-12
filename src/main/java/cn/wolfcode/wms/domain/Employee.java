package cn.wolfcode.wms.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class Employee extends BaseDomain{

    private String name;

    private String password;

    private String email;

    private Integer age;

    private boolean admin;

    //一对多
    private Department dept;
    //多对多
    private List<Role> roles = new ArrayList<>();
}