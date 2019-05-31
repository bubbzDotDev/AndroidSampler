package macbeth.androidsampler.Files;

public class Data {
    private String param1;
    private String param2;
    private String param3;

    public Data(String param1, String param2, String param3) {
        this.param1 = param1;
        this.param2 = param2;
        this.param3 = param3;
    }

    public String toString() {
        return "Param1="+param1+" Param2="+param2+" Param3="+param3;
    }
}
