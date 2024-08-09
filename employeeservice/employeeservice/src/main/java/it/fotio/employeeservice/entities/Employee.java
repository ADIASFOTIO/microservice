package it.fotio.employeeservice.entities;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "employees")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    @Id
    private String id;
    //    @NotNull
    @NotEmpty
//    @NotBlank
    private String name;
    private String surname;
    @Size(min = 10,max = 50)
    @Email
//    @Pattern(regexp = "[a-zA-z0-9]")
    private String email;
    private String job;
    @Min(18)
    @Max(100)
//    @Pattern(regexp = "[0-9]")
//    @DecimalMin()
//    @DecimalMax()
//    @Positive
    @PositiveOrZero
//    @Negative
//    @NegativeOrZero
    private int age;
    //    @Future
//    @Past
    @CreationTimestamp
    private Date addedDate;

}
