package com.example.http.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName: MyUser
 * @Description:
 * @Author: lixl
 * @Date: 2021/6/8 15:47
 */
@Data
public class MyUserDto implements Serializable {

    private static final long serialVersionUID = 1345110497030156048L;

    private Long id;

    private String name;

    private Integer age;

    private Gender gender;

    enum  Gender{
        male,female
    }
}
