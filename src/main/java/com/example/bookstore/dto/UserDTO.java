package com.example.bookstore.dto;
import lombok.Data;
@Data
public class UserDTO {
    private String id;
    private String name;
    private String email;
    private String password;
    private String createdTime;
    private String updatedTime;
    private String status;
    private String roleId;
}
