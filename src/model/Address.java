
package model;


public class Address {
    private int AddressId;
    private String Street1;
    private String Street2;
    private String City;
    private String State;
    private String Zip;
    private boolean IsDeliverable;


    public int getAddressId() {
        return AddressId;
    }

    public void setAddressId(int AddressId) {
        this.AddressId = AddressId;
    }

    public String getStreet1() {
        return Street1;
    }

    public void setStreet1(String Street1) {
        this.Street1 = Street1;
    }

    public String getStreet2() {
        return Street2;
    }

    public void setStreet2(String Street2) {
        this.Street2 = Street2;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String City) {
        this.City = City;
    }

    public String getState() {
        return State;
    }

    public void setState(String State) {
        this.State = State;
    }

    public String getZip() {
        return Zip;
    }

    public void setZip(String Zip) {
        this.Zip = Zip;
    }

    public boolean isIsDeliverable() {
        return IsDeliverable;
    }

    public void setIsDeliverable(boolean IsDeliverable) {
        this.IsDeliverable = IsDeliverable;
    }

}

