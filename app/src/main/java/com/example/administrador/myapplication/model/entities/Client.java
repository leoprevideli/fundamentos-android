package com.example.administrador.myapplication.model.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.administrador.myapplication.model.persistence.MemoryClientRepository;

import java.io.Serializable;
import java.util.List;

public class Client implements Serializable, Parcelable{

    private String name;
    private Integer age;
    private String phone;
    private String address;

    public Client(){
        super();
    }

    public Client(Parcel in){
        super();

        readToParcel(in);
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Client client = (Client) o;

        if (name != null ? !name.equals(client.name) : client.name != null) return false;
        if (age != null ? !age.equals(client.age) : client.age != null) return false;
        if (phone != null ? !phone.equals(client.phone) : client.phone != null) return false;
        return !(address != null ? !address.equals(client.address) : client.address != null);

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (age != null ? age.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Client{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    public void save() {
        MemoryClientRepository.getInstance().save(this);
    }

    public static List<Client> getAll() {
        return MemoryClientRepository.getInstance().getAll();

    }

    public void delete() {
        MemoryClientRepository.getInstance().delete(this);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name == null ? "" : name);
        dest.writeString(phone == null ? "" : phone);
        dest.writeInt(age == null ? -1 : age);
        dest.writeString(address == null ? "" : address);
    }

    private void readToParcel(Parcel in) {
        name = in.readString();
        phone = in.readString();
        int partialAge = in.readInt();
        age = partialAge == 1 ? null : partialAge;
        address = in.readString();
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
