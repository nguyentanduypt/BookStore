package com.example.bookstore.models;

import com.example.bookstore.enums.UserStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "\"user\"")
@Data
@Inheritance(strategy = InheritanceType.JOINED)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Size(min = 3, max = 255, message = "Tên phải từ 3 đến 255 ký tự")
    @NotBlank(message = "Tên không được để trống")
    @Column(nullable = false)
    private String name;
    @Email(message = "Email không hợp lệ")
    @Pattern(
            regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.(com|vn)$",
            message = "Email phải kết thúc bằng .com hoặc .vn"
    )
    @NotBlank(message = "Email không được để trống")
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank(message = "Vui lòng nhập mật khẩu")
    @Size(min = 8, max = 255, message = "Mật khẩu phải có ít nhất 8 kí tự")
    @Column(nullable = false)
    private String password;

    @NotBlank(message = "Số điện thoại không được để trống")
    @Pattern(regexp = "^\\d{10}$", message = "Số điện thoại phải đúng 10 chữ số")
    @Column(nullable = false, unique = true)
    private String phoneNumber;

    private Instant createdTime;
    private Instant updatedTime;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
}
