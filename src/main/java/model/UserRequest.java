package model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.javafaker.Faker;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Locale;

//@Getter             //implementira sve getere
//@Setter             //implementira sve setere
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data               //implementira sve getere, setere, equals() i toString metode kao i konstruktor za sva final polja
@AllArgsConstructor //implementira konstruktor sa svim poljima i argumentima
@NoArgsConstructor  //implementira prayan konstruktor da bi mogli da koristimo getere i setere
@Builder
@With
public class UserRequest {
    private String title;
    private String firstName;
    private String lastName;
    private String picture;
    private String gender;
    private String email;
    private String dateOfBirth;
    private String phone;
    private UserLocation location;

    public static UserRequest createUser(){
        Faker faker = new Faker(new Locale("en-US"));

        UserLocation location = UserLocation.builder()
                .street(faker.address().streetAddress())
                .city(faker.address().cityName())
                .state(faker.address().state())
                .country(faker.address().country())
                .build();

        String prefix = faker.name().prefix();
        if(prefix.equalsIgnoreCase("Ms.")){
            prefix = "miss";
        }else{
            prefix = "mr";
        };

//        UserRequest user = UserRequest.builder()
//                .title(prefix)
//                .firstName(faker.name().firstName())
//                .lastName(faker.name().lastName())
//                .picture(faker.internet().image(faker.number().randomDigitNotZero(),faker.number().randomDigitNotZero(),faker.random().nextBoolean(), faker.number().randomDigitNotZero() +".jpg"))
//                .gender(faker.demographic().sex().toLowerCase())
//                .email(faker.internet().emailAddress())
//                .dateOfBirth(String.valueOf(LocalDateTime.of(faker.number().numberBetween(1945,2000), faker.number().numberBetween(1,12),faker.number().numberBetween(1,28),faker.number().numberBetween(0,23),faker.number().numberBetween(0,59),faker.number().numberBetween(0,59))))
//                .phone(faker.phoneNumber().cellPhone())
//                .location(location)
//                .build();

        return UserRequest.builder()
                .title(prefix)
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .picture(faker.internet().image(faker.number().randomDigitNotZero(),faker.number().randomDigitNotZero(),faker.random().nextBoolean(), faker.number().randomDigitNotZero() +".jpg"))
                .gender(faker.demographic().sex().toLowerCase())
                .email(faker.internet().emailAddress())
                .dateOfBirth(String.valueOf(LocalDateTime.of(faker.number().numberBetween(1945,2000), faker.number().numberBetween(1,12),faker.number().numberBetween(1,28),faker.number().numberBetween(0,23),faker.number().numberBetween(0,59),faker.number().numberBetween(0,59))))
                .phone(faker.phoneNumber().cellPhone())
                .location(location)
                .build();

    }

}
