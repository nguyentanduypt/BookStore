package com.example.bookstore.models.dto;
import lombok.Data;
@Data
public class UserDTO {
    private String id;
    private String name;
    private String email;
    private String phoneNumber;
    private String password;
    private String createdTime;
    private String updatedTime;
    private String status;
    private String roleId;
}
