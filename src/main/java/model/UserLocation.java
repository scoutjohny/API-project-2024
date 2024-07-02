package model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
public class UserLocation {

    private String street;
    private String city;
    private String state;
    private String country;

    //OVO NAM SVE LOMBOK OBEZBEDJUJE!!!
//    public UserLocation(String street, String city, String state, String country) {
//        this.street = street;
//        this.city = city;
//        this.state = state;
//        this.country = country;
//    }
//
//    public UserLocation() {
//    }
//
//    public String getStreet() {
//        return street;
//    }
//
//    public void setStreet(String street) {
//        this.street = street;
//    }
//
//    public String getCity() {
//        return city;
//    }
//
//    public void setCity(String city) {
//        this.city = city;
//    }
//
//    public String getState() {
//        return state;
//    }
//
//    public void setState(String state) {
//        this.state = state;
//    }
//
//    public String getCountry() {
//        return country;
//    }
//
//    public void setCountry(String country) {
//        this.country = country;
//    }
//
//    @Override
//    public String toString() {
//        return "UserLocation{" +
//                "street='" + street + '\'' +
//                ", city='" + city + '\'' +
//                ", state='" + state + '\'' +
//                ", country='" + country + '\'' +
//                '}';
//    }
}
