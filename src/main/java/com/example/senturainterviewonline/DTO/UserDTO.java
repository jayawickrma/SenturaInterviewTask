package com.example.senturainterviewonline.DTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.Map;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    @NotBlank(message = "UID is required")
    @Pattern(regexp = ".*\\D.*", message = "UID must contain at least one non-digit character")
    private String uid;

    @Email(message = "Invalid email format")
    private String email;

    @Size(max = 50, message = "Given name cannot be more than 50 characters")
    private String givenName;

    @Size(max = 50, message = "Middle name cannot be more than 50 characters")
    private String middleName;

    @Size(max = 100, message = "Name cannot be more than 100 characters")
    private String name;

    @Size(max = 50, message = "Family name cannot be more than 50 characters")
    private String familyName;

    @Size(max = 50, message = "Nickname cannot be more than 50 characters")
    private String nickname;

    @Pattern(regexp = "^\\+?[0-9. ()-]{7,25}$", message = "Invalid phone number format")
    private String phoneNumber;

    @Size(max = 255, message = "Comment cannot be more than 255 characters")
    private String comment;

    private String picture;

    @Size(max = 100, message = "Directory cannot be more than 100 characters")
    private String directory;

    private Map<String, Object> metadata;

    private List<String> tags;

    @NotNull(message = "Suspension status must be provided")
    private Boolean isSuspended;

    @NotNull(message = "Bot status must be provided")
    private Boolean isBot;
}
