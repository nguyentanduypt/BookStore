package com.example.bookstore.models.dto;
import lombok.Data;
@Data
public class CustomerDTO {
    private String id;
    private String name;
    //    private String avatar;
    private String email;
    private String phoneNumber;
    private String password;
    private String status;
    private String roleName;
    private String createdTime;
    private String updatedTime;
    private String customerType;
}
