package models;

public class Store {

    private String _id;
    private Double lat;
    private Double lng;
    private String label;
    private boolean draggable;
    private String user;

    public Store() {
    }

    public Store(String _id, Double lat, Double lng, String label, boolean draggable, String user) {
        this._id = _id;
        this.lat = lat;
        this.lng = lng;
        this.label = label;
        this.draggable = draggable;
        this.user = user;
    }

    @Override
    public String toString(){
        return "{ \"_id\":\"" + this._id + "\", " +
                "\"lat\":\"" + this.lat + "\", " +
                "\"lng\":\"" + this.lng + "\", " +
                "\"label\":\"" + this.label + "\", " +
                "\"draggable\":\"" + this.draggable + "\", " +
                "\"user\":\"" + this.user + "\" }";
    }

    @Override
    public boolean equals(Object obj){

        if(!(obj instanceof Store)){
            return false;
        }

        Store store2 = (Store) obj;

        if(this._id.equals(store2.get_id()) &&
           this.label.equals(store2.getLabel()) &&
           this.lat.equals(store2.getLat()) &&
           this.lng.equals(store2.getLng()) &&
           this.draggable == store2.isDraggable() &&
           this.user.equals(store2.getUser())){
            return true;
        }
        else {
            return false;
        }
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public boolean isDraggable() {
        return draggable;
    }

    public void setDraggable(boolean draggable) {
        this.draggable = draggable;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
