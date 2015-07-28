package com.example.administrador.myapplication.model.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.administrador.myapplication.model.persistence.MemoryClientRepository;
import com.example.administrador.myapplication.model.persistence.SQLiteClientRepository;

import java.io.Serializable;
import java.sql.SQLClientInfoException;
import java.util.List;

public class Client implements Serializable, Parcelable{

    private Integer id;
    private String name;
    private Integer age;
    private String phone;
    private String zip;
    private String street;
    private String streetName;
    private String neighborhood;
    private String city;
    private String state;

    public Client(){
        super();
    }

    public Client(Parcel in){
        super();

        readToParcel(in);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Client client = (Client) o;

        if (id != null ? !id.equals(client.id) : client.id != null) return false;
        if (name != null ? !name.equals(client.name) : client.name != null) return false;
        if (age != null ? !age.equals(client.age) : client.age != null) return false;
        if (phone != null ? !phone.equals(client.phone) : client.phone != null) return false;
        if (zip != null ? !zip.equals(client.zip) : client.zip != null) return false;
        if (street != null ? !street.equals(client.street) : client.street != null) return false;
        if (streetName != null ? !streetName.equals(client.streetName) : client.streetName != null)
            return false;
        if (neighborhood != null ? !neighborhood.equals(client.neighborhood) : client.neighborhood != null)
            return false;
        if (city != null ? !city.equals(client.city) : client.city != null) return false;
        return !(state != null ? !state.equals(client.state) : client.state != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (age != null ? age.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (zip != null ? zip.hashCode() : 0);
        result = 31 * result + (street != null ? street.hashCode() : 0);
        result = 31 * result + (streetName != null ? streetName.hashCode() : 0);
        result = 31 * result + (neighborhood != null ? neighborhood.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", phone='" + phone + '\'' +
                ", zip='" + zip + '\'' +
                ", street='" + street + '\'' +
                ", streetName='" + streetName + '\'' +
                ", neighborhood='" + neighborhood + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                '}';
    }

        public void save() {
        SQLiteClientRepository.getInstance().save(this);
    }

    public static List<Client> getAll() {
        return SQLiteClientRepository.getInstance().getAll();

    }

    public void delete() {
        SQLiteClientRepository.getInstance().delete(this);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id == null ? -1 : id);
        dest.writeString(name == null ? "" : name);
        dest.writeInt(age == null ? -1 : age);
        dest.writeString(phone == null ? "" : phone);
        dest.writeString(zip == null ? "" : zip);
        dest.writeString(street == null ? "" : street);
        dest.writeString(streetName == null ? "" : streetName);
        dest.writeString(neighborhood == null ? "" : neighborhood);
        dest.writeString(city == null ? "" : city);
        dest.writeString(state == null ? "" : state);
    }

    private void readToParcel(Parcel in) {
        int partialId = in.readInt();
        id = partialId == -1 ? null : partialId;
        name = in.readString();
        int partialAge = in.readInt();
        age = partialAge == -1 ? null : partialAge;
        phone = in.readString();
        zip = in.readString();
        street = in.readString();
        streetName = in.readString();
        neighborhood = in.readString();
        city = in.readString();
        state = in.readString();
    }

    public static final Parcelable.Creator<Client> CREATOR =
            new Parcelable.Creator<Client>() {
                @Override
                public Client createFromParcel(Parcel source) {
                    return new Client(source);
                }

                @Override
                public Client[] newArray(int size) {
                    return new Client[size];
                }
            };

}
