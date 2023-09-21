package entities;

public class Laaner {

    private int laaner_id;
    private String name;
    private String address;
    private int zip;
    private String by;

    public Laaner(int laaner_id, String name, String address, int zip, String by){
        this.laaner_id = laaner_id;
        this.name = name;
        this.address = address;
        this.zip = zip;
        this.by = by;
    }

    public Laaner(String name, String address, int zip){
        this.name = name;
        this.address = address;
        this.zip = zip;
    }

    public String getBy() {
        return by;
    }

    public void setBy(String by) {
        this.by = by;
    }

    public int getLaaner_id() {
        return laaner_id;
    }

    public void setLaaner_id(int laaner_id) {
        this.laaner_id = laaner_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getZip() {
        return zip;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }


    @Override
    public String toString() {
        return "Laaner{" +
                "laaner_id=" + laaner_id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", zip=" + zip +
                ", by='" + by + '\'' +
                '}';
    }
}
